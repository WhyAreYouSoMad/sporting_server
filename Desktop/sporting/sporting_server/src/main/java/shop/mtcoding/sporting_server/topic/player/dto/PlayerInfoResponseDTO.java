package shop.mtcoding.sporting_server.topic.player.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAddress;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoGender;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PlayerInfoResponseDTO {
    private Long id;
    private String tel;
    private PlayerInfoGender gender;
    private PlayerInfoAge age;
    private PlayerInfoAddress address;

    @QueryProjection
    public PlayerInfoResponseDTO(Long id, String tel, PlayerInfoGender gender, PlayerInfoAge age,
            PlayerInfoAddress address) {
        this.id = id;
        this.tel = tel;
        this.gender = gender;
        this.age = age;
        this.address = address;
    }
}
