package shop.mtcoding.sporting_server.modules.connection_data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "connection_data_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class ConnectionData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @Comment("Player/Company")
    @Column(name = "role")
    private String role;

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
