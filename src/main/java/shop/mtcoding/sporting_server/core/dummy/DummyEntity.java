package shop.mtcoding.sporting_server.core.dummy;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class DummyEntity {
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

    public ProfileFile newProfileFile(FileInfo fileInfo) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return ProfileFile.builder()
                .fileInfo(fileInfo)
                .fileName("CompanyProfile/company.jpg")
                .fileUrl("https://3-sporting.s3.ap-northeast-2.amazonaws.com/CompanyProfile/company.jpg")
                .build();
    }

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

    public FileInfo newFileInfo() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return FileInfo.builder().type(FileInfoSource.기업프로필).build();
    }

}
