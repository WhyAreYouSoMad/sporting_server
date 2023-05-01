package shop.mtcoding.sporting_server.modules.player_favorite_sport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.player_info.entity.PlayerInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.topic.player.dto.PlayerRequest.PlayerUpdateInDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_favorite_sport_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class PlayerFavoriteSport {
    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Comment("유저 정보 테이블")
    @ManyToOne
    @JoinColumn(name = "player_info_id")
    private PlayerInfo playerInfo;

    @Comment("카테고리 테이블")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private SportCategory category;

}
