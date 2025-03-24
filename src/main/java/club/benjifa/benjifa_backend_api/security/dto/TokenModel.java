package club.benjifa.benjifa_backend_api.security.dto;

import java.util.Date;

public record TokenModel(
        String token,
        String username,
        Date expireDate
) {

}