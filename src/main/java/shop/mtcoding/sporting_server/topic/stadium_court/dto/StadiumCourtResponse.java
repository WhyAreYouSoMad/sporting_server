package shop.mtcoding.sporting_server.topic.stadium_court.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
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
        private CourtFileDto courtFile = new CourtFileDto();
        private String title;
        private String content;
        private Integer capacity;
        @JsonIgnore
        private Integer courtPrice;
        private String price;
        private LocalDateTime createdAt;
        private StadiumCourtStatus status;

        public StadiumCourtOutDTO(StadiumCourt stadiumCourt, ProfileFile profileFile) {
            this.stadiumId = stadiumCourt.getStadium().getId();
            this.id = stadiumCourt.getId();
            this.courtFile.setId(profileFile.getId());
            this.courtFile.setFileName(profileFile.getFileName());
            this.courtFile.setFileUrl(profileFile.getFileUrl());
            this.title = stadiumCourt.getTitle();
            this.content = stadiumCourt.getContent();
            this.capacity = stadiumCourt.getCapacity();
            String price = String.format("%,d", stadiumCourt.getCourtPrice());
            this.price = price;
            this.createdAt = stadiumCourt.getCreatedAt();
            this.status = stadiumCourt.getStatus();
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class CourtFileDto {
            private Long id;
            private String fileName;
            private String fileUrl;
        }
    }
}