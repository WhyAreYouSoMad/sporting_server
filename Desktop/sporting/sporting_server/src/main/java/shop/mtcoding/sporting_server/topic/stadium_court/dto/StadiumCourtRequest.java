package shop.mtcoding.sporting_server.topic.stadium_court.dto;

import lombok.*;
import shop.mtcoding.sporting_server.core.enums.ValueOfEnum;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.fk_fields.SportCategoryType;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

import java.time.LocalDateTime;

public class StadiumCourtRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class StadiumCourtInDTO {

        private FileInfo fileInfo;
        private Integer courtPrice;
        private Integer capacity;
        private String content;
        private LocalDateTime createdAt;
        private StadiumCourtStatus status;

        public StadiumCourt toEntity(Stadium stadiumPS) {
            return StadiumCourt.builder()

                    .stadium(stadiumPS)
                    .fileInfo(fileInfo)
                    .courtPrice(courtPrice)
                    .capacity(capacity)
                    .content(content)
                    .createdAt(LocalDateTime.now())
                    .status(StadiumCourtStatus.등록대기)
                    .build();

        }

    }
}