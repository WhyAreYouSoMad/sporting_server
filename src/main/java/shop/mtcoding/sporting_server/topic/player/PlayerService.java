package shop.mtcoding.sporting_server.topic.player;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAddress;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoGender;
import shop.mtcoding.sporting_server.core.enums.role.RoleType;
import shop.mtcoding.sporting_server.core.exception.Exception400;
import shop.mtcoding.sporting_server.core.util.BASE64DecodedMultipartFile;
import shop.mtcoding.sporting_server.core.util.S3Utils;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.file.repository.ProfileFileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.PlayerFavoriteSport;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.repository.PlayerFavoriteSportRepository;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.player_info.repository.PlayerInfoRepository;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.sport_category.repository.SportCategoryRepository;
import shop.mtcoding.sporting_server.modules.user.entity.User;
import shop.mtcoding.sporting_server.modules.user.repository.UserRepository;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest.PlayerUpdateInDTO;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest.PlayerUpdateInDTO.PlayerFavoriteSportDTO;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerResponse;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerResponse.PlayerUpdateOutDTO;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerResponse.PlayerUpdateOutDTO.PlayerFavoriteSportOutDTO;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerUpdateFormOutDTO;

@RequiredArgsConstructor
@Service
public class PlayerService {
        private final SportCategoryRepository sportCategoryRepository;
        private final AmazonS3Client amazonS3Client;
        private final UserRepository userRepository;
        private final PlayerInfoRepository playerInfoRepository;
        private final PlayerFavoriteSportRepository playerFavoriteSportRepository;
        private final BCryptPasswordEncoder passwordEncoder;
        private final ProfileFileRepository profileFileRepository;
        private final FileInfoRepository fileInfoRepository;

        @Value("${bucket}")
        private String bucket;
        @Value("${static}")
        private String staticRegion;

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

                // User 엔티티 생성 및 저장
                User user = joinDTO.toEntity();
                User userPS = userRepository.save(user);

                FileInfo fileInfo = new FileInfo();
                fileInfo.setType(FileInfoSource.플레이어프로필);
                FileInfo fileInfoPS = fileInfoRepository.save(fileInfo);

                ProfileFile profileFile = new ProfileFile();
                profileFile.setFileInfo(fileInfoPS);
                profileFile.setFileName("PlayerProfile/player.jpg");
                profileFile.setFileUrl("https://3-sporting.s3.ap-northeast-2.amazonaws.com/PlayerProfile/player.jpg");
                ProfileFile profileFilePS = profileFileRepository.save(profileFile);

                ;
                // PlayerInfo 엔티티 생성
                PlayerInfo playerInfo = PlayerInfo.builder()
                                .user(userPS)
                                .gender(null)
                                .age(null)
                                .address(null)
                                .tel(null)
                                .fileInfo(fileInfoPS)
                                .updatedAt(LocalDateTime.now())
                                .build();

                // playerInfo 엔티티 저장
                playerInfoRepository.save(playerInfo);

