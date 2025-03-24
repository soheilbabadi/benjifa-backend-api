package club.benjifa.benjifa_backend_api.card.application;


import club.benjifa.benjifa_backend_api.card.dto.ReminderCardDto;
import club.benjifa.benjifa_backend_api.card.model.ReminderCard;

import java.util.List;

public interface ReminderCardService {
    ReminderCard create(ReminderCardDto dto, String token);

    void delete(long id, String token);

    ReminderCardDto update(ReminderCardDto dto, String token);

    ReminderCardDto update(ReminderCardDto dto);

    List<ReminderCardDto> findAllByUsername(String token);

    ReminderCardDto inactivate(long id, String token);

    ReminderCardDto complete(long id, String token);

    ReminderCardDto findById(long id, String token);

    List<ReminderCardDto> findAllByPetId(String petId, String token);

    List<ReminderCardDto> findAllNotifiable(String petId, String token);

    List<ReminderCard> findAllNotifiable();
}
