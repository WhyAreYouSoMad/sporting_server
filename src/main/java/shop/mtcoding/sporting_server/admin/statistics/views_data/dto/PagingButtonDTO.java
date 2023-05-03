package shop.mtcoding.sporting_server.admin.statistics.views_data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingButtonDTO {
    private Boolean first;
    private Boolean last;
    private Integer check;
}
