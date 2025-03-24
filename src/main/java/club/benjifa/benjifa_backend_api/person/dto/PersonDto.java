package club.benjifa.benjifa_backend_api.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PersonDto {
    private String id;
    private String nationalId;
    private String firstName;
    private String lastname;
    private String email;
    private String phone;
    private String username;
    private String status;
    private LocalDateTime registerAt;
    private boolean enabled;
    private boolean verified;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private String role;
    private String personPhoto;
}