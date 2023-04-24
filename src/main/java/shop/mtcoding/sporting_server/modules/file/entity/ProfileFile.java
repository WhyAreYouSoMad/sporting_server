package shop.mtcoding.sporting_server.modules.file.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import lombok.Setter;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "file_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class ProfileFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    // 하나의 Court에서 사진이 여러개 일 수 있으므로, ManyToOne
    @Comment("파일 출처")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_info_id")
    private FileInfo fileInfo;

    // @Comment("파일 이름")
    // @Column(name = "file_name", unique = true)
    // private String fileName;

    @Comment("파일 경로")
    @Column(name = "file_url")
    private String fileUrl;

    // @Enumerated(EnumType.STRING)
    // @Column(name = "status")
    // @Comment("admin 승인관련 (ex. 승인/대기/승인불가)")
    // private FileStatus status;
}
