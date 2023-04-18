package shop.mtcoding.sporting_server.topic.stadium.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StadiumMyListOutDTO {
    private Long id;
    private String sport;
    private String name;
}
