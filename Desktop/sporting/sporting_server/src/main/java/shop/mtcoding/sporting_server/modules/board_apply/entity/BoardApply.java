package shop.mtcoding.sporting_server.modules.board_apply.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import lombok.NonNull;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.BoardApplyStatus;
import shop.mtcoding.sporting_server.modules.board.entity.Board;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
// 공고 참가지원 목록 테이블
@Table(name = "board_apply_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class BoardApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @NonNull
    @Comment("유저 테이블")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @Comment("경기장 코트 테이블")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_court_id")
    private StadiumCourt stadiumCourt;

    @NonNull
    @Comment("공고 테이블")
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @NonNull
    @Comment("공고 신청서 내 참가인원")
    @Column(name = "person_num")
    private Integer personNum;

    @NonNull
    @Comment("현재 참가 남자 인원")
    @Column(name = "man_player_count")
    private Integer manPlayerCount;

    @NonNull
    @Comment("현재 참가 여자 인원")
    @Column(name = "woman_player_count")
    private Integer womanPlayerCount;

    @NonNull
    @Comment("신청자 1명의 연령") // CheckPoint
    @Column(name = "age")
    private Integer age;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NonNull
    @Comment("대기, 승낙, 취소")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BoardApplyStatus status;
}
