package org.duckdns.apichahyunhocommunity.domain.user.repository;

import org.duckdns.apichahyunhocommunity.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
