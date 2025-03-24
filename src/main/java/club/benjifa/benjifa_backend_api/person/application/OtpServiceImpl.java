package club.benjifa.benjifa_backend_api.person.application;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.person.domain.Otp;
import club.benjifa.benjifa_backend_api.person.dto.OtpDto;
import club.benjifa.benjifa_backend_api.person.dto.PersonDto;
import club.benjifa.benjifa_backend_api.person.repository.OtpRepo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepo otpRepo;
    private final PersonService personService;

    @Override
    public PersonDto validate(OtpDto dto) {
        var otp = otpRepo.findByOtpAndEmailAndExpiredAtAfter(dto.getOtp(), dto.getEmail(), LocalDateTime.now(ZoneId.of("UTC")));
        if (otp.isPresent()) {
            otp.get().setUsed(true);
            otpRepo.save(otp.get());
            return personService.setAccountActive(dto.getEmail());
        } else {
            return null;
        }

    }

    @Override

    public String create(String email) {
        delete();
        var otpOptional = otpRepo.findByEmailAndExpiredAtAfter(email, LocalDateTime.now(TimeZone.getTimeZone("UTC").toZoneId()));
        if (otpOptional.isPresent()) {
            return otpOptional.get().getOtp();
        }

        var otp = Otp.builder()
                .createAt(LocalDateTime.now(ZoneId.of("UTC")))
                .expiredAt(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(15))
                .email(email)
                .used(false)
                .otp(generateOtp())
                .build();
        otpRepo.save(otp);
        return otp.getOtp();
    }

    private void delete() {

        otpRepo.deleteByExpiredAtBeforeAndUsedIsTrue(LocalDateTime.now(ZoneId.of("UTC")));
    }

    private String generateOtp() {
        return RandomStringUtils.randomNumeric(8);
    }

}
