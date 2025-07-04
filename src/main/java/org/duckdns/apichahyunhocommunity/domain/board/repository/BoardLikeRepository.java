package org.duckdns.apichahyunhocommunity.domain.board.repository;

import org.duckdns.apichahyunhocommunity.domain.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

}
