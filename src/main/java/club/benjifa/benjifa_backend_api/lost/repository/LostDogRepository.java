package club.benjifa.benjifa_backend_api.lost.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.lost.domain.LostDogAdvertise;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LostDogRepository extends JpaRepository<LostDogAdvertise, Long> {

    Page<LostDogAdvertise> findAllByExpireOnBeforeAndActiveIsTrueAndFoundIsFalse(LocalDateTime now, Pageable pageable);

    List<LostDogAdvertise> findAllByExpireOnAfter(LocalDateTime now);
}
