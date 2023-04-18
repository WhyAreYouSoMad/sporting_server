package shop.mtcoding.sporting_server.core.util;

import org.springframework.http.ResponseEntity;

import shop.mtcoding.sporting_server.core.enums.field.fk_fields.SportCategoryType;
import shop.mtcoding.sporting_server.core.exception.Exception400;

public class StadiumUtils {

    public static void keywordValidiationCheck(String keyword) {
        if (keyword == null) {
            throw new Exception400("카테고리 keyword가 필요합니다.");
        }

        boolean isValid = false;
        for (SportCategoryType sportCategoryType : SportCategoryType.values()) {
            if (sportCategoryType.name().equals(keyword)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new Exception400("카테고리 keyword가 적합하지 않습니다. (야구, 농구, 축구 등으로 입력)");
        }
    }
}
