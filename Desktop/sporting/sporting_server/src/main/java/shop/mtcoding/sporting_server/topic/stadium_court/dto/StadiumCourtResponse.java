package shop.mtcoding.sporting_server.topic.stadium_court.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

public class StadiumCourtResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class StadiumCourtOutDTO {

        private Long stadiumId;
        private Long id;
        private FileInfo fileInfo;
        private String title;
        private String content;
        private Integer capacity;
        private Integer courtPrice;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private StadiumCourtStatus status;

        public StadiumCourtOutDTO(StadiumCourt stadiumCourt) {
            this.stadiumId = stadiumCourt.getStadium().getId();
            this.id = stadiumCourt.getId();
            this.fileInfo = stadiumCourt.getFileInfo();
            this.title = stadiumCourt.getTitle();
            this.content = stadiumCourt.getContent();
            this.capacity = stadiumCourt.getCapacity();
            this.courtPrice = stadiumCourt.getCourtPrice();
            this.createdAt = stadiumCourt.getCreatedAt();
            this.updatedAt = stadiumCourt.getUpdatedAt();
            this.status = stadiumCourt.getStatus();
        }

    }
}