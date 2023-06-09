package shop.mtcoding.sporting_server.topic.stadium.dto;

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
public class StadiumCourtDTO {
    private Long id;
    private String title;
    private String content;
    private Integer capacity;
    // @JsonIgnore
    private Integer price;
    // private String price;
    private CourtFileResponseDto sourceFile;
    private List<Boolean> reservationTime;

    @QueryProjection
    public StadiumCourtDTO(Long id, String title, String content, Integer capacity, Integer courtPrice,
            CourtFileResponseDto courtFile) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.capacity = capacity;
        // String price = String.format("%,d", courtPrice);
        this.price = courtPrice;
        this.sourceFile = courtFile;
    }
}
