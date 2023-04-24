package shop.mtcoding.sporting_server.topic.stadium.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;

public class StadiumResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class StadiumRegistrationOutDTO {
        private Long id;
        private String name;
        private String address;
        private String category;
        private String startTime;
        private String endTime;

        public StadiumRegistrationOutDTO(Stadium stadiumPS) {
            this.id = stadiumPS.getId();
            this.name = stadiumPS.getName();
            this.address = stadiumPS.getAddress().toString();
            this.category = stadiumPS.getCategory().getSport();
            this.startTime = stadiumPS.getStartTime().toString();
            this.endTime = stadiumPS.getEndTime().toString();
        }

    }

}
