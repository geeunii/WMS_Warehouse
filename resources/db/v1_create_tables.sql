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




-- 문의 Table
CREATE TABLE IF NOT EXISTS Request
(
    requestID   INT AUTO_INCREMENT PRIMARY KEY,
    uid         INT                       NOT NULL,
    r_title     VARCHAR(255)              NOT NULL,
    r_content   TEXT                      NOT NULL,
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





-- 창고 Table
DROP TABLE Warehouse;
CREATE TABLE IF NOT EXISTS Warehouse
(
    warehouseID       INT                                               NOT NULL AUTO_INCREMENT,
    warehouseName     VARCHAR(50)                                       NOT NULL,
    warehouseAddress  VARCHAR(255)                                      NOT NULL,
    warehouseStatus   VARCHAR(20)                                       NOT NULL,
    warehouseCityName VARCHAR(50)                                       NOT NULL,
    -- maxCapacity       INT          NOT NULL,
    maxCapacity       INT AS (warehouseArea * floorHeight * 0.9) STORED NOT NULL COMMENT '창고 최대 수용량',
    warehouseArea     INT                                               NOT NULL,
    regDate           DATE                                              NOT NULL DEFAULT (CURRENT_DATE),
    floorHeight       INT                                               NOT NULL,
    mid               INT                                               NOT NULL,
    PRIMARY KEY (warehouseID)
);


-- 창고 구역 Table 
DROP TABLE WarehouseSection;
CREATE TABLE IF NOT EXISTS WarehouseSection
(
    sectionID   INT         NOT NULL AUTO_INCREMENT,
    sectionName VARCHAR(50) NOT NULL,
    maxVol      INT         NOT NULL,
    currentVol  INT         NOT NULL,
    warehouseID INT         NOT NULL,
    PRIMARY KEY (sectionID)
);





-- 재고 관리 Table
CREATE TABLE `Inventory`
(
    `eID`         INT auto_increment NOT NULL,
    `quantity`    INT                NOT NULL,
    `warehouseID` INT                NOT NULL,
    sectionID     INT                NOT NULL,
    `itemID`      INT                NOT NULL,
    `stockDate`   DATE,
    `shipDate`    DATE default null,
    primary key (eID)
);

-- 재고 실사 Table
CREATE TABLE `InvenLog`
(
    `logID`            INT  NOT NULL,
    `eID`              INT  NOT NULL,
    `log_quantity`     INT  NOT NULL,
    `warehouseID`      INT  NOT NULL,
    `itemID`           INT  NOT NULL,
    `stockingQuantity` INT  NOT NULL,
    `stockingDate`     DATE NOT NULL,
    `shippingQuantity` INT  NOT NULL,
    `shippingDate`     DATE NOT NULL,
    `logTime`          DATE NOT NULL,
    primary key (logID)
);




