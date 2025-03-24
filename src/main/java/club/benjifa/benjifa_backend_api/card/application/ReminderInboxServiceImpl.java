package club.benjifa.benjifa_backend_api.card.application;

import club.benjifa.benjifa_backend_api.card.dto.ReminderInboxDto;
import club.benjifa.benjifa_backend_api.card.model.ReminderInbox;
import club.benjifa.benjifa_backend_api.card.repository.ReminderInboxRepository;
import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;
import club.benjifa.benjifa_backend_api.pet.repository.PetRepository;
import club.benjifa.benjifa_backend_api.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderInboxServiceImpl implements ReminderInboxService {

    private final ReminderInboxRepository reminderInboxRepository;
    private final PetRepository petRepository;
    private final UserUtils userUtils;

    @Override
    public void markReminderAsSeen(Long reminderId, String token) {

        var person = userUtils.getPersonFromToken(token);
        var reminderInbox = reminderInboxRepository.findById(reminderId).orElseThrow(() -> new RuntimeException(""));

        if (!reminderInbox.getPerson().getUsername().equals(person.getUsername())) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to perform this action");
        }

        reminderInbox.setSeen(true);
        reminderInboxRepository.save(reminderInbox);
    }


    @Override
    public List<ReminderInboxDto> findAll(String token) {
        var person = userUtils.getPersonFromToken(token);
        return reminderInboxRepository.findAllByPersonAndSeenIsFalseAndReminderDateBeforeOrderByReminderDate(person, LocalDateTime.now().plusDays(3))
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReminderInboxDto> findAllByPet(String token, String petId) {
        var person = userUtils.getPersonFromToken(token);
        var pet = petRepository.findById(petId).orElseThrow(() -> new BenjiCustomException.EntityDidNotFoundException("Pet not found"));
        if (!pet.getPerson().getUsername().equals(person.getUsername())) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to perform this action");
        }
        return reminderInboxRepository.findAllByPetAndSeenIsFalseAndReminderDateBeforeOrderByReminderDate(pet, LocalDateTime.now().plusDays(3))
                .stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public ReminderInboxDto findById(Long id, String token) {
        var person = userUtils.getPersonFromToken(token);
        var entity = reminderInboxRepository.findById(id).orElseThrow(() -> new BenjiCustomException.EntityDidNotFoundException("Reminder not found"));

        if (!person.getUsername().equals(entity.getPerson().getUsername())) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to perform this action");
        }
        entity.setSeen(true);
        reminderInboxRepository.save(entity);

        return fromEntity(entity);
    }

    @Override
    public void deleteAllSeen() {
        reminderInboxRepository.deleteBySeenIsTrue();
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public void delete(long id, String token) {
        var person = userUtils.getPersonFromToken(token);
        var entity = reminderInboxRepository.findById(id).orElseThrow(() -> new BenjiCustomException.EntityDidNotFoundException("Reminder not found"));

        if (!person.getUsername().equals(entity.getPerson().getUsername())) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to perform this action");
        }
        reminderInboxRepository.delete(entity);
        deleteAllSeen();
    }

    private ReminderInbox fromDto(ReminderInboxDto dto) {
        Pet pet = petRepository.findById(dto.getPetId()).orElseThrow(() ->
                new IllegalArgumentException("Pet not found"));

        return ReminderInbox.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .reminderDate(dto.getReminderDate())
                .seen(dto.isSeen())
                .pet(pet)
                .person(userUtils.getPersonFromUsername(dto.getUsername()))
                .build();
    }

    private ReminderInboxDto fromEntity(ReminderInbox inbox) {
        return ReminderInboxDto.builder()
                .id(inbox.getId())
                .title(inbox.getTitle())
                .reminderDate(inbox.getReminderDate())
                .seen(inbox.isSeen())
                .username(inbox.getPerson().getUsername())
                .pet(inbox.getPet().getName())
                .petId(inbox.getPet().getId())
                .build();
    }
}
