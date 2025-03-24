package club.benjifa.benjifa_backend_api.person.application;


import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.person.dto.OtpDto;
import club.benjifa.benjifa_backend_api.person.presentation.dto.JwtAuthenticationResponse;
import club.benjifa.benjifa_backend_api.person.presentation.dto.SigningRequest;
import club.benjifa.benjifa_backend_api.person.presentation.dto.SignupRequest;
import club.benjifa.benjifa_backend_api.person.presentation.dto.UpdatePasswordDto;

public interface AuthService {

    OtpDto signup(SignupRequest signupRequest);

    JwtAuthenticationResponse signing(SigningRequest signupDto);

    JwtAuthenticationResponse setRefreshToken(String token);


    void logout(String token);

    boolean existToken(String token);

    JwtAuthenticationResponse updatePassword(String token, UpdatePasswordDto dto) throws BenjiCustomException.UnauthorizedException;


}
