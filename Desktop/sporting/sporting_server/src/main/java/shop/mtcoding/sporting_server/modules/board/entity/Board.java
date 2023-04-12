package shop.mtcoding.sporting_server.modules.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import shop.mtcoding.sporting_server.core.enums.field.etc.BoardAge;
import shop.mtcoding.sporting_server.core.enums.field.status.BoardStatus;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;
import shop.mtcoding.sporting_server.modules.stadium_court.entity.StadiumCourt;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("유저 테이블")
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @Comment("경기장 코트 테이블")
    @JoinColumn(name = "stadium_court_id")
    private StadiumCourt stadiumCourt;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @Comment("스포츠 카테고리 테이블 (종목)")
    @JoinColumn(name = "category_id")
    private SportCategory category;

    @NonNull
    @Comment("공고 제목")
    @Column(name = "title")
    private String title;

    @NonNull
    @Comment("공고 내용")
    @Column(name = "content")
    private String content;

    @NonNull
    @Comment("경기 시작 시간")
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NonNull
    @Comment("경기 종료 시간")
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @NonNull
    @Comment("해당 공고 최대 참가 인원")
    @Column(name = "attendance_capacity")
    private Integer attendanceCapacity;

    @NonNull
    @Comment("현재 참가 남자 인원")
    @Column(name = "man_player_count")
    private Integer manPlayerCount;

    @NonNull
    @Comment("현재 참가 여자 인원")
    @Column(name = "woman_player_count")
    private Integer womanPlayerCount;

    @NonNull
    @Comment("10대이하, 20대 30대 --- 70대이상")
    @Enumerated(EnumType.STRING)
    @Column(name = "age")
    private BoardAge age;

    @NonNull
    @Comment("공고 신청 참가비")
    @Column(name = "board_price")
    private Integer boardPrice;

    @Comment("공고 등록일")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Comment("공고 수정일")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NonNull
    @Comment("공고 모집 상태 (ex. 모집중/모집마감)")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BoardStatus status;
}
