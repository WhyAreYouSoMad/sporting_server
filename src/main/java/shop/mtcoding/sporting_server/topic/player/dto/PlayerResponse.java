package shop.mtcoding.sporting_server.topic.player.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.util.MyDateUtils;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.player_favorite_sport.entity.PlayerFavoriteSport;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.user.entity.User;

public class PlayerResponse {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class JoinOutDto {
        private Long id;
        private String email;
        private String role;
        private String status;
        private String createdAt;

        public JoinOutDto(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.role = user.getRole();
            this.status = user.getStatus().toString();
            this.createdAt = MyDateUtils.toStringFormat(user.getCreatedAt());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class PlayerUpdateOutDTO {
        private Long id;
        private String nickname;
        private String password;

        private String tel;
        private String gender;
        private String age;
        private String address;
        private PlayerFileDTO sourceFile = new PlayerFileDTO();

        private List<PlayerFavoriteSportOutDTO> sportList = new ArrayList<>();

        public PlayerUpdateOutDTO(Long id, String nickname, String password, String tel, String gender, String age,
                String address, PlayerFileDTO playerFile, List<PlayerFavoriteSportOutDTO> sportList) {
            this.id = id;
            this.nickname = nickname;
            this.password = password;
            this.tel = tel;
            this.gender = gender;
            this.age = age;
            this.address = address;
            this.sourceFile = playerFile;
            this.sportList = sportList;
        }

        public PlayerUpdateOutDTO(User userPS, PlayerInfo playerInfoPS, ProfileFile playerProfilFilePS,
                List<PlayerFavoriteSportOutDTO> sportList) {
            this.id = userPS.getId();
            this.nickname = userPS.getNickname();
            this.password = userPS.getPassword();
            this.tel = playerInfoPS.getTel();
            this.gender = playerInfoPS.getGender().toString();
            this.age = playerInfoPS.getAge().toString();
            this.address = playerInfoPS.getAddress().toString();
            this.sourceFile.id = playerProfilFilePS.getId();
            this.sourceFile.fileBase64 = playerProfilFilePS.getFileUrl();
            this.sportList = sportList;
        }

        @EqualsAndHashCode
        @NoArgsConstructor
        @Setter
        @Getter
        public static class PlayerFavoriteSportOutDTO {
            private Long id;
            private String sport;

            public PlayerFavoriteSportOutDTO(Long id, String sport) {
                this.id = id;
                this.sport = sport;

            }

            public PlayerFavoriteSportOutDTO(PlayerFavoriteSport playerFavoriteSportPS) {
                this.id = playerFavoriteSportPS.getId();
                this.sport = playerFavoriteSportPS.getCategory().getSport();
            }

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class PlayerFileDTO {
            private Long id;
            @JsonIgnore
            private String fileName;
            private String fileBase64;

        }

    }
}
