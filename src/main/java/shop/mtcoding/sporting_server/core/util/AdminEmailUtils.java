package shop.mtcoding.sporting_server.core.util;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.email.dto.EmailDTO;
import shop.mtcoding.sporting_server.admin.email.dto.EmailDTO.EmailObj;

@Service
@RequiredArgsConstructor
public class AdminEmailUtils {

    private final JavaMailSender javaMailSender;
    private final String fromAddress = "aozp83@gmail.com";

    public void sendEmail(EmailDTO emailDTO) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        List<EmailObj> emailList = emailDTO.getEmailList();
        String[] toAddresses = new String[emailList.size()];
        for (int i = 0; i < emailList.size(); i++) {
            toAddresses[i] = emailList.get(i).getEmail();
        }
        message.setTo(toAddresses);
        message.setSubject(emailDTO.getTitle());
        message.setText(emailDTO.getContent());
        javaMailSender.send(message);
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom(fromAddress);
        // message.setTo(emailDTO.getEmailList().get(0).getEmail());
        // message.setSubject(emailDTO.getTitle());
        // message.setText(emailDTO.getContent());
        // javaMailSender.send(message);

    }

}
