package shop.mtcoding.sporting_server.modules.user.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.Comment;

import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

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
    @Comment("PLAYER / USER / ADMIND(1단계에선 x)")
    private String role;

    @Comment("유저 생성일자")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Comment("유저 수정일자")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NonNull
    @Comment("유저 활성상태 (일반회원/인증대기/인증완료/휴먼상태/탈퇴계정)")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    // @PrePersist // insert 시에 동작
    // public void onCreate() {
    // this.createdAt = LocalDateTime.now();
    // }

    @PreUpdate // update 시에 동작
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public User(Long id, String nickname, @NonNull String email, @NonNull String password, @NonNull String role,
                LocalDateTime createdAt, LocalDateTime updatedAt, @NonNull UserStatus status) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
