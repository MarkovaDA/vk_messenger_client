package su.vistar.client.configuration;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.MimeMessageHelper;

@Component
@PropertySource("classpath:application.mail.properties")
public class SMTPMailSender {
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Value("${mail.from}")
    private  String mailFrom;
    
    @Value("${mail.to}")
    private  String mailTo;
    
    //gennady.ponomarev@vistar.su     
    private String readPattern(String fileName) throws FileNotFoundException, IOException{
        String patternResult = "";
        BufferedReader br =
        new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName), "UTF-8"));
        String strLine;
        while ((strLine = br.readLine()) != null){
            patternResult += strLine;
        }
        return patternResult;
    }  
    public void newUserNotify(Long uid) throws MessagingException {        
        try {
            String subject = "Новый пользователь в приложении OnClick messenger";
            String body = String.format(readPattern("/newuser.pattern.txt"), uid, uid, uid);
            MimeMessage message = javaMailSender.createMimeMessage();            
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setFrom(mailFrom);
            helper.setTo(mailTo.split(","));
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (IOException ex) {
            Logger.getLogger(SMTPMailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void newCompanyNotify(Long uid, String companyName) throws MessagingException{
        try {
            String subject = "Cоздание новой кампании в приложении OnClick messenger";
            String pattern = "dd.MM.yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateFormat = simpleDateFormat.format(new Date());
            String body = String.format(readPattern("/newcompany.pattern.txt"), uid, companyName, dateFormat);
            MimeMessage message = javaMailSender.createMimeMessage();            
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setFrom(mailFrom);
            helper.setTo(mailTo.split(","));
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (IOException ex) {
            Logger.getLogger(SMTPMailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void newMessageNotify(Long uid, String companyMessage) throws MessagingException{
        try {
            String subject = "Cоздание нового сообщения в приложении OnClick messenger";
            String pattern = "dd.MM.yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateFormat = simpleDateFormat.format(new Date());
            String body = String.format(readPattern("/newmessage.pattern.txt"), uid, companyMessage, dateFormat);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);
            helper.setFrom(mailFrom);
            helper.setTo(mailTo.split(","));
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (IOException ex) {
            Logger.getLogger(SMTPMailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
