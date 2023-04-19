package shop.mtcoding.sporting_server.topic.player.dto;

import com.querydsl.core.annotations.QueryProjection;

public class PlayerUpdateFormOutDTO {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private PlayerInfoResponseDTO playerInfo;
    private PlayerFavoriteSportResponseDTO playerFavoriteSport;

    @QueryProjection
    public PlayerUpdateFormOutDTO(Long id, String nickname, String email, String password) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public PlayerUpdateFormOutDTO(PlayerInfoResponseDTO playerInfo) {
        this.playerInfo = playerInfo;
    }

    public PlayerUpdateFormOutDTO(PlayerFavoriteSportResponseDTO playerFavoriteSport) {
        this.playerFavoriteSport = playerFavoriteSport;
    }

}
