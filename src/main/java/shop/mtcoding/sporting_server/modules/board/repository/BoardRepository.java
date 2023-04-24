package shop.mtcoding.sporting_server.modules.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.sporting_server.modules.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

}
