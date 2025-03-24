package club.benjifa.benjifa_backend_api.person.application;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.lookup.domain.Lookup;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.person.dto.PersonDto;
import club.benjifa.benjifa_backend_api.person.dto.PersonUpdateDto;
import club.benjifa.benjifa_backend_api.person.enums.UserStatusEnum;
import club.benjifa.benjifa_backend_api.person.repository.PersonRepo;
import club.benjifa.benjifa_backend_api.security.JwtService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, UserDetailsService {


    private final PersonRepo userInfoRepository;
    private final JwtService jwtService;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userInfoRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
    }

    @Override
    public PersonDto findUserByEmail(String email) {
        return convertToDto(findByEmail(email));
    }

    @Override
    public PersonDto findUserId(String id) {
        return convertToDto(userInfoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND)));
    }

    @Override
    public Person findByUsername(String username) {
        return userInfoRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));

    }

    @Override
    public PersonDto findDtoByUsername(String username) {
        return convertToDto(findByUsername(username));
    }

    @Override
    public Person findByEmail(String email) {
        return userInfoRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
    }

    @Override
    public PersonDto findByMobile(String mobile) {
        var entity = userInfoRepository.findByPhone(mobile).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
        return convertToDto(entity);
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userInfoRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));

    }


    @Override
    public PersonDto update(PersonUpdateDto dto, String token) {

        String username = jwtService.getUsernameFromToken(token);

        var userInfo = userInfoRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));


        userInfo.setNationalId(dto.nationalId());
        userInfo.setFirstName(dto.firstName());
        userInfo.setLastname(dto.lastname());
        userInfo.setPhone(dto.phone());
        userInfo.setLastLoginAt(LocalDateTime.now(ZoneId.of("UTC")));
        userInfoRepository.saveAndFlush(userInfo);

        return convertToDto(userInfo);
    }

    @Override
    public List<Person> findByCity(Lookup city) {
        return userInfoRepository.findAllByCity(city);

    }

    @Override
    public PersonDto setAccountActive(String email) {
        var userInfo = userInfoRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
        userInfo.setVerified(false);
        userInfo.setAccountNonExpired(true);
        userInfo.setAccountNonLocked(true);
        userInfo.setCredentialsNonExpired(true);
        userInfo.setEnabled(true);
        userInfo.setStatus(UserStatusEnum.ACTIVE);
        userInfoRepository.save(userInfo);
        return convertToDto(userInfo);
    }

    private PersonDto convertToDto(Person userInfo) {
        return PersonDto.builder()
                .id(userInfo.getId())
                .nationalId(userInfo.getNationalId())
                .firstName(userInfo.getFirstName())
                .lastname(userInfo.getLastname())
                .email(userInfo.getEmail())
                .phone(userInfo.getPhone())
                .username(userInfo.getUsername())
                .status(userInfo.getStatus().name())
                .registerAt(userInfo.getRegisterAt())
                .enabled(userInfo.isEnabled())
                .verified(userInfo.isVerified())
                .accountNonLocked(userInfo.isAccountNonLocked())
                .accountNonExpired(userInfo.isAccountNonExpired())
                .credentialsNonExpired(userInfo.isCredentialsNonExpired())
                .role(userInfo.getRole().name())
              //  .personPhoto(userInfo.getPersonPhoto().getPhotoUrl())
                .personPhoto("https://picsum.photos/200/300")
                .build();
    }
}
