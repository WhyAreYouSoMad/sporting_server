package shop.mtcoding.sporting_server.topic.stadium.dto;

import java.time.LocalTime;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StadiumDetailOutDTO {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;
    private Double lat;
    private Double lon;
    private String address;
    private SportCategoryDTO category;
    private StadiumFileResponseDTO sourceFile;
    private List<StadiumCourtDTO> courts;

    public StadiumDetailOutDTO(List<StadiumCourtDTO> stadiumCourt) {
        this.courts = stadiumCourt;
    }

    public StadiumDetailOutDTO(SportCategoryDTO category) {
        this.category = category;
    }

    @QueryProjection
    public StadiumDetailOutDTO(Long id, LocalTime startTime, LocalTime endTime, String name, Double lat, Double lon,
            String address, StadiumFileResponseDTO stadiumFile) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.sourceFile = stadiumFile;
    }

}
