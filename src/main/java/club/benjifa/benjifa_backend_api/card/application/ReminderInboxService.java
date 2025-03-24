package club.benjifa.benjifa_backend_api.card.application;


import club.benjifa.benjifa_backend_api.card.dto.ReminderInboxDto;

import java.util.List;

public interface ReminderInboxService {
    void markReminderAsSeen(Long reminderId, String token);

    List<ReminderInboxDto> findAll(String token);

    List<ReminderInboxDto> findAllByPet(String token, String petId);


    ReminderInboxDto findById(Long id, String token);

    void deleteAllSeen();

    void delete(long id, String token);
}
