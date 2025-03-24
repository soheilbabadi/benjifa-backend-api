package club.benjifa.benjifa_backend_api.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.security.dto.TokenModel;

import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenSaverServiceImpl implements TokenSaverService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveToken(TokenModel tokenModel) {
        deleteByUsername(tokenModel.username());
        try {
            String tokenJson = objectMapper.writeValueAsString(tokenModel);
            stringRedisTemplate.opsForValue().set(String.valueOf(tokenModel.token().hashCode()), tokenJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void deleteByToken(String token) {
        stringRedisTemplate.delete(String.valueOf(token.hashCode()));
    }


    @Override
    public boolean existsByToken(String token) {
        var result = Boolean.TRUE.equals(stringRedisTemplate.hasKey(String.valueOf(token.hashCode())));
        if (!result) {
            throw new BenjiCustomException.UnauthorizedException("Token not found");
        }
        return true;
    }

    @Override
    public void deleteByUsername(String username) {
        Set<String> keys = redisTemplate.keys("*");
        assert keys != null;
        for (String key : keys) {
            String tokenJson = redisTemplate.opsForValue().get(key);
            try {
                TokenModel tokenModel = objectMapper.readValue(tokenJson, TokenModel.class);
                if (tokenModel.username().equals(username)) {
                    redisTemplate.delete(key);
                }
            } catch (Exception e) {
                // Handle JSON parsing exception
                log.error("Error while parsing token", e);
            }
        }
    }

    public void cleanup() {
        Set<String> keys = redisTemplate.keys("*");
        long currentTime = System.currentTimeMillis();
        assert keys != null;
        for (String key : keys) {
            String tokenJson = redisTemplate.opsForValue().get(key);
            try {
                TokenModel tokenModel = objectMapper.readValue(tokenJson, TokenModel.class);
                if (tokenModel.expireDate().getTime() < currentTime) {
                    redisTemplate.delete(key);
                }
            } catch (Exception e) {
                // Handle JSON parsing exception
                log.error("Error while parsing token", e);
            }
        }
    }
}