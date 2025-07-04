package org.duckdns.apichahyunhocommunity.domain.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import org.duckdns.apichahyunhocommunity.domain.auth.entity.RefreshToken;
import org.duckdns.apichahyunhocommunity.domain.auth.entity.UserAuth;
import org.duckdns.apichahyunhocommunity.domain.board.entity.Board;
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
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Board> boards = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  List<UserAuth> userAuths = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  List<RefreshToken> refreshTokens = new ArrayList<>();

  @Column(nullable = false, length = 50, unique = true)
  String userId;

  @Column(nullable = false, length = 255)
  String password;

  @Column(nullable = false, length = 100)
  String name;

  @Column(nullable = false, length = 100)
  String nickname;

  @Column(nullable = false, length = 255)
  String email;

  @Column(name = "phone_number", length = 20)
  private String phoneNumber;

  @Column(nullable = false, length = 255)
  String about;

  @Column(nullable = false)
  Boolean enabled;

  @Column(nullable = false)
  Integer point;

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
  public User(String userId, String password, String name, String nickname, String email,
      String phoneNumber, String about, Boolean enabled, Integer point) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.about = about;
    this.enabled = enabled;
    this.point = point;
  }
}
