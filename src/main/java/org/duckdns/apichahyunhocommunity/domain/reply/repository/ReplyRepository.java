package org.duckdns.apichahyunhocommunity.domain.reply.repository;

import org.duckdns.apichahyunhocommunity.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
