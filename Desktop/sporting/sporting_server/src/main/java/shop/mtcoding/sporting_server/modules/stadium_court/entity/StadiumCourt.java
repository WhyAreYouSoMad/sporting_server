package shop.mtcoding.sporting_server.modules.stadium_court.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumCourtStatus;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stadium_court_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class StadiumCourt {
    @Id
    @Comment("고유번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Comment("경기장 테이블")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @NonNull
    @Comment("thumbnail 사진 정보")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_info_id")
    private FileInfo fileInfo;

    @NonNull
    @Comment("경기장 코트 대관비")
    @Column(name = "court_price")
    private Integer courtPrice;

    @NonNull
    @Comment("경기장 코트 수용인원")
    @Column(name = "capacity")
    private Integer capacity;

    @NonNull
    @Comment("경기장 코트 상세내용")
    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    @Comment("경기장 코트 등록시간")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Comment("경기장 코트 수정시간")
    private LocalDateTime updatedAt;

    @NonNull
    @Comment("경기장 코트 활성화 상태 (등록완료/등록대기/비활성)")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StadiumCourtStatus status;

}
