package club.benjifa.benjifa_backend_api.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReminderInboxDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -7155382510300659603L;

    private Long id;

    private String title;

    private LocalDateTime reminderDate;

    private boolean seen;

    private String username;

    private String pet;
    private String petId;

}