                // JPA 사용에 있어 영속성 컨텍스트
                return new PlayerResponse.JoinOutDto(userPS);
        }

        @Transactional
        public PlayerUpdateFormOutDTO getUpdateForm(Long id) {
                userRepository.findById(id).orElseThrow(() -> {
                        throw new Exception400("존재하지 않는 유저 입니다");
                });
                PlayerUpdateFormOutDTO playerUpdateFormOutDTO = userRepository.findByUserId(id);
                playerUpdateFormOutDTO.setPlayerInfo(playerInfoRepository.findplayerInfoByuserId(id));
                playerUpdateFormOutDTO.setPlayerFavoriteSport(playerFavoriteSportRepository.findSportByuserId(id));
                return playerUpdateFormOutDTO;
        }

        @Transactional
        public PlayerUpdateOutDTO update(Long id, PlayerUpdateInDTO playerUpdateInDTO) throws IOException {
                User userPS = userRepository.findById(id).orElseThrow(() -> {
                        throw new Exception400("존재하지 않는 유저입니다.");
                });

                String rawPassword = playerUpdateInDTO.getPassword();
                String encPassword = passwordEncoder.encode(rawPassword);

                userPS.setNickname(playerUpdateInDTO.getNickname());
                userPS.setPassword(encPassword);

                PlayerInfo playerInfo = playerInfoRepository.findByUserId(userPS.getId()).orElseThrow(() -> {
                        throw new Exception400("존재하지 않는 Player 입니다.");
                });

                playerInfo.setTel(playerUpdateInDTO.getTel());
                playerInfo.setGender(PlayerInfoGender.valueOf(playerUpdateInDTO.getGender()));
                playerInfo.setAge(PlayerInfoAge.valueOf(playerUpdateInDTO.getAge()));
                playerInfo.setAddress(playerUpdateInDTO.getAddress());

                ProfileFile playerProfileFilePS = profileFileRepository.findById(playerInfo.getFileInfo().getId())
                                .orElseThrow(() -> {
                                        throw new Exception400("ProfileFile이 존재하지 않습니다.");
                                });

                Boolean sizeCheck = S3Utils.updateProfileCheck_Player(playerProfileFilePS,
                                playerUpdateInDTO.getSourceFile().getFileBase64(), bucket, staticRegion);

                if (!sizeCheck) {
                        MultipartFile multipartFile2 = BASE64DecodedMultipartFile
                                        .convertBase64ToMultipartFile(
                                                        playerUpdateInDTO.getSourceFile().getFileBase64());

                        List<String> nameAndUrl = S3Utils.uploadFile(multipartFile2, "PlayerProfile", bucket,
                                        amazonS3Client);
                        playerProfileFilePS.setFileName(nameAndUrl.get(0));
                        playerProfileFilePS.setFileUrl(nameAndUrl.get(1));
                }

                // PlayerInfo playerInfo =
                // playerInfoRepository.findById(playerInfoPS.getUser().getId()).orElseThrow(()
                // -> {
                // throw new Exception400("유저 정보가 존재하지 않습니다.");
                // });

                List<PlayerFavoriteSportDTO> playerFavoriteSportDTOs = playerUpdateInDTO.getSportList(); // DTO 리스트
                List<String> sportList = playerFavoriteSportDTOs.stream()
                                .map(PlayerFavoriteSportDTO::getSport)
                                .collect(Collectors.toList());
                // List<String> sportList = new ArrayList<>(Arrays.asList("축구", "야구")); //
                // 요청으로부터 받은 스포츠 종목 리스트
                List<SportCategory> sportCategories = sportCategoryRepository.findAllBySportIn(sportList);

                List<Long> sportCategoryIds = sportCategories.stream()
                                .map(SportCategory::getId)
                                .collect(Collectors.toList());

                List<PlayerFavoriteSport> playerFavoriteSportListPS = playerFavoriteSportRepository
                                .findAllByPlayerInfo(playerInfo);

                List<Long> favoriteSportCategoryIdsPS = playerFavoriteSportListPS.stream()
                                .map(playerFavoriteSportPS -> playerFavoriteSportPS.getCategory().getId())
                                .collect(Collectors.toList());

                // sportCategoryIds : DTO의 categoryID값
                List<Long> idsToInsert = sportCategoryIds.stream()
                                // DB에 저장된 category값(1,2,3)에 sportCategoryIds(4,6)가 없으면 insert
                                .filter(sportCategoryId -> !favoriteSportCategoryIdsPS.contains(sportCategoryId))
                                .collect(Collectors.toList());

                // favoriteSportCategoryIdsPS : DB의 categoryID값
                List<Long> idsToDelete = favoriteSportCategoryIdsPS.stream()
                                // DTO에 없는 값이 DB에 있으면 delete
                                .filter(favoriteSportCategoryIdPS -> !sportCategoryIds
                                                .contains(favoriteSportCategoryIdPS))
                                .collect(Collectors.toList());

                // Insert
                if (!idsToInsert.isEmpty()) {
                        List<PlayerFavoriteSport> playerFavoriteSportsToInsert = idsToInsert.stream()
                                        .map(sportCategoryId -> {
                                                SportCategory sportCategory = sportCategoryRepository
                                                                .findById(sportCategoryId)
                                                                .orElseThrow(() -> new Exception400(
                                                                                "Sport category not found"));
                                                return PlayerFavoriteSport.builder()
                                                                .playerInfo(playerInfo)
                                                                .category(sportCategory)
                                                                .build();
                                        })
                                        .collect(Collectors.toList());

                        playerFavoriteSportRepository.saveAll(playerFavoriteSportsToInsert);
                }
                // Delete
                if (!idsToDelete.isEmpty()) {
                        playerFavoriteSportRepository.deleteByPlayerInfoIdAndCategoryIdIn(playerInfo.getId(),
                                        idsToDelete);
                }

                //
                List<PlayerFavoriteSport> playerFavoriteSports = playerFavoriteSportRepository
                                .findFavoriteSportByPlayerInfo(playerInfo);

                List<PlayerFavoriteSportOutDTO> playerFavoriteSportOutDTO = new ArrayList<>();
                for (PlayerFavoriteSport playerFavoriteSport : playerFavoriteSports) {
                        playerFavoriteSportOutDTO.add(new PlayerFavoriteSportOutDTO(playerFavoriteSport));
                }

                PlayerUpdateOutDTO playerUpdateOutDTO = new PlayerUpdateOutDTO(userPS, playerInfo, playerProfileFilePS,
                                playerFavoriteSportOutDTO);

                return playerUpdateOutDTO;
        }

}
