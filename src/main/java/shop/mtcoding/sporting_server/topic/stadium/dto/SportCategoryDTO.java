package shop.mtcoding.sporting_server.topic.stadium.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SportCategoryDTO {

    private Long id;
    private String sport;

    @QueryProjection
    public SportCategoryDTO(Long id, String sport) {
        this.id = id;
        this.sport = sport;
    }
}
