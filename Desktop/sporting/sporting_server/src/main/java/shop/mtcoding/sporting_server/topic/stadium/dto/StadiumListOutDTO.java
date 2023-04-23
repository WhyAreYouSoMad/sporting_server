package shop.mtcoding.sporting_server.topic.stadium.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StadiumListOutDTO {
    private Long id;
    private String sport;
    private String name;
    private Integer courtPrice;
    @JsonIgnore
    private Long fileId;
    @JsonIgnore
    private String fileUrl;

    private StadiumFileResponseDTO stadiumFile;

    public StadiumListOutDTO(Long id, String sport, String name, Integer courtPrice, Long fileId, String fileUrl) {
        this.id = id;
        this.sport = sport;
        this.name = name;
        this.courtPrice = courtPrice;
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.stadiumFile = new StadiumFileResponseDTO(fileId, fileUrl);
    }

}
