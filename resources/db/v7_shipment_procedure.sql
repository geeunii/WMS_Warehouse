### 출고 프로시저 작성 ###
drop procedure sp_createShipping;
-- 사용자 출고 요청
DELIMITER $$
create PROCEDURE sp_createShipping(
    IN p_ItemID int,
    IN p_uid int,
    IN p_shipping_p_quantity int
)

begin
    INSERT INTO shipment(itemID, uid, Shipping_p_quantity, shippingProcess , shippingDate, warehouseID)
        values (p_ItemID, p_uid, p_shipping_p_quantity, '승인 대기' , now(), null);
end $$
delimiter ;

ALTER TABLE shipment MODIFY warehouseID INT NULL;




-- 2 출고 현황 조회 (승인대기, 승인, 거절)
DROP PROCEDURE IF EXISTS sp_selectCurrentShipmentUser;
DELIMITER $$

CREATE PROCEDURE sp_selectCurrentShipmentUser(
    IN p_userID INT
)
BEGIN
    SELECT
        s.shipmentID,
        s.uid AS userID,
        s.itemID,
        i.itemName AS shipItemName,
        s.Shipping_p_quantity,
        s.shippingProcess,
        s.waybill,
        s.shippingDate
    FROM shipment s
             JOIN Item i ON s.itemID = i.itemID
    WHERE s.uid = p_userID
    ORDER BY s.shipmentID;
END$$

DELIMITER ;

select * from shipment;
select * from Item;





DROP PROCEDURE IF EXISTS sp_shippingProductSearch;
DELIMITER $$

CREATE PROCEDURE sp_shippingProductSearch(
    IN p_ItemID int
)
BEGIN
    SELECT
        s.shipmentID,
        s.shippingDate,
        s.Shipping_p_quantity,
        s.shippingProcess,
        s.waybill,
        i.itemID,
        i.itemName AS shipItemName
    FROM shipment s
             JOIN Item i ON s.itemID = i.itemID
    WHERE s.itemID = p_ItemID
    ORDER BY s.shippingDate DESC;
END$$

DELIMITER ;


















-- ====================================================================



-- 관리자 출고 요청 삭제

DELIMITER $$
create procedure sp_deleteShipment(
    IN p_shipment_id int,
    IN p_uid int
)

begin

    DELETE from shipment
        where shipmentID = p_shipment_id and uid = p_uid;

end $$
delimiter ;




drop procedure sp_updateShipment;
-- 출고 승인 / 거절
DELIMITER $$
CREATE PROCEDURE sp_updateShipment (
    IN p_shipmentID INT,
    IN p_shippingProcess VARCHAR(20),
    IN p_waybill varchar(50),
    IN p_warehouseID int
)
BEGIN
    UPDATE shipment
    SET shippingProcess = p_shippingProcess,
        waybill = CASE WHEN p_shippingProcess = '승인' THEN p_waybill ELSE NULL END,
        warehouseID = CASE WHEN p_shippingProcess = '승인' THEN p_warehouseID ELSE NULL END,
        shippingDate = NOW()
    WHERE shipmentID = p_shipmentID;
END $$
DELIMITER ;

SELECT * FROM shipment WHERE shipmentID;
ALTER TABLE shipment MODIFY waybill VARCHAR(50);





-- 관리자 출고 현황 조회 (모든 출고 현황)
DROP PROCEDURE IF EXISTS sp_selectCurrentShipment;
DELIMITER $$
CREATE PROCEDURE sp_selectCurrentShipment()
BEGIN
    SELECT s.shipmentID,
           s.uid,
           s.itemID,
           i.itemName AS shipItemName,
           s.Shipping_p_quantity,
           s.shippingProcess,
           s.waybill,
           s.shippingDate,
           s.warehouseID
    FROM shipment s
             JOIN Item i ON s.itemID = i.itemID
    ORDER BY s.shippingDate DESC;
END$$
DELIMITER ;




drop procedure sp_selectShipmentByID;
-- 유저 아이디 기준으로 출고 지시서 조회
DELIMITER $$
CREATE PROCEDURE sp_selectShipmentByID(
    IN p_uID INT
)
BEGIN
    SELECT
        s.shipmentID,
        s.uid,
        s.itemID,
        i.itemName AS shipItemName,
        s.Shipping_p_quantity,
        s.shippingProcess,
        s.waybill,
        s.warehouseID
    FROM shipment s
             JOIN Item i ON s.itemID = i.itemID
    WHERE s.uid = p_uID;
END$$

DELIMITER ;





-- 출고리스트 조회 (승인 대기 인것만)

DROP PROCEDURE IF EXISTS sp_selectPendingShipment;
DELIMITER $$

CREATE PROCEDURE sp_selectPendingShipment(

)
BEGIN
    SELECT s.shipmentID,
           s.uid AS userID,
           s.itemID,
           i.itemName AS shipItemName,
           s.Shipping_p_quantity,
           s.shippingProcess
    FROM shipment s
             JOIN Item i ON s.itemID = i.itemID
    WHERE s.shippingProcess = '승인 대기';
END$$
DELIMITER ;

