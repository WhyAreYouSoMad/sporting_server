package shop.mtcoding.sporting_server.modules.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.core.enums.role.RoleType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @NonNull
    @Comment("유저 - 닉네임 / 회사 - 회사명")
    @Column(name = "nickname", unique = true)
    private String nickname;

    @NonNull
    @Comment("유저-로그인 이메일")
    @Column(name = "email")
    private String email;

    @NonNull
    @Comment("유저-로그인 비밀번호")
    @Column(name = "password")
    private String password;

    @NonNull
    @Comment("인증")
    private String role;

    @NonNull
    @Comment("유저 생성일자")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Comment("유저 수정일자")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NonNull
    @Comment("유저 활성상태 (인증대기/인증완료/휴먼상태/탈퇴계정)")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    public User get(int i) {
        return null;
    }

}
