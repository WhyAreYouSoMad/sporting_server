package shop.mtcoding.sporting_server.topic.stadium.dto;

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

    public CourtResponseDTO(Long id, String title, String content, Integer capacity, Integer courtPrice, String sport) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.capacity = capacity;
        this.courtPrice = courtPrice;
        this.sport = sport;
    }

}
