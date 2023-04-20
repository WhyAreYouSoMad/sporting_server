package shop.mtcoding.sporting_server.topic.stadium_court.dto;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.topic.stadium_court.StadiumCourtService;
import shop.mtcoding.sporting_server.topic.stadium_court.dto.StadiumCourtResponse.StadiumDTO;

import javax.persistence.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StadiumCourtResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class StadiumCourtOutDTO {

        private Long id;
        private StadiumDTO stadium;
        private FileInfoDTO fileInfo;
        private Integer courtPrice;
        private Integer capacity;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private StadiumCourtStatus status;

        public StadiumCourtOutDTO(StadiumCourt stadiumCourt) {
            this.id = stadiumCourt.getId();
            this.courtPrice = stadiumCourt.getCapacity();
            this.capacity = stadiumCourt.getCapacity();
            this.content = stadiumCourt.getContent();
            this.createdAt = stadiumCourt.getCreatedAt();
            this.updatedAt = stadiumCourt.getUpdatedAt();
            this.status = stadiumCourt.getStatus();
        }

    }

    @Getter
    @Setter
    public static class StadiumDTO {

        private Long id;
        private CompanyInfo companyInfo;
        private String name;
        private String description;
        private StadiumAddress address;
        private Double lat;
        private Double lon;
        private String tel;
        private SportCategory category;
        private LocalTime startTime;
        private LocalTime endTime;
        private FileInfo fileInfo;
        private StadiumStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

    }

    @Getter
    @Setter
    public static class FileInfoDTO {
        private Long id;
        private FileInfoSource type;

    }
}
