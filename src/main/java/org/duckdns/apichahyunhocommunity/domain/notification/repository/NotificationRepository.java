package org.duckdns.apichahyunhocommunity.domain.notification.repository;

import org.duckdns.apichahyunhocommunity.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
