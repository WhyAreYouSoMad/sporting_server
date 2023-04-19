package shop.mtcoding.sporting_server.topic.player.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;

@EqualsAndHashCode
@NoArgsConstructor
@Setter
@Getter
public class PlayerFavoriteSportResponseDTO {
    private Long id;
    private String sport;

    @QueryProjection
    public PlayerFavoriteSportResponseDTO(Long id, String sport) {
        this.id = id;
        this.sport = sport;
    }

}
