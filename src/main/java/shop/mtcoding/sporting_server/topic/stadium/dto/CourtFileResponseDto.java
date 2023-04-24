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
public class CourtFileResponseDto {
    private Long id;
    private String fileUrl;

    @QueryProjection
    public CourtFileResponseDto(Long id, String fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }

}
