package shop.mtcoding.sporting_server.topic.stadium.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CourtResponseDTO {

    private Long id;
    private String title;
    private String content;
    private Integer capacity;
    private Integer courtPrice;
    private String sport;

    // private String profileURL;
    private CourtFileResponseDto courtFile;

    @QueryProjection
    public CourtResponseDTO(Long id, String title, String content, Integer capacity,
            Integer courtPrice, String sport, CourtFileResponseDto courtFile) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.capacity = capacity;
        this.courtPrice = courtPrice;
        this.sport = sport;
        this.courtFile = courtFile;
    }

    // @QueryProjection
    // public CourtResponseDTO(CourtFileResponseDto courtFile) {
    // this.courtFile = courtFile;
    // }

}
