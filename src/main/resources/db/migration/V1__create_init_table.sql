-- 사용자 테이블
CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 고유 ID',
    user_id    VARCHAR(50) UNIQUE NOT NULL COMMENT '로그인용 사용자 ID',
    password   VARCHAR(255)       NOT NULL COMMENT '비밀번호 (암호화 저장)',
    name       VARCHAR(100) COMMENT '실명',
    nickname   VARCHAR(100) COMMENT '닉네임',
    email      VARCHAR(255) COMMENT '이메일 주소',
    about      TEXT COMMENT '자기 소개',
    enabled    BOOLEAN     DEFAULT TRUE COMMENT '계정 활성화 여부',
    point      INT         DEFAULT 0 COMMENT '포인트',
    role       VARCHAR(20) DEFAULT 'USER' COMMENT '역할 (USER, ADMIN 등)',
    created_at DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT ='사용자 정보 테이블';

CREATE TABLE user_auth
(
    user_id BIGINT      NOT NULL COMMENT '사용자 ID',
    auth    VARCHAR(50) NOT NULL COMMENT '권한 이름 (예: ROLE_USER, ROLE_ADMIN)',
    PRIMARY KEY (user_id, auth)
) COMMENT ='사용자 권한 매핑 테이블';

-- 닉네임 변경 이력
CREATE TABLE nickname_history
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '변경 이력 고유 ID',
    user_id      BIGINT NOT NULL COMMENT '사용자 ID',
    old_nickname VARCHAR(100) COMMENT '이전 닉네임',
    new_nickname VARCHAR(100) COMMENT '새 닉네임',
    changed_at   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '변경일시'
) COMMENT ='닉네임 변경 이력';

-- 리프레시 토큰
CREATE TABLE refresh_token
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '토큰 고유 ID',
    user_id     BIGINT       NOT NULL COMMENT '사용자 ID',
    token       VARCHAR(255) NOT NULL COMMENT '리프레시 토큰 값',
    user_agent  TEXT COMMENT '기기 정보 (브라우저 등)',
    expiry_date DATETIME     NOT NULL COMMENT '만료 일시',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT ='리프레시 토큰 저장 테이블';

-- 카테고리
CREATE TABLE category
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID',
    name       VARCHAR(100) NOT NULL COMMENT '카테고리 이름',
    parent_id  BIGINT COMMENT '부모 카테고리 ID',
    depth      INT      DEFAULT 0 COMMENT '카테고리 계층 깊이',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT ='게시판 카테고리';

-- 게시글
CREATE TABLE board
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '게시글 ID',
    title       VARCHAR(255) NOT NULL COMMENT '제목',
    content     TEXT COMMENT '내용',
    user_id     BIGINT       NOT NULL COMMENT '작성자 ID',
    nickname    VARCHAR(100) COMMENT '작성자 닉네임',
    category_id BIGINT COMMENT '카테고리 ID',
    good_count  INT         DEFAULT 0 COMMENT '좋아요 수',
    bad_count   INT         DEFAULT 0 COMMENT '싫어요 수',
    reply_count INT         DEFAULT 0 COMMENT '댓글 수',
    view_count  INT         DEFAULT 0 COMMENT '조회 수',
    status      VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '게시 상태',
    created_at  DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
    updated_at  DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
) COMMENT ='게시글 테이블';

-- 댓글 (2단계 트리 구조)
CREATE TABLE reply
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 ID',
    board_id        BIGINT NOT NULL COMMENT '게시글 ID',
    writer_id       BIGINT NOT NULL COMMENT '작성자 ID',
    nickname        VARCHAR(100) COMMENT '작성자 닉네임',
    content         TEXT COMMENT '댓글 내용',
    parent_id       BIGINT COMMENT '부모 댓글 ID (NULL이면 1뎁스)',
    depth           INT      DEFAULT 0 COMMENT '댓글 깊이 (0=부모, 1=자식)', -- ✅ CHECK 제거
    mention_user_id BIGINT COMMENT '자식댓글일 경우, 언급된 부모댓글 작성자 ID',
    good_count      INT      DEFAULT 0 COMMENT '좋아요 수',
    use_yn          BOOLEAN  DEFAULT TRUE COMMENT '사용 여부',
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
    updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
) COMMENT ='게시글 댓글';

-- 게시글 좋아요/싫어요
CREATE TABLE board_like
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '좋아요 ID',
    board_id   BIGINT      NOT NULL COMMENT '게시글 ID',
    user_id    BIGINT      NOT NULL COMMENT '사용자 ID',
    status     VARCHAR(10) NOT NULL COMMENT 'LIKE 또는 DISLIKE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
) COMMENT ='게시글 좋아요/싫어요';

-- 댓글 좋아요
CREATE TABLE reply_like
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 좋아요 ID',
    reply_id   BIGINT NOT NULL COMMENT '댓글 ID',
    user_id    BIGINT NOT NULL COMMENT '사용자 ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
) COMMENT ='댓글 좋아요';

-- 알림
CREATE TABLE notification
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '알림 ID',
    receiver_id BIGINT NOT NULL COMMENT '수신자 ID',
    sender_id   BIGINT COMMENT '발신자 ID (NULL이면 시스템)',
    message     TEXT   NOT NULL COMMENT '알림 메시지',
    is_read     BOOLEAN  DEFAULT FALSE COMMENT '읽음 여부',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '알림 생성일'
) COMMENT ='사용자 알림 테이블';

-- 신고
CREATE TABLE report
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '신고 ID',
    reporter_id  BIGINT NOT NULL COMMENT '신고자 ID',
    target_type  VARCHAR(20) COMMENT '대상 타입 (BOARD, REPLY)',
    target_id    BIGINT COMMENT '대상 ID',
    reason       TEXT COMMENT '신고 사유',
    status       VARCHAR(20) DEFAULT 'PENDING' COMMENT '처리 상태',
    processed_by BIGINT COMMENT '처리 관리자 ID',
    processed_at DATETIME COMMENT '처리 일시',
    created_at   DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '신고일'
) COMMENT ='게시글/댓글 신고 테이블';

-- 운영자 제재 로그
CREATE TABLE moderation_log
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제재 로그 ID',
    target_type  VARCHAR(20) NOT NULL COMMENT '대상 타입 (BOARD, REPLY)',
    target_id    BIGINT      NOT NULL COMMENT '대상 ID',
    moderator_id BIGINT      NOT NULL COMMENT '관리자 ID',
    action       VARCHAR(20) NOT NULL COMMENT '조치 유형 (HIDE, DELETE 등)',
    reason       TEXT COMMENT '조치 사유',
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '조치 일시'
) COMMENT ='운영자 제재 내역';

-- 공지사항
CREATE TABLE notice
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '공지 ID',
    title      VARCHAR(255) NOT NULL COMMENT '공지 제목',
    content    TEXT         NOT NULL COMMENT '공지 내용',
    writer_id  BIGINT       NOT NULL COMMENT '작성자 (관리자)',
    is_fixed   BOOLEAN  DEFAULT FALSE COMMENT '상단 고정 여부',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일'
) COMMENT ='공지사항 게시판';

-- 첨부 파일
CREATE TABLE file
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '파일 ID',
    board_id    BIGINT COMMENT '게시글 ID',
    reply_id    BIGINT COMMENT '댓글 ID',
    uploader_id BIGINT COMMENT '업로더 ID',
    file_path   VARCHAR(255) COMMENT '파일 경로',
    file_type   VARCHAR(50) COMMENT '파일 타입',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '업로드 일시'
) COMMENT ='게시글/댓글 첨부파일 테이블';



