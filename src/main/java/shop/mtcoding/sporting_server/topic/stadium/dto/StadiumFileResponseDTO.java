package shop.mtcoding.sporting_server.topic.stadium.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StadiumFileResponseDTO {
    private Long id;
    private String fileUrl;

    @QueryProjection
    public StadiumFileResponseDTO(Long id, String fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }
}
