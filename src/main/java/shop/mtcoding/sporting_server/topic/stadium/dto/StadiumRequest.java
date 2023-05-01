package shop.mtcoding.sporting_server.topic.stadium.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.ValueOfEnum;
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;
import shop.mtcoding.sporting_server.core.enums.field.fk_fields.SportCategoryType;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;

public class StadiumRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class StadiumRegistrationInDTO {
        private String name;

        // @ValueOfEnum(enumClass = StadiumAddress.class, message = "주소 값 이상 (부산시, 서울시
        // 등으로 입력)")
        private String address;

        @ValueOfEnum(enumClass = SportCategoryType.class, message = "스포츠 카테고리 값 이상 (야구, 축구, 배구 등으로 입력)")
        private String category;

        public Stadium toEntity(CompanyInfo companyInfoPS, StadiumRegistrationInDTO stadiumRegistrationInDTO,
                SportCategory sportCategoryPS) {
            return Stadium
                    .builder()
                    .companyInfo(companyInfoPS)
                    .name(stadiumRegistrationInDTO.getName())
                    .address(StadiumAddress.valueOf(stadiumRegistrationInDTO.getAddress()))
                    .category(sportCategoryPS)
                    .status(StadiumStatus.운영중)
                    .startTime(LocalTime.of(9, 0))
                    .endTime(LocalTime.of(18, 0))
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class StadiumUpdateInDTO {
        private String id;
        @ValueOfEnum(enumClass = StadiumAddress.class, message = "주소 값 이상 (부산시, 서울시 등으로 입력)")
        private String address;
        @ValueOfEnum(enumClass = StadiumStatus.class, message = "status 값 이상 (운영중, 휴업, 등록대기, 폐업으로 입력)")
        private String status;
        private String startTime;
        private String endTime;
        @ValueOfEnum(enumClass = SportCategoryType.class, message = "스포츠 카테고리 값 이상 (야구, 축구, 배구 등으로 입력)")
        private String category;
        private StadiumFileDTO sourceFile;
        private List<CourtDTO> courts;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class StadiumFileDTO {
            private String id;
            private String fileBase64;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @EqualsAndHashCode
        public static class CourtDTO {
            private String id;
            private String title;
            private String content;
            private String capacity;
            private String courtPrice;
            private CourtFileDTO sourceFile;

            @Getter
            @Setter
            @NoArgsConstructor
            @AllArgsConstructor
            @EqualsAndHashCode
            public static class CourtFileDTO {
                private String id;
                private String fileBase64;
            }
        }
    }

}
