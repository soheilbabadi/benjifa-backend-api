package club.benjifa.benjifa_backend_api.person.application;


import club.benjifa.benjifa_backend_api.person.dto.OtpDto;
import club.benjifa.benjifa_backend_api.person.dto.PersonDto;

public interface OtpService {
    PersonDto validate(OtpDto dto);

    String create(String email);
}
