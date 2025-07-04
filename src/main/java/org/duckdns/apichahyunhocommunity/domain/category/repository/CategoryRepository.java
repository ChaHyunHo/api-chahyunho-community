package org.duckdns.apichahyunhocommunity.domain.category.repository;

import org.duckdns.apichahyunhocommunity.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
