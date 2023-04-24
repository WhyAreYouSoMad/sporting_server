package shop.mtcoding.sporting_server.topic.player;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.enums.role.RoleType;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.exception.Exception403;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.repository.PlayerFavoriteSportRepository;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.player_info.repository.PlayerInfoRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerResponse;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerUpdateFormOutDTO;

@RequiredArgsConstructor
@Service
public class PlayerService {
    private final UserRepository userRepository;
    private final PlayerInfoRepository playerInfoRepository;
    private final PlayerFavoriteSportRepository playerFavoriteSportRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 1. 트랜잭션 관리
     * 2. 영속성 객체 변경감지
     * 3. RequestDTO 요청받기
     * 4. 비지니스 로직 처리하기
     * 5. ResponseDTO 응답하기
     */
    @Transactional
    public PlayerResponse.JoinOutDto 회원가입(PlayerRequest.JoinInDTO joinDTO) {
        // rawPassword : 입력 Password값 저장
        String rawPassword = joinDTO.getPassword();
        Optional<User> emailCheck = userRepository.findByEmail(joinDTO.getEmail());
        if (emailCheck.isPresent()) {
            throw new Exception400("동일한 email이 있습니다");
        }
        // encPassword : BCryptPasswordEncoder로 60Byte 암호화
        // 암호화 결과 : $2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW
        String encPassword = passwordEncoder.encode(rawPassword);
        joinDTO.setPassword(encPassword);
        joinDTO.setRole(RoleType.PLAYER.toString());

        // JPA 사용에 있어 영속성 컨텍스트
        User userPS = userRepository.save(joinDTO.toEntity());
        return new PlayerResponse.JoinOutDto(userPS);
    }

    public PlayerUpdateFormOutDTO getUpdateForm(Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new Exception400("존재하지 않는 유저 입니다");
        });
        if (!user.getId().equals(id)) {
            throw new Exception403("수정 권한이 없습니다");
        }

        PlayerUpdateFormOutDTO playerUpdateFormOutDTO = userRepository.findByUserId(userId);
        playerUpdateFormOutDTO.setPlayerInfo(playerInfoRepository.findplayerInfoByuserId(userId));
        playerUpdateFormOutDTO.setPlayerFavoriteSport(playerFavoriteSportRepository.findSportByuserId(userId));
        return playerUpdateFormOutDTO;
    }

}
