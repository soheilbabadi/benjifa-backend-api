package club.benjifa.benjifa_backend_api.card.application;

import club.benjifa.benjifa_backend_api.card.dto.ReminderCardDto;
import club.benjifa.benjifa_backend_api.card.enums.ReminderStatusEnum;
import club.benjifa.benjifa_backend_api.card.model.ReminderCard;
import club.benjifa.benjifa_backend_api.card.model.ReminderInbox;
import club.benjifa.benjifa_backend_api.card.repository.ReminderCardRepository;
import club.benjifa.benjifa_backend_api.card.repository.ReminderInboxRepository;
import club.benjifa.benjifa_backend_api.exception.BenjiCustomException;
import club.benjifa.benjifa_backend_api.pet.domain.Pet;
import club.benjifa.benjifa_backend_api.pet.repository.PetRepository;
import club.benjifa.benjifa_backend_api.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReminderCardServiceImpl implements ReminderCardService {

    private final ReminderCardRepository reminderCardRepository;
    private final PetRepository petRepository;
    private final ReminderInboxRepository reminderInboxRepository;

    private final UserUtils userUtils;


    @Override
    public ReminderCard create(ReminderCardDto dto, String token) {

        ReminderCard reminderCard = fromDto(dto);
        reminderCard.setPet(petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid pet ID: " + dto.getPetId())));
        reminderCard.setUsername(userUtils.getUsernameFromToken(token));
        reminderCard.setStatus(ReminderStatusEnum.ACTIVE);
        reminderCard.setCreateOn(LocalDateTime.now());
        return reminderCardRepository.save(reminderCard);
    }


    @Override
    public void delete(long id, String token) {
        ReminderCard reminderCard = reminderCardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reminder card ID: " + id));
        String requestUsername = userUtils.getUsernameFromToken(token);
        if (!reminderCard.getUsername().equals(requestUsername)) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to delete this reminder card");
        }
        reminderCardRepository.delete(reminderCard);
    }

    @Override
    public ReminderCardDto update(ReminderCardDto dto, String token) {
        ReminderCard reminderCard = reminderCardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid reminder card ID: " + dto.getId()));
        String requestUsername = userUtils.getUsernameFromToken(token);
        if (!reminderCard.getUsername().equals(requestUsername)) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to update this reminder card");
        }
        reminderCard.setTitle(dto.getTitle());
        reminderCard.setDescription(dto.getDescription());
        reminderCard.setExpireOn(dto.getExpireOn());
        reminderCard.setNotifyMe(dto.isNotifyMe());
        reminderCard.setStatus(dto.getStatus());
        return fromEntity(reminderCardRepository.save(reminderCard));
    }


    @Override
    public ReminderCardDto update(ReminderCardDto dto) {
        ReminderCard reminderCard = reminderCardRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid reminder card ID: " + dto.getId()));
        reminderCard.setTitle(dto.getTitle());
        reminderCard.setDescription(dto.getDescription());
        reminderCard.setExpireOn(dto.getExpireOn());
        reminderCard.setNotifyMe(dto.isNotifyMe());
        reminderCard.setStatus(dto.getStatus());
        return fromEntity(reminderCardRepository.save(reminderCard));
    }

    @Override
    public List<ReminderCardDto> findAllByUsername(String token) {
        String username = userUtils.getUsernameFromToken(token);
        return reminderCardRepository.findAllByUsernameAndNotifyMeIsTrueAndStatusAndExpireOnOrderByExpireOnDesc(username, ReminderStatusEnum.ACTIVE, LocalDateTime.now().plusDays(3)).stream()

                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ReminderCardDto inactivate(long id, String token) {
        ReminderCard reminderCard = reminderCardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reminder card ID: " + id));
        String requestUsername = userUtils.getUsernameFromToken(token);
        if (!reminderCard.getUsername().equals(requestUsername)) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to update this reminder card");
        }
        reminderCard.setStatus(ReminderStatusEnum.INACTIVE);
        return fromEntity(reminderCardRepository.save(reminderCard));
    }


    @Override
    public ReminderCardDto complete(long id, String token) {
        ReminderCard reminderCard = reminderCardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reminder card ID: " + id));
        String requestUsername = userUtils.getUsernameFromToken(token);
        if (!reminderCard.getUsername().equals(requestUsername)) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to update this reminder card");
        }
        reminderCard.setStatus(ReminderStatusEnum.COMPLETED);
        return fromEntity(reminderCardRepository.save(reminderCard));
    }

    @Override
    public ReminderCardDto findById(long id, String token) {
        ReminderCard reminderCard = reminderCardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reminder card ID: " + id));
        String requestUsername = userUtils.getUsernameFromToken(token);
        if (!reminderCard.getUsername().equals(requestUsername)) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to see this reminder card");
        }
        return fromEntity(reminderCard);
    }


    @Override
    public List<ReminderCardDto> findAllByPetId(String petId, String token) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pet ID: " + petId));
        String requestUsername = userUtils.getUsernameFromToken(token);
        if (!pet.getPerson().getUsername().equals(requestUsername)) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to see this pet's reminder cards");
        }
        return reminderCardRepository.findAllByPet(pet).stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReminderCardDto> findAllNotifiable(String petId, String token) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pet ID: " + petId));
        String requestUsername = userUtils.getUsernameFromToken(token);
        if (!pet.getPerson().getUsername().equals(requestUsername)) {
            throw new BenjiCustomException.UnauthorizedException("You are not authorized to see this pet's reminder cards");
        }
        return reminderCardRepository.findAllByPet(pet).stream()
                .filter(reminderCard -> reminderCard.getStatus().equals(ReminderStatusEnum.ACTIVE))
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReminderCard> findAllNotifiable() {
        return reminderCardRepository.findAll().stream()
                .filter(reminderCard -> reminderCard.getStatus().equals(ReminderStatusEnum.ACTIVE))
                .filter(reminderCard -> reminderCard.getExpireOn().isBefore(LocalDateTime.now()))
                .filter(ReminderCard::isNotifyMe)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Scheduled(fixedRate = 43_200_000) // اجرا هر 12 ساعت یک‌بار
    public void processReminders() {
        List<ReminderCard> reminders = findAllNotifiable();
        for (ReminderCard reminder : reminders) {
            sendReminder(reminder);
            reminder.setStatus(ReminderStatusEnum.COMPLETED);
            reminderCardRepository.save(reminder);
            sendReminder(reminder);

        }
    }

    private void sendReminder(ReminderCard reminder) {

        ReminderInbox inbox = ReminderInbox.builder()
                .title(reminder.getTitle())
                .person(userUtils.getPersonFromUsername(reminder.getUsername()))
                .seen(false)
                .pet(reminder.getPet())
                .reminderDate(reminder.getExpireOn())
                .build();

        reminderInboxRepository.save(inbox);

    }

    private ReminderCardDto fromEntity(ReminderCard reminderCard) {
        return ReminderCardDto.builder()
                .id(reminderCard.getId())
                .title(reminderCard.getTitle())
                .description(reminderCard.getDescription())
                .createOn(reminderCard.getCreateOn())
                .expireOn(reminderCard.getExpireOn())
                .notifyMe(reminderCard.isNotifyMe())
                .username(reminderCard.getUsername())
                .status(reminderCard.getStatus())
                .petId(reminderCard.getPet().getId())
                .petId(reminderCard.getPet().getName())
                .build();
    }

    private ReminderCard fromDto(ReminderCardDto dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid pet ID: " + dto.getPetId()));

        return ReminderCard.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createOn(dto.getCreateOn())
                .expireOn(dto.getExpireOn())
                .notifyMe(dto.isNotifyMe())
                .username(dto.getUsername())
                .status(dto.getStatus())
                .pet(pet)
                .build();
    }
}
