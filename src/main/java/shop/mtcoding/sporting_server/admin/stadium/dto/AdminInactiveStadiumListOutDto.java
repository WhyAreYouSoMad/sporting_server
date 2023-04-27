package shop.mtcoding.sporting_server.admin.stadium.dto;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class AdminInactiveStadiumListOutDto {
    private Long id;
    private String name;
    private String status;
    private String fileUrl;
    private LocalDateTime createdAt;

    public AdminInactiveStadiumListOutDto(Long id, String name, StadiumStatus status, String fileUrl,
            LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.fileUrl = fileUrl;
        this.status = status.name();
        this.createdAt = createdAt;
    }
}
