package club.benjifa.benjifa_backend_api.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.card.enums.ReminderStatusEnum;
import club.benjifa.benjifa_backend_api.card.model.ReminderCard;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderCardRepository extends JpaRepository<ReminderCard, Long> {

    List<ReminderCard> findAllByPet(Pet pet);

//    List<ReminderCard> findAllByUsernameOrderByExpireOnDesc(String username);

    List<ReminderCard> findAllByUsernameAndNotifyMeIsTrueAndStatusAndExpireOnOrderByExpireOnDesc(String username, ReminderStatusEnum reminderStatusEnum, LocalDateTime expireOn);
}
