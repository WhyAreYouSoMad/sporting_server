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
    // @JsonIgnore
    private Integer price;
    // private String price;
    @JsonIgnore
    private Long fileId;
    @JsonIgnore
    private String fileUrl;

    private StadiumFileResponseDTO sourceFile;

    public StadiumListOutDTO(Long id, String sport, String name, Integer courtPrice, Long fileId,
            String fileUrl) {
        this.id = id;
        this.sport = sport;
        this.name = name;
        // String price = String.format("%,d", courtPrice);
        this.price = courtPrice;
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.sourceFile = new StadiumFileResponseDTO(fileId, fileUrl);
    }

}
