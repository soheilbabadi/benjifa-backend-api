package club.benjifa.benjifa_backend_api.notification.application;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import club.benjifa.benjifa_backend_api.notification.dto.MailDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

   @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendMessage(MailDto mailDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmail);
            helper.setTo(mailDto.getTo());
            helper.setSubject(mailDto.getSubject());

            String htmlTemplate = new String(Files.readAllBytes(Paths.get("src/main/java/social/benji/benji_backend_api/notification/mailTemp/signup.html")));

            String htmlContent = htmlTemplate.replace("{{otp}}", mailDto.getExtraText());

            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            log.error("Error while sending email: ", e);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendOtpMail(MailDto mailDto) {

    }
}