####################### 창고 프로시저 #######################
######################### 창고 관리 ########################
-- 창고 등록
DROP PROCEDURE IF EXISTS sp_InsertWarehouse;
DELIMITER $$
CREATE PROCEDURE sp_InsertWarehouse(
    IN p_warehouseName VARCHAR(50),
    IN p_warehouseAddress VARCHAR(255),
    IN p_warehouseStatus VARCHAR(20),
    IN p_warehouseCityName VARCHAR(50),
    -- IN p_maxCapacity INT,
    IN p_warehouseArea INT,
    IN p_floorHeight INT,
    IN p_mid INT,
    OUT out_newWarehouseID INT
)
BEGIN
    INSERT INTO Warehouse (warehouseName, warehouseAddress, warehouseStatus, warehouseCityName,
                           warehouseArea, floorHeight, mid)
    VALUES (p_warehouseName, p_warehouseAddress, p_warehouseStatus, p_warehouseCityName,
            p_warehouseArea, p_floorHeight, p_mid);

    SET out_newWarehouseID = LAST_INSERT_ID();
END $$
DELIMITER ;

-- 창고 등록 조회시 창고 ID 가져오기
DROP PROCEDURE IF EXISTS sp_GetWarehouseId;
DELIMITER $$
CREATE PROCEDURE sp_GetWarehouseId(IN p_warehouseID INT)
BEGIN
    SELECT *
    FROM Warehouse
    WHERE warehouseID = p_warehouseID;
END $$
DELIMITER ;

-- 창고 전체 조회
delimiter $$
create procedure sp_searchAllWarehouse()
begin
    select wh.warehouseID,
           wh.warehouseName,
           wh.warehouseAddress,
           wh.warehouseStatus,
           wh.warehouseCityName,
           wh.maxCapacity,
           wh.warehouseArea,
           wh.regDate,
           wh.floorHeight,
           wh.mid,
           ad.adminName
    from Warehouse wh
             left join Admins ad
                       on wh.mid = ad.mid;
end $$
delimiter ;

-- 소재지 조회
delimiter $$
create procedure sp_selectByLocation(IN p_warehouseAddress VARCHAR(255))
begin
    select wh.warehouseID,
           wh.warehouseName,
           wh.warehouseAddress,
           wh.warehouseStatus,
           wh.warehouseCityName,
           wh.maxCapacity,
           wh.warehouseArea,
           wh.regDate,
           wh.floorHeight,
           wh.mid,
           ad.adminName
    from Warehouse wh
             join Admins ad on wh.mid = ad.mid
    where wh.warehouseAddress like CONCAT('%', p_warehouseAddress, '%');
end $$
delimiter ;

-- 이름 조회
DROP PROCEDURE IF EXISTS sp_searchByName;
delimiter $$
create procedure sp_searchByName(in p_warehouseName varchar(50))
begin
    select wh.warehouseID,
           wh.warehouseName,
           wh.warehouseAddress,
           wh.warehouseStatus,
           wh.warehouseCityName,
           wh.maxCapacity,
           wh.warehouseArea,
           wh.regDate,
           wh.floorHeight,
           wh.mid,
           ad.adminName
    from Warehouse wh
             join Admins ad
                  on wh.mid = ad.mid
    where wh.warehouseName like CONCAT('%', p_warehouseName, '%');
end $$
delimiter ;

-- 면적(사이즈) 조회
DROP PROCEDURE IF EXISTS sp_selectBySize;
DELIMITER $$
CREATE PROCEDURE sp_selectBySize(IN p_warehouseArea INT)
BEGIN
    SELECT wh.warehouseID,
           wh.warehouseName,
           wh.warehouseAddress,
           wh.warehouseStatus,
           wh.warehouseCityName,
           wh.maxCapacity,
           wh.warehouseArea,
           wh.regDate,
           wh.floorHeight,
           wh.mid,
           ad.adminName
    FROM Warehouse wh
             JOIN Admins ad ON wh.mid = ad.mid
    WHERE wh.warehouseArea = p_warehouseArea;
END $$
DELIMITER ;

-- 상태 조회
DROP PROCEDURE IF EXISTS sp_getWarehouseStatus;
delimiter $$
create procedure sp_getWarehouseStatus(in whStatus VARCHAR(20))
begin
    select wh.warehouseID,
           wh.warehouseName,
           wh.warehouseAddress,
           wh.warehouseStatus,
           wh.warehouseCityName,
           wh.maxCapacity,
           wh.warehouseArea,
           wh.regDate,
           wh.floorHeight,
           wh.mid,
           ad.adminName
    from Warehouse wh
             join Admins ad on ad.mid = wh.mid
    where wh.warehouseStatus like CONCAT('%', whStatus, '%');
end $$
delimiter ;

-- 창고 수정
DROP PROCEDURE IF EXISTS sp_updateWarehouse;
delimiter $$
create procedure sp_updateWarehouse(IN p_warehouseID INT,
                                    IN p_warehouseName VARCHAR(50),
                                    IN p_warehouseAddress VARCHAR(255),
                                    IN p_warehouseStatus VARCHAR(20),
                                    IN p_warehouseCityName VARCHAR(50),
    -- IN p_maxCapacity INT,
                                    IN p_warehouseArea INT,
                                    IN p_floorHeight INT,
                                    IN p_mid INT)
begin
    update Warehouse
    set warehouseName     = p_warehouseName,
        warehouseAddress  = p_warehouseAddress,
        warehouseStatus   = p_warehouseStatus,
        warehouseCityName = p_warehouseCityName,
        -- maxCapacity       = p_maxCapacity,
        warehouseArea     = p_warehouseArea,
        floorHeight       = p_floorHeight,
        mid               = p_mid
    where warehouseID = p_warehouseID;
end $$
delimiter ;

-- 창고 삭제
delimiter $$
create procedure sp_deleteWarehouse(in p_warehouseID int)
begin
    delete
    from Warehouse
    where warehouseID = p_warehouseID;
end $$
delimiter ;
######################### 구역 관리 ########################
-- 구역 등록
DROP PROCEDURE IF EXISTS sp_InsertSection;
DELIMITER $$
CREATE PROCEDURE sp_InsertSection(
    IN p_sectionName VARCHAR(50),
    IN p_maxVol INT,
    IN p_currentVol INT,
    IN p_warehouseID INT,
    OUT out_newSectionID INT
)
BEGIN
    INSERT INTO WarehouseSection (sectionName, maxVol, currentVol, warehouseID)
    VALUES (p_sectionName, p_maxVol, p_currentVol, p_warehouseID);

    SET out_newSectionID = LAST_INSERT_ID();
END $$
DELIMITER ;

-- 창고 ID 로 구역 조회
DROP PROCEDURE IF EXISTS sp_GetSectionsByWarehouseId;
DELIMITER $$
CREATE PROCEDURE sp_GetSectionsByWarehouseId(
    IN p_warehouseID INT
)
BEGIN
    SELECT * FROM WarehouseSection WHERE warehouseID = p_warehouseID;
END $$
DELIMITER ;

-- 구역 ID 로 조회하는 프로시저
DROP PROCEDURE IF EXISTS sp_GetSectionById;
DELIMITER $$
CREATE PROCEDURE sp_GetSectionById(IN p_sectionID INT)
BEGIN
    SELECT *
    FROM WarehouseSection
    WHERE sectionID = p_sectionID;
END $$
DELIMITER ;

-- 구역 정보 수정
DROP PROCEDURE IF EXISTS sp_UpdateSection;
DELIMITER $$
CREATE PROCEDURE sp_UpdateSection(
    IN p_sectionID INT,
    IN p_sectionName VARCHAR(50),
    IN p_maxVol INT,
    IN p_currentVol INT,
    IN p_warehouseID INT
)
BEGIN
    UPDATE WarehouseSection
    SET sectionName = p_sectionName,
        maxVol      = p_maxVol,
        currentVol  = p_currentVol,
        warehouseID = p_warehouseID
    WHERE sectionID = p_sectionID;
END $$
DELIMITER ;

-- 구역 삭제
DROP PROCEDURE IF EXISTS sp_DeleteSection;
DELIMITER $$
CREATE PROCEDURE sp_DeleteSection(
    IN p_sectionID INT
)
BEGIN
    DELETE FROM WarehouseSection WHERE sectionID = p_sectionID;
END $$
DELIMITER ;