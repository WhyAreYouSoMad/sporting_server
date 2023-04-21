package shop.mtcoding.sporting_server.topic.player.dto;

import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PlayerUpdateFormOutDTO {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private PlayerInfoResponseDTO playerInfo;
    private List<PlayerFavoriteSportResponseDTO> playerFavoriteSport;

    @QueryProjection
    public PlayerUpdateFormOutDTO(Long id, String nickname, String email, String password) {
        this.id = id;
        if (nickname != null) {
            this.nickname = nickname;
        } else {
            this.nickname = email;
        }
        this.email = email;
        this.password = password;
    }

    public PlayerUpdateFormOutDTO(PlayerInfoResponseDTO playerInfo) {
        this.playerInfo = playerInfo;
    }

    public PlayerUpdateFormOutDTO(List<PlayerFavoriteSportResponseDTO> playerFavoriteSport) {
        this.playerFavoriteSport = playerFavoriteSport;
    }

}
