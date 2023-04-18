package shop.mtcoding.sporting_server.topic.stadium.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
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
    private StadiumAddress address;
    private StadiumStatus status;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<CourtResponseDTO> court;

    public StadiumUpdateFomrOutDTO(Long id, String name, StadiumAddress address, StadiumStatus status,
            LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public StadiumUpdateFomrOutDTO(List<CourtResponseDTO> court) {
        this.court = court;
    }

}
