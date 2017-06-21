package su.vistar.client.configuration;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.MimeMessageHelper;

@Component
public class SMTPMailSender {
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    private final String mailFrom = "darya.towarddreams@gmail.com";
    private String messagePattern = "<p><a href=\"https://vk.com/id%d\">Пользователь</a> подал заявку на использование приложения OnClick messenger</p>"
            + "<br><a href=\"http://localhost:8084/vk_messenger_client/approve?uid=%d\" style=\"padding: 5px; margin:5px; border: 1px solid black;text-decoration:none;box-shadow: 0 0 10px rgba(0,0,0,0.5);\">разрешить</a>"
            + "<a href=\"http://localhost:8084/vk_messenger_client/decline?uid=%d\" style=\"padding: 5px; margin:5px; border: 1px solid black;text-decoration:none;box-shadow: 0 0 10px rgba(0,0,0,0.5);\">отклонить</a>";
    
    public void sendMail(Long uid) throws MessagingException {
        String to = "darya.markova.95@mail.ru";
        String subject = "Новый пользователь в приложении OnClick messenger";
        String body = String.format(messagePattern, uid, uid, uid);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setFrom(mailFrom);
        helper.setTo(to);  
        helper.setText(body, true);
        javaMailSender.send(message);       
    }
}
