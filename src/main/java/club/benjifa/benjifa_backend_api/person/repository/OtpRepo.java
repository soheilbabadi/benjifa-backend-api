package club.benjifa.benjifa_backend_api.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.person.domain.Otp;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {

    void deleteByOtp(String otp);

    void deleteByExpiredAtBeforeAndUsedIsTrue(LocalDateTime expiredOn);

    Optional<Otp> findByOtpAndEmailAndExpiredAtAfter(String otp, String email, LocalDateTime expiredOn);

    Optional<Otp> findByEmailAndExpiredAtAfter(String email, LocalDateTime expiredOn);
}
