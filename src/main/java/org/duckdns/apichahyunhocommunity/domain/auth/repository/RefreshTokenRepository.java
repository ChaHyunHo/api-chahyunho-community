package org.duckdns.apichahyunhocommunity.domain.auth.repository;

import org.duckdns.apichahyunhocommunity.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

}
