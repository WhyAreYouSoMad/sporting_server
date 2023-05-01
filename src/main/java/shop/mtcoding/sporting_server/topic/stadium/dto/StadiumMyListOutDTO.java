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
public class StadiumMyListOutDTO {
    private Long id;
    private String sport;
    private String name;
    private StadiumFileResponseDTO sourceFile;

    @QueryProjection
    public StadiumMyListOutDTO(Long id, String sport, String name, StadiumFileResponseDTO stadiumFile) {
        this.id = id;
        this.sport = sport;
        this.name = name;
        this.sourceFile = stadiumFile;
    }

}
