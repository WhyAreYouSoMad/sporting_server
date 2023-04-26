package shop.mtcoding.sporting_server.topic.player.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
@Setter
@Getter
public class PlayerFileResponseDTO {
    private Long id;
    private String fileUrl;

    @QueryProjection
    public PlayerFileResponseDTO(Long id, String fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }
}
