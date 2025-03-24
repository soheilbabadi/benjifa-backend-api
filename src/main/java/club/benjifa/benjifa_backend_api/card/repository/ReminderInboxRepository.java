package club.benjifa.benjifa_backend_api.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import club.benjifa.benjifa_backend_api.card.model.ReminderInbox;
import club.benjifa.benjifa_backend_api.person.domain.Person;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReminderInboxRepository extends JpaRepository<ReminderInbox, Long> {


    List<ReminderInbox> findAllByPetAndSeenIsFalseAndReminderDateBeforeOrderByReminderDate(Pet pet, LocalDateTime dateTime);

    List<ReminderInbox> findAllByPersonAndSeenIsFalseAndReminderDateBeforeOrderByReminderDate(Person person, LocalDateTime dateTime);

    void deleteBySeenIsTrue();

}
