package shop.mtcoding.sporting_server.admin.statistics.views_data.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewDataOutDTO {
    private List<String> topStadiumNames;
    private List<Long> viewCounts;
    private Integer requestMonth;
    private Integer check;
    private Boolean first;
    private Boolean last;
}
