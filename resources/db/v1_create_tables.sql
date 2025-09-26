
-- Admin Table
create table if not exists Admins
(
    mID        int auto_increment
        primary key,
    role       varchar(50) not null,
    adminID    varchar(50) not null,
    adminPW    varchar(50) not null,
    adminName  varchar(50) not null,
    adminPhone varchar(50) not null
);

-- User Table 
create table if not exists Users
(
    uID        int auto_increment
        primary key,
    name       varchar(50) not null,
    userID     varchar(50) not null,
    userPW     varchar(50) not null,
    phone      varchar(50) not null,
    account    varchar(50) not null,
    createAt   date        not null,
    adminCheck varchar(50) not null
);
truncate Users;

-- 문의 Table
CREATE TABLE IF NOT EXISTS Request
(
    requestID   INT AUTO_INCREMENT PRIMARY KEY,
    uid         INT                       NOT NULL,
    title       VARCHAR(255)              NOT NULL,
    content     TEXT                      NOT NULL,
    r_response  TEXT,
    r_createdAt DATETIME    DEFAULT CURRENT_TIMESTAMP,
    r_updatedAt DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    r_status    VARCHAR(50) DEFAULT '대기',
    r_type      ENUM ('board','onetoone') NOT NULL
);

-- 공지 Table
CREATE TABLE IF NOT EXISTS Notice
(
    noticeID   INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    n_title    VARCHAR(100) NOT NULL,
    n_content  VARCHAR(255) NOT NULL,
    n_createAt DATETIME     NOT NULL,
    n_updateAt DATETIME     NOT NULL,
    n_priority INT          NOT NULL,
    mid        INT          NOT NULL
);


