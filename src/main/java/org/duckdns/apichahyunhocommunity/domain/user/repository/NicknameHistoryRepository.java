package org.duckdns.apichahyunhocommunity.domain.user.repository;

import org.duckdns.apichahyunhocommunity.domain.user.entity.NicknameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NicknameHistoryRepository extends JpaRepository<NicknameHistory, Long> {

}
