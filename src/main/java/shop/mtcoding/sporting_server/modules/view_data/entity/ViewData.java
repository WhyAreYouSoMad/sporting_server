package shop.mtcoding.sporting_server.modules.view_data.entity;

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
import lombok.NonNull;
import shop.mtcoding.sporting_server.modules.stadium.entity.Stadium;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "view_data_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class ViewData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("유저 테이블")
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @Comment("해당 년/월, role 별 접속수")
    @Column(name = "cnt")
    private Long cnt;

    @Comment("년도")
    @Column(name = "year_val")
    private Integer year;

    @Comment("월")
    @Column(name = "month_val")
    private Integer month;

}
