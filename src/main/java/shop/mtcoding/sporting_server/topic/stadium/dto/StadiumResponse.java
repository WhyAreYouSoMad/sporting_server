package shop.mtcoding.sporting_server.topic.stadium.dto;

import java.util.ArrayList;
import java.util.List;

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
        private String category;
        private String startTime;
        private String endTime;
        private StadiumFileDto stadiumFile = new StadiumFileDto();

        public StadiumRegistrationOutDTO(Long id, String name, String address, String category, String startTime,
                String endTime, StadiumFileDto stadiumFile) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.category = category;
            this.startTime = startTime;
            this.endTime = endTime;
            this.stadiumFile = stadiumFile;
        }

        public StadiumRegistrationOutDTO(Stadium stadiumPS, ProfileFile profileFile) {
            this.id = stadiumPS.getId();
            this.name = stadiumPS.getName();
            this.address = stadiumPS.getAddress().toString();
            this.category = stadiumPS.getCategory().getSport();
            this.startTime = stadiumPS.getStartTime().toString();
            this.endTime = stadiumPS.getEndTime().toString();
            this.stadiumFile.setId(profileFile.getId());
            this.stadiumFile.setFileName(profileFile.getFileName());
            this.stadiumFile.setFileUrl(profileFile.getFileUrl());
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class StadiumFileDto {
            private Long id;
            private String fileName;
            private String fileUrl;
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class StadiumUpdateOutDTO {
        private String id;
        private String address;
        private String status;
        private String startTime;
        private String endTime;
        private String category;
        private StadiumFileOutDTO stadiumFile = new StadiumFileOutDTO();
        private List<CourtOutDTO> courtList = new ArrayList<>();

        public StadiumUpdateOutDTO(String id, String address, String status, String startTime, String endTime,
                String category, StadiumFileOutDTO stadiumFile, List<CourtOutDTO> courtList) {
            this.id = id;
            this.address = address;
            this.status = status;
            this.startTime = startTime;
            this.endTime = endTime;
            this.category = category;
            this.stadiumFile = stadiumFile;
            this.courtList = courtList;
        }

        public StadiumUpdateOutDTO(Stadium stadiumPS, ProfileFile stadiumFile, List<CourtOutDTO> courtList) {
            this.id = Long.toString(stadiumPS.getId());
            this.address = stadiumPS.getAddress().name();
            this.status = stadiumPS.getStatus().name();
            this.startTime = stadiumPS.getStartTime().toString();
            this.endTime = stadiumPS.getEndTime().toString();
            this.category = stadiumPS.getCategory().getSport();
            this.stadiumFile.id = Long.toString(stadiumFile.getId());
            this.stadiumFile.fileName = stadiumFile.getFileName();
            this.stadiumFile.fileUrl = stadiumFile.getFileUrl();
            this.courtList = courtList;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class StadiumFileOutDTO {
            private String id;
            private String fileName;
            private String fileUrl;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @EqualsAndHashCode
        public static class CourtOutDTO {
            private String id;
            private String title;
            private String content;
            private String capacity;
            private String courtPrice;
            private CourtFileOutDTO courtFile = new CourtFileOutDTO();

            public CourtOutDTO(String id, String title, String content, String capacity, String courtPrice,
                    CourtFileOutDTO courtFile) {
                this.id = id;
                this.title = title;
                this.content = content;
                this.capacity = capacity;
                this.courtPrice = courtPrice;
                this.courtFile = courtFile;
            }

            public CourtOutDTO(StadiumCourt stadiumCourtPS, ProfileFile courtFilePS) {
                this.id = Long.toString(stadiumCourtPS.getId());
                this.title = stadiumCourtPS.getTitle();
                this.content = stadiumCourtPS.getContent();
                this.capacity = stadiumCourtPS.getCapacity().toString();
                this.courtPrice = stadiumCourtPS.getCourtPrice().toString();
                this.courtFile.id = Long.toString(courtFilePS.getId());
                this.courtFile.fileName = courtFilePS.getFileName();
                this.courtFile.fileUrl = courtFilePS.getFileUrl();

            }

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @EqualsAndHashCode
            public static class CourtFileOutDTO {
                private String id;
                private String fileName;
                private String fileUrl;
            }
        }
    }

}
