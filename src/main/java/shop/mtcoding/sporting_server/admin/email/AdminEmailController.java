package shop.mtcoding.sporting_server.admin.email;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.email.dto.EmailDTO;
import shop.mtcoding.sporting_server.core.dto.ResponseDto;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminEmailController {
    private final AdminEmailService adminEmailService;

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDTO emailDTO) {
        adminEmailService.sendEmail(emailDTO);

        return ResponseEntity.ok().body(new ResponseDto<>());
    }
}
