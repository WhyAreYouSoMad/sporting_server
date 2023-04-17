package shop.mtcoding.sporting_server.topic.board.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.ValueOfEnum;
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;

public class BoardRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class StadiumRegistrationInDTO {
        private String name;

        @ValueOfEnum(enumClass = StadiumAddress.class, message = "공지사항 상태 값이 이상이 있습니다. 확인해주세요.")
        private String address;
        private String category;
    }
}
