package club.benjifa.benjifa_backend_api.lost.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.lost.domain.LostDogAdvertise;
import club.benjifa.benjifa_backend_api.lost.dto.LostDogAdvertiseDto;
import club.benjifa.benjifa_backend_api.lost.dto.LostDogTransformer;
import club.benjifa.benjifa_backend_api.lost.repository.LostDogRepository;
import club.benjifa.benjifa_backend_api.security.JwtService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LostDogServiceImpl implements LostDogService {
    private final LostDogRepository lostDogRepository;
    private final JwtService jwtService;

    @Override
    public LostDogAdvertiseDto register(LostDogAdvertiseDto lostDogAdvertiseDto, String token) {
        String username = jwtService.getUsernameFromToken(token);
        var dto = new LostDogAdvertiseDto(
                lostDogAdvertiseDto.id(),
                lostDogAdvertiseDto.dogName(),
                lostDogAdvertiseDto.breed(),
                lostDogAdvertiseDto.breedId(),
                lostDogAdvertiseDto.age(),
                lostDogAdvertiseDto.color(),
                lostDogAdvertiseDto.colorId(),
                lostDogAdvertiseDto.lastSeenLocation(),
                lostDogAdvertiseDto.ownerName(),
                lostDogAdvertiseDto.description(),
                lostDogAdvertiseDto.found(),
                lostDogAdvertiseDto.lostOn(),
                lostDogAdvertiseDto.registerOn(),
                lostDogAdvertiseDto.expireOn(),
                username,
                lostDogAdvertiseDto.active()
        );

        LostDogAdvertise lostDogAdvertise = LostDogTransformer.toEntity(dto);
        lostDogAdvertise.setRegisterOn(LocalDateTime.now());
        lostDogAdvertise.setExpireOn(LocalDateTime.now().plusMonths(1));
        lostDogAdvertise.setFound(false);
        lostDogRepository.save(lostDogAdvertise);
        return LostDogTransformer.fromEntity(lostDogAdvertise);
    }

    @Override
    public LostDogAdvertiseDto findById(Long id) {
        LostDogAdvertise lostDogAdvertise = lostDogRepository.findById(id).orElseThrow(() -> new BenjiCustomException.EntityDidNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));
        return LostDogTransformer.fromEntity(lostDogAdvertise);
    }

    @Override
    public void delete(Long id, String token) throws BenjiCustomException.UnauthorizedException {
        String username = jwtService.getUsernameFromToken(token);

        var entity = lostDogRepository.findById(id)
                .orElseThrow(() -> new BenjiCustomException.EntityDidNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));

        if (!entity.getOwnerName().getUsername().equals(username)) {
            if (jwtService.isAdmin(token) && jwtService.isSupport(token))
                throw new BenjiCustomException.UnauthorizedException(BenjiCustomException.UNAUTHORIZED);
        }
        lostDogRepository.delete(entity);
    }

    @Override
    public Page<LostDogAdvertiseDto> findAll(Pageable pageable) {
        disableExpiredLostDog();
        Page<LostDogAdvertise> lostDogAdvertisePage = lostDogRepository.findAllByExpireOnBeforeAndActiveIsTrueAndFoundIsFalse(LocalDateTime.now(), pageable);
        return lostDogAdvertisePage.map(LostDogTransformer::fromEntity);
    }

    @Override
    public void disableExpiredLostDog() {
        var list = lostDogRepository.findAllByExpireOnAfter(LocalDateTime.now());
        for (LostDogAdvertise lostDogAdvertise : list) {
            lostDogAdvertise.setActive(false);
            lostDogRepository.save(lostDogAdvertise);
        }

    }


    @Override
    public void disable(long id, String token) throws BenjiCustomException.UnauthorizedException {
        String username = jwtService.getUsernameFromToken(token);
        var entity = lostDogRepository.findById(id)
                .orElseThrow(() -> new BenjiCustomException.EntityDidNotFoundException(BenjiCustomException.ENTITY_NOT_FOUND));

        if (!entity.getOwnerName().getUsername().equals(username)) {
            if (jwtService.isAdmin(token) && jwtService.isSupport(token))
                throw new BenjiCustomException.UnauthorizedException(BenjiCustomException.UNAUTHORIZED);
        }

        entity.setActive(false);
        lostDogRepository.save(entity);

    }
}
