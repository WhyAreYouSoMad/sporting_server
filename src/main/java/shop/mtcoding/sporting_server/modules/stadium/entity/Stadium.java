package shop.mtcoding.sporting_server.modules.stadium.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
import shop.mtcoding.sporting_server.core.enums.field.etc.StadiumAddress;
import shop.mtcoding.sporting_server.core.enums.field.status.StadiumStatus;
import shop.mtcoding.sporting_server.modules.company_info.entity.CompanyInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.sport_category.entity.SportCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stadium_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @NonNull
    @Comment("회사 정보 테이블")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_info_id")
    private CompanyInfo companyInfo;

    @NonNull
    @Comment("경기장 이름")
    @Column(name = "name")
    private String name;

    @Comment("경기장 소개")
    @Column(name = "description")
    private String description;

    @NonNull
    @Comment("유저 거주도시 (ex. 서울시, 부산시, 대구시, 인천시, 광주시, 대전시, 울산시, 제주시)")
    @Enumerated(EnumType.STRING)
    @Column(name = "address")
    private StadiumAddress address;

    @Comment("경기장 위도 값")
    @Column(name = "lat")
    private Double lat;

    @Comment("경기장 경도 값")
    @Column(name = "lon")
    private Double lon;

    @Comment("경기장 관리자 전화번호")
    @Column(name = "tel")
    private String tel;

    @NonNull
    @Comment("카테고리 테이블")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private SportCategory category;

    @Comment("경기장 영업시작 시간")
    @Column(name = "start_time")
    private LocalTime startTime;

    @Comment("경기장 영업종료 시간")
    @Column(name = "end_time")
    private LocalTime endTime;

    @Comment("thumbnail 사진 정보")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_info_id", unique = true)
    private FileInfo fileInfo;

    @NonNull
    @Comment("경기장 활성화 상태 (운영중/휴업/등록대기/폐업)")
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StadiumStatus status;

    @Comment("경기장 등록 시간")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Comment("경기장 수정 시간")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
