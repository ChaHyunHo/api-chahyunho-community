package org.duckdns.apichahyunhocommunity.domain.board.repository;

import org.duckdns.apichahyunhocommunity.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
