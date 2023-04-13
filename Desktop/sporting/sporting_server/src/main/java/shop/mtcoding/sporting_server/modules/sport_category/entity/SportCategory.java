package shop.mtcoding.sporting_server.modules.sport_category.entity;

import java.time.LocalDateTime;

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
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sport_category_tb")
@EqualsAndHashCode(of = "id", callSuper = false)
public class SportCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    @Column(name = "id")
    private Long id;

    @NonNull
    @Comment("스포츠 종목")
    @Column(name = "sport", unique = true)
    private String sport;

    @Column(name = "created_at")
    @Comment("스포츠 종목 등록시간")
    private LocalDateTime createdAt;
}
