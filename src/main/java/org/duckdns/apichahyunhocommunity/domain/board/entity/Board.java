package org.duckdns.apichahyunhocommunity.domain.board.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.duckdns.apichahyunhocommunity.common.enums.BoardStatus;
import org.duckdns.apichahyunhocommunity.domain.category.entity.Category;
import org.duckdns.apichahyunhocommunity.domain.reply.entity.Reply;
import org.duckdns.apichahyunhocommunity.domain.user.entity.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(nullable = false, length = 255)
  String title;

  @Column(columnDefinition = "MEDIUMTEXT")
  String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  Category category;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reply> replies = new ArrayList<>();

  @Column(nullable = false)
  Integer goodCount;

  @Column(nullable = false)
  Integer badCount;

  @Column(nullable = false)
  Integer replyCount;

  @Column(nullable = false)
  Integer viewCount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  BoardStatus status;

  @CreationTimestamp // 엔티티 생성시 시간이 자동으로 기록됨
  @Column(nullable = false, updatable = false)
  LocalDateTime createdAt;

  @UpdateTimestamp // 엔티티 수정시 시간이 자동으로 기록됨
  @Column(nullable = false, updatable = false)
  LocalDateTime updatedAt;

  @PreUpdate
  public void onPreUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  @PrePersist
  public void onPrePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = this.createdAt;
  }

  @Builder
  public Board(String title, String content, User user, Category category, Integer goodCount,
      Integer badCount, Integer replyCount, Integer viewCount, BoardStatus status) {
    this.title = title;
    this.content = content;
    this.user = user;
    this.category = category;
    this.goodCount = goodCount;
    this.badCount = badCount;
    this.replyCount = replyCount;
    this.viewCount = viewCount;
    this.status = status;
  }
}
