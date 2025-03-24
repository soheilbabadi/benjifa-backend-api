package club.benjifa.benjifa_backend_api.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.card.enums.ReminderStatusEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReminderCardDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 8634147644117511265L;

    private long id;

    private String title;

    private String description;

    private LocalDateTime createOn = LocalDateTime.now();

    private LocalDateTime expireOn;

    private boolean notifyMe = true;

    private String username;

    private ReminderStatusEnum status = ReminderStatusEnum.ACTIVE;
    private String Pet;
    private String petId;


}
