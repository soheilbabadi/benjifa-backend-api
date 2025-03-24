package club.benjifa.benjifa_backend_api.notification.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import club.benjifa.benjifa_backend_api.notification.enums.MessageTypeEnum;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDto implements Serializable  {
    @Serial
    private static final long serialVersionUID = -670967536166455157L;

    private String to;
    private String subject;
    private String extraText;
    private MessageTypeEnum messageType;

}