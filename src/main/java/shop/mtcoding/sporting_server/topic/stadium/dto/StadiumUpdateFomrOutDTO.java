package shop.mtcoding.sporting_server.topic.stadium.dto;

import java.time.LocalTime;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StadiumUpdateFomrOutDTO {

    private Long id;
    private String name;
    private String address;
    private StadiumStatus status;
    private LocalTime startTime;
    private LocalTime endTime;
    private StadiumFileResponseDTO sourceFile;
    private List<CourtResponseDTO> courts;

    @QueryProjection
    public StadiumUpdateFomrOutDTO(Long id, String name, String address,
            StadiumStatus status,
            LocalTime startTime, LocalTime endTime, StadiumFileResponseDTO stadiumFile) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sourceFile = stadiumFile;
    }

    public StadiumUpdateFomrOutDTO(List<CourtResponseDTO> court) {
        this.courts = court;
    }

}
