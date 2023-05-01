package shop.mtcoding.sporting_server.modules.player_info.entity;

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
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAddress;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoAge;
import shop.mtcoding.sporting_server.core.enums.field.etc.PlayerInfoGender;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.user.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "player_info_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class PlayerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("고유번호")
    private Long id;

    @NonNull
    @Comment("플레이어 테이블")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Comment("플레이어 성별 (남자/여자/비공개)")
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private PlayerInfoGender gender;

    @Comment("플레이어 나이 (ex. 10대이하, 20대 30대 , … , 70대이상)")
    @Enumerated(EnumType.STRING)
    @Column(name = "age")
    private PlayerInfoAge age;

    @Comment("플레이어 거주도시")
    @Column(name = "address")
    private String address;

    @Comment("플레이어 핸드폰번호")
    @Column(name = "tel")
    private String tel;

    @Comment("플레이어 사진 정보 (1장)")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_info_id", unique = true)
    private FileInfo fileInfo;

    @Comment("회사 수정일자")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
