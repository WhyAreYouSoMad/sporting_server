package shop.mtcoding.sporting_server.topic.stadium.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class StadiumCourtDTO {
    private Long id;
    private String title;
    private String content;
    private Integer capacity;
    private Integer courtPrice;

    private FileInfo fileInfo;

    @QueryProjection
    public StadiumCourtDTO(Long id, String title, String content, Integer capacity, Integer courtPrice,
            FileInfo fileInfo) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.capacity = capacity;
        this.courtPrice = courtPrice;
        this.fileInfo = fileInfo;
    }
}
