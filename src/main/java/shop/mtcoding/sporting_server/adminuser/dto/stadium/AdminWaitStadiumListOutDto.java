package shop.mtcoding.sporting_server.adminuser.dto.stadium;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AdminWaitStadiumListOutDto {

    private Long id;
    private String name;
    private String tel;
    private String fileUrl;
    private LocalDateTime createdAt;

    public AdminWaitStadiumListOutDto(Long id, String name, String tel, String fileUrl, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.fileUrl = fileUrl;
        this.createdAt = createdAt;
    }

}
