package shop.mtcoding.sporting_server.modules.company_info.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "company_info_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class CompanyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @NonNull
    @Comment("유저 테이블")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @NonNull
    @Comment("기업 사업자번호")
    @Column(name = "business_number", unique = true)
    private String businessNumber;

    @NonNull
    @Comment("기업 주소")
    @Column(name = "business_address", unique = true)
    private String businessAdress;

    @NonNull
    @Comment("기업 전화번호")
    @Column(name = "tel")
    private String tel;

    @NonNull
    @Comment("기업 대표자명")
    @Column(name = "ceo")
    private String ceo;

    @NonNull
    @Comment("기업 사진 정보 (1장)")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_info")
    private FileInfo fileInfo;

    @Comment("기업 수정일자")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
