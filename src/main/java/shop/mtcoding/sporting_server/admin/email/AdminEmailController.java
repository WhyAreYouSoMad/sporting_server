package shop.mtcoding.sporting_server.admin.email;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.email.dto.EmailDTO;
import shop.mtcoding.sporting_server.admin.email.dto.EmailDTO.EmailObj;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminEmailController {
    private final JavaMailSender javaMailSender;
    private AdminEmailService adminEmailService;

    @PostMapping("/email")
    public ResponseEntity<?> emailSend(@RequestBody EmailDTO emailDTO) {
        System.out.println("테스트 000");
        List<EmailObj> emailList = emailDTO.getEmailList();
        System.out.println("테스트 : " + emailDTO.getTitle());
        System.out.println("테스트 : " + emailDTO.getContent());
        System.out.println("==============================================");
        System.out.println("테스트 : " + emailList.get(0).getId());
        System.out.println("테스트 : " + emailList.get(0).getEmail());
        System.out.println("==============================================");
        // System.out.println("테스트 : " + emailList.get(1).getId());
        // System.out.println("테스트 : " + emailList.get(1).getEmail());

        String fromAddress = "aozp83@gmail.com";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(emailDTO.getEmailList().get(0).getEmail());
        message.setSubject(emailDTO.getTitle());
        message.setText(emailDTO.getContent());
        javaMailSender.send(message);
        return null;
    }
}
