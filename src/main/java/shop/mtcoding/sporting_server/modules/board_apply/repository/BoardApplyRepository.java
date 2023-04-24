package shop.mtcoding.sporting_server.modules.board_apply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.board_apply.entity.BoardApply;

public interface BoardApplyRepository extends JpaRepository<BoardApply, Long>, BoardApplyCustomRepository {

}
