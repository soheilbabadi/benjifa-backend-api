package club.benjifa.benjifa_backend_api.person.application;


import club.benjifa.benjifa_backend_api.security.TokenSaverService;
import club.benjifa.benjifa_backend_api.security.dto.TokenModel;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.notification.application.EmailService;
import club.benjifa.benjifa_backend_api.notification.dto.MailDto;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.person.dto.OtpDto;
import club.benjifa.benjifa_backend_api.person.enums.RoleEnum;
import club.benjifa.benjifa_backend_api.person.enums.UserStatusEnum;
import club.benjifa.benjifa_backend_api.person.presentation.dto.JwtAuthenticationResponse;
import club.benjifa.benjifa_backend_api.person.presentation.dto.SigningRequest;
import club.benjifa.benjifa_backend_api.person.presentation.dto.SignupRequest;
import club.benjifa.benjifa_backend_api.person.presentation.dto.UpdatePasswordDto;
import club.benjifa.benjifa_backend_api.person.repository.PersonRepo;
import club.benjifa.benjifa_backend_api.security.JwtService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {

    private final PersonRepo userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final EmailService emailService;
    private final PersonRepo personRepo;
    private final TokenSaverService tokenSaverService;


    @Override
    public OtpDto signup(SignupRequest signupRequest) {


        if (personRepo.existsByEmail(signupRequest.email())) {
            throw new BenjiCustomException.DuplicateEntityException("The entered email has already been registered.");
        }

        Person userInfo = Person.builder()
                .id(UUID.randomUUID().toString())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(false)
                .accountNonLocked(false)
                .accountNonExpired(false)
                .verified(false)

                .firstName(signupRequest.firstName())
                .lastname(signupRequest.lastName())
                .email(signupRequest.email().trim().toLowerCase())
                .password(passwordEncoder.encode(signupRequest.password()))
                .role(RoleEnum.USER)
                .status(UserStatusEnum.INACTIVE)
                .phone("")
                .nationalId(RandomStringUtils.randomNumeric(10))
                .lastLoginAt(LocalDateTime.now(ZoneId.of("UTC")))
                .registerAt(LocalDateTime.now(ZoneId.of("UTC")))
                .username(String.valueOf(signupRequest.email().trim().toLowerCase().hashCode()))
                .build();


        userInfoRepository.saveAndFlush(userInfo);

        String otp = sendOtp(signupRequest.email());
        return new OtpDto(otp, userInfo.getEmail());

    }


    @Override
    public JwtAuthenticationResponse signing(SigningRequest dto) {

        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(dto.email().trim().toLowerCase().hashCode(), dto.password()));

        var userInfo = userInfoRepository.findByUsername(String.valueOf(dto.email().trim().toLowerCase().hashCode())).orElseThrow(() ->
                new EntityNotFoundException("User not found!"));

        String token = jwtService.generateToken(userInfo);
        String refreshToken = jwtService.getRefreshToken(new HashMap<>(0), userInfo);

        var expireDate=jwtService.getClaimsFromToken(token).getExpiration();
        TokenModel tokenModel=new TokenModel(token,userInfo.getUsername(),expireDate);

        tokenSaverService.saveToken(tokenModel);

        return new JwtAuthenticationResponse(token, refreshToken);
    }

    @Override
    public JwtAuthenticationResponse setRefreshToken(String token) {
        var userInfo = userInfoRepository.findByUsername(jwtService.getUsernameFromToken(token)).orElseThrow(() ->
                new EntityNotFoundException("User not found"));

        String newToken = jwtService.generateToken(userInfo);
        String newRefreshToken = jwtService.getRefreshToken(new HashMap<>(0), userInfo);

        return new JwtAuthenticationResponse(newToken, newRefreshToken);
    }


    @Override
    public void logout(String token) {
        jwtService.logout(token);
    }

    @Override
    public boolean existToken(String token) {
        return jwtService.existToken(token);
    }

    @Override
    public JwtAuthenticationResponse updatePassword(String token, UpdatePasswordDto dto) {
        var userInfo = userInfoRepository.findByUsername(jwtService.getUsernameFromToken(token)).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));

        if (!passwordEncoder.matches(dto.oldPassword(), userInfo.getPassword())) {
            throw new BenjiCustomException.UnauthorizedException(BenjiCustomException.UNAUTHORIZED);
        }

        userInfo.setPassword(passwordEncoder.encode(dto.newPassword()));
        userInfoRepository.save(userInfo);

        return new JwtAuthenticationResponse(jwtService.generateToken(userInfo), jwtService.getRefreshToken(new HashMap<>(0), userInfo));
    }


    private String sendOtp(String email) {
        // var opt=OtpDto.builder().email(email).build();
        String otp = otpService.create(email);

        var maildto = MailDto.builder()
                .to(email)
                .subject("OTP Code")
                .extraText(otp)
                .build();
        emailService.sendMessage(maildto);
        return otp;
    }


}