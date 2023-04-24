package shop.mtcoding.sporting_server.topic.stadium.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FileInfoResponseDTO {
    private Long id;
    private FileInfoSource type;

    @QueryProjection
    public FileInfoResponseDTO(Long id, FileInfoSource type) {
        this.id = id;
        this.type = type;
    }
}
