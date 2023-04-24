package shop.mtcoding.sporting_server.modules.fileinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Comment;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "file_info_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 번호")
    @Column(name = "id")
    private Long id;

    @Nullable
    @Comment("파일 출처 테이블 (ex. 기업프로필, 플레이어프로필, 경기장사진, 코트사진)")
    @Enumerated(EnumType.STRING)
    private FileInfoSource type;

    public FileInfo(Long id, FileInfoSource type) {
        this.id = id;
        this.type = type;
    }

}
