package org.duckdns.apichahyunhocommunity.domain.reply.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.duckdns.apichahyunhocommunity.domain.board.entity.Board;
import org.duckdns.apichahyunhocommunity.domain.category.entity.Category;
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
public class Reply {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", nullable = false)
  Board board;

  @Column(columnDefinition = "TEXT")
  String content;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  @JsonBackReference
  Category parent;

  @Column(nullable = false)
  Integer depth;

  @Column(nullable = false)
  Integer mentionUserId;

  @Column(nullable = false)
  Integer goodCount;

  @Column(nullable = false)
  Integer badCount;

  @Column(nullable = false)
  Boolean useYn;

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
  public Reply(User user, Board board, String content, Category parent, Integer depth,
      Integer mentionUserId, Integer goodCount, Integer badCount, Boolean useYn) {
    this.user = user;
    this.board = board;
    this.content = content;
    this.parent = parent;
    this.depth = depth;
    this.mentionUserId = mentionUserId;
    this.goodCount = goodCount;
    this.badCount = badCount;
    this.useYn = useYn;
  }
}
