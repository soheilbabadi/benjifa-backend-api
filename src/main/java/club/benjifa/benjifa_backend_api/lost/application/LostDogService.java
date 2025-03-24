package club.benjifa.benjifa_backend_api.lost.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.lost.dto.LostDogAdvertiseDto;

public interface LostDogService {
    LostDogAdvertiseDto register(LostDogAdvertiseDto lostDogAdvertiseDto, String token);

    LostDogAdvertiseDto findById(Long id);

    void delete(Long id, String token) throws BenjiCustomException.UnauthorizedException;

    Page<LostDogAdvertiseDto> findAll(Pageable pageable);

    void disableExpiredLostDog();

    void disable(long id, String token) throws BenjiCustomException.UnauthorizedException;
}
