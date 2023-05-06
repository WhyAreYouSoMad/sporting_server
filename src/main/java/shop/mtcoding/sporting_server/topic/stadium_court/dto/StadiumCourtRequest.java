package shop.mtcoding.sporting_server.topic.stadium_court.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

public class StadiumCourtRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class StadiumCourtInDTO {

        private String courtProfile;
        private String title;
        private String content;
        private Integer capacity;
        private Integer courtPrice;

        public StadiumCourt toEntity(Stadium stadiumPS, StadiumCourtInDTO stadiumCourtInDTO, FileInfo fileInfo) {
            return StadiumCourt.builder()
                    .stadium(stadiumPS)
                    .fileInfo(fileInfo)
                    .title(title)
                    .content(content)
                    .capacity(capacity)
                    .courtPrice(courtPrice)
                    .createdAt(LocalDateTime.now())
                    .status(StadiumCourtStatus.등록대기)
                    .build();

        }

    }
}