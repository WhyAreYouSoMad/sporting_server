package shop.mtcoding.sporting_server.admin.email;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.admin.email.dto.EmailDTO;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.exception.Exception500;
import shop.mtcoding.sporting_server.core.util.AdminEmailUtils;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdminEmailService {

    private final AdminEmailUtils adminEmailUtils;
    private final UserRepository userRepository;

    public void sendEmail(EmailDTO emailDTO) {
        List<Long> emailIdList = emailDTO.getEmailList().stream().map(emailObj -> emailObj.getId())
                .collect(Collectors.toList());
        List<User> emailEntityListPS = userRepository.findByIdIn(emailIdList);

        // 이메일 목록 유효성 검사
        if (emailEntityListPS.size() != emailDTO.getEmailList().size()) {
            throw new Exception400("Email 전송받을 유저 일부가 존재하지 않습니다.");
        }

        Map<Long, String> emailMap = emailEntityListPS.stream()
                .collect(Collectors.toMap(User::getId, User::getEmail));

        for (EmailDTO.EmailObj emailObj : emailDTO.getEmailList()) {
            String email = emailMap.get(emailObj.getId());
            if (email == null || !email.equals(emailObj.getEmail())) {
                throw new Exception400("잘못된 Email 주소가 전송되었습니다.  잘못된 이메일 : " + emailObj.getEmail());
            }
        }

        try {
            new Thread(() -> {
                adminEmailUtils.sendEmail(emailDTO);
            }).start();

        } catch (Exception e) {
            throw new Exception500("이메일 전송에 실패했습니다.");
        }

    }

}
