package shop.mtcoding.sporting_server.admin.statistics.connection_data.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDataOutDTO {
    private List<Integer> playerConnectionData;
    private List<Integer> compnayConnectionData;
    private List<String> recentHalfYear;
}
