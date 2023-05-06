package shop.mtcoding.sporting_server.core.dummy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.PlayerFavoriteSport;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class DummyEntity {
    // User
    public User newPlayerUser(String username, String nickname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .nickname(nickname)
                .email(username + "@nate.com")
                .password(passwordEncoder.encode("1234"))
                .role("PLAYER")
                .status(UserStatus.일반회원)
                .build();
    }

    public User newCompanyUser(String username, String nickname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .nickname(nickname)
                .email(username + "@nate.com")
                .password(passwordEncoder.encode("1234"))
                .role("COMPNAY")
                .status(UserStatus.기업회원)
                .build();
    }

    // Info
    public CompanyInfo newCompanyInfo(User user, FileInfo fileInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return CompanyInfo.builder()
                .user(user)
                .businessNumber(null)
                .businessAddress(null)
                .tel(null)
                .ceo(null)
                .fileInfo(fileInfo)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public PlayerInfo newPlayerInfo(User user, FileInfo fileInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return PlayerInfo.builder()
                .user(user)
                .gender(null)
                .age(null)
                .address(null)
                .tel(null)
                .fileInfo(fileInfo)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // ProfileFile, FileInfo
    public ProfileFile newProfileFile(FileInfo fileInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return ProfileFile.builder()
                .fileInfo(fileInfo)
                .fileName("CompanyProfile/company.jpg")
                .fileUrl("https://3-sporting.s3.ap-northeast-2.amazonaws.com/CompanyProfile/company.jpg")
                .build();
    }

    public ProfileFile newPlayerProfileFile(FileInfo fileInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return ProfileFile.builder()
                .fileInfo(fileInfo)
                .fileName("PlayerProfile/player.jpg")
                .fileUrl("https://3-sporting.s3.ap-northeast-2.amazonaws.com/PlayerProfile/player.jpg")
                .build();
    }

    public FileInfo newFileInfo(FileInfoSource fileInfoSource) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return FileInfo.builder().type(fileInfoSource).build();
    }

    // Stadium, Court
    public Stadium newStadium(CompanyInfo companyInfo, SportCategory sportCategory) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return Stadium
                .builder()
                .companyInfo(companyInfo)
                .name("테스트 경기장")
                .address("부산시 연제구")
                .category(sportCategory)
                .status(StadiumStatus.운영중)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .createdAt(LocalDateTime.now())
                .build();
    }

    public StadiumCourt newStadiumCourt(Stadium stadium, FileInfo fileInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return StadiumCourt.builder()
                .stadium(stadium)
                .fileInfo(fileInfo)
                .title("코트제목(통합테스트)")
                .content("코트내용(통합테스트)")
                .capacity(10)
                .courtPrice(40000)
                .createdAt(LocalDateTime.now())
                .status(StadiumCourtStatus.등록대기)
                .build();
    }

    // SportCategory
    public SportCategory newSportCategory() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return SportCategory
                .builder()
                .sport("축구")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public PlayerFavoriteSport newFavoriteSport(SportCategory sportCategory, PlayerInfo playerInfo) {
        return PlayerFavoriteSport
                .builder()
                .category(sportCategory)
                .playerInfo(playerInfo)
                .build();

    }

}
