package club.benjifa.benjifa_backend_api.person.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtpDto {
        @NotEmpty
        private String otp;

        @NotEmpty
        private String email;

}