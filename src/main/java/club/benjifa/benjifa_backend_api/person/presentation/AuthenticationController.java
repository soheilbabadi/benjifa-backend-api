package club.benjifa.benjifa_backend_api.person.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.benjifa.benjifa_backend_api.person.application.AuthService;
import club.benjifa.benjifa_backend_api.person.application.OtpService;
import club.benjifa.benjifa_backend_api.person.dto.OtpDto;
import club.benjifa.benjifa_backend_api.person.dto.PersonDto;
import club.benjifa.benjifa_backend_api.person.presentation.dto.*;
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthService authService;
    private final OtpService otpService;


    @PostMapping("/signup")
    public ResponseEntity<OtpDto> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/signing")
    public ResponseEntity<JwtAuthenticationResponse> signing(@RequestBody SigningRequest signingRequest) {
        return ResponseEntity.ok(authService.signing(signingRequest));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<PersonDto> verifyOtp(@RequestBody OtpDto otpDto) {

        return ResponseEntity.ok(otpService.validate(otpDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest dto) {
        return ResponseEntity.ok(authService.setRefreshToken(dto.token()));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String jwt) {
        authService.logout(jwt);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-password")
    public ResponseEntity<JwtAuthenticationResponse> updatePassword(@RequestHeader("Authorization") String token, @RequestBody UpdatePasswordDto dto) {
        return ResponseEntity.ok(authService.updatePassword(token, dto));
    }
}
