package org.duckdns.apichahyunhocommunity.domain.auth.repository;

import org.duckdns.apichahyunhocommunity.domain.auth.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

}
