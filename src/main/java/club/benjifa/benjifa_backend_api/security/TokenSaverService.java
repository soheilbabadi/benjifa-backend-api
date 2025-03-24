package club.benjifa.benjifa_backend_api.security;

import club.benjifa.benjifa_backend_api.security.dto.TokenModel;

public interface TokenSaverService {
    void saveToken(TokenModel tokenModel);

    void deleteByToken(String token);

    boolean existsByToken(String token);

    void deleteByUsername(String username);

    void cleanup();
}
