package shop.mtcoding.sporting_server.topic.stadium.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;

public class StadiumResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class StadiumRegistrationOutDTO {
        private Long id;
        private String name;
        private String address;
        private String sport;
        private String startTime;
        private String endTime;
        private StadiumFileDto sourceFile = new StadiumFileDto();

        public StadiumRegistrationOutDTO(Long id, String name, String address, String sport, String startTime,
                String endTime, StadiumFileDto stadiumFile) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.sport = sport;
            this.startTime = startTime;
            this.endTime = endTime;
            this.sourceFile = stadiumFile;
        }

        public StadiumRegistrationOutDTO(Stadium stadiumPS, ProfileFile profileFile) {
            this.id = stadiumPS.getId();
            this.name = stadiumPS.getName();
            this.address = stadiumPS.getAddress().toString();
            this.sport = stadiumPS.getCategory().getSport();
            this.startTime = stadiumPS.getStartTime().toString();
            this.endTime = stadiumPS.getEndTime().toString();
            this.sourceFile.setId(profileFile.getId());
            // this.sourceFile.setFileName(profileFile.getFileName());
            this.sourceFile.setFileUrl(profileFile.getFileUrl());
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class StadiumFileDto {
            private Long id;
            @JsonIgnore
            private String fileName;
            private String fileUrl;
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class StadiumUpdateOutDTO {
        private Long id;
        private String address;
        private String status;
        private String startTime;
        private String endTime;
        private String sport;
        private StadiumFileOutDTO sourceFile = new StadiumFileOutDTO();
        private List<CourtOutDTO> courts = new ArrayList<>();

        public StadiumUpdateOutDTO(Long id, String address, String status, String startTime, String endTime,
                String sport, StadiumFileOutDTO stadiumFile, List<CourtOutDTO> courtList) {
            this.id = id;
            this.address = address;
            this.status = status;
            this.startTime = startTime;
            this.endTime = endTime;
            this.sport = sport;
            this.sourceFile = stadiumFile;
            this.courts = courtList;
        }

        public StadiumUpdateOutDTO(Stadium stadiumPS, ProfileFile stadiumFile, List<CourtOutDTO> courtList) {
            this.id = stadiumPS.getId();
            this.address = stadiumPS.getAddress();
            this.status = stadiumPS.getStatus().name();
            this.startTime = stadiumPS.getStartTime().toString();
            this.endTime = stadiumPS.getEndTime().toString();
            this.sport = stadiumPS.getCategory().getSport();
            this.sourceFile.id = stadiumFile.getId();
            // this.sourceFile.fileName = stadiumFile.getFileName();
            this.sourceFile.fileUrl = stadiumFile.getFileUrl();
            this.courts = courtList;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class StadiumFileOutDTO {
            private Long id;
            @JsonIgnore
            private String fileName;
            private String fileUrl;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @EqualsAndHashCode
        public static class CourtOutDTO {
            private Long id;
            private String title;
            private String content;
            private Integer capacity;
            private Integer price;
            private CourtFileOutDTO sourceFile = new CourtFileOutDTO();

            public CourtOutDTO(Long id, String title, String content, Integer capacity, String courtPrice,
                    CourtFileOutDTO courtFile) {
                this.id = id;
                this.title = title;
                this.content = content;
                this.capacity = capacity;
                this.price = Integer.parseInt(courtPrice);
                this.sourceFile = courtFile;
            }

            public CourtOutDTO(StadiumCourt stadiumCourtPS, ProfileFile courtFilePS) {
                this.id = stadiumCourtPS.getId();
                this.title = stadiumCourtPS.getTitle();
                this.content = stadiumCourtPS.getContent();
                this.capacity = stadiumCourtPS.getCapacity();
                this.price = stadiumCourtPS.getCourtPrice();
                this.sourceFile.id = courtFilePS.getId();
                // this.sourceFile.fileName = courtFilePS.getFileName();
                this.sourceFile.fileUrl = courtFilePS.getFileUrl();

            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @EqualsAndHashCode
            public static class CourtFileOutDTO {
                private Long id;
                @JsonIgnore
                private String fileName;
                private String fileUrl;
            }
        }
    }

}
