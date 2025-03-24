package club.benjifa.benjifa_backend_api.notification.application;

import club.benjifa.benjifa_backend_api.notification.dto.MailDto;

public interface EmailService {
    void sendMessage(MailDto mailDto);
    void sendOtpMail(MailDto mailDto);

}
