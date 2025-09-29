### 출고 프로시저 작성 ###

-- 사용자 출고 요청
DELIMITER $$
create PROCEDURE sp_createShipping(
    IN p_ItemID int,
    IN p_uid int,
    IN p_shipping_p_quantity int
)

begin
    INSERT INTO shipment(itemID, uid, Shipping_p_quantity, shippingProcess , shippingDate)
        values (p_ItemID, p_uid, p_shipping_p_quantity, '승인 대기' , now());
end $$
delimiter ;











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
    IN p_waybill INT
)
BEGIN
    UPDATE shipment
    SET shippingProcess = p_shippingProcess,
        waybill = CASE
                      WHEN p_shippingProcess = '승인' THEN p_waybill
                      ELSE NULL
            END,
        shippingDate = NOW()
    WHERE shipmentID = p_shipmentID;
END $$
DELIMITER ;

SELECT * FROM shipment WHERE shipmentID;


drop procedure sp_selectCurrentShipmentUser;
-- 유저용 출고 현황 조회
DELIMITER $$
CREATE PROCEDURE sp_selectCurrentShipmentUser(
    IN p_shipmentID int
)

BEGIN
    SELECT shipmentID, uid, itemID, Shipping_p_quantity,
           shippingProcess, waybill, shippingDate
    FROM shipment
    where shipmentID = p_shipmentID  -- 출고번호 기준 정렬
    ORDER BY shipmentID;             -- 출고번호 기준 정렬
END$$
DELIMITER ;





-- 관리자용 출고 현황 조회 (상품명 포함)
DROP PROCEDURE IF EXISTS sp_selectCurrentShipment;
DELIMITER $$
CREATE PROCEDURE sp_selectCurrentShipment()
BEGIN
    SELECT s.shipmentID,
           s.uid AS userID,
           s.itemID,
           i.itemName AS shipItemName,
           s.Shipping_p_quantity,
           s.shippingProcess,
           s.waybill,
           s.shippingDate
    FROM shipment s
             JOIN Item i ON s.itemID = i.itemID
    ORDER BY s.shippingDate DESC;
END$$
DELIMITER ;




drop procedure sp_selectShipmentByID;
-- 출고 지시서 조회 (출고 아이디 기준)
DELIMITER $$
CREATE PROCEDURE sp_selectShipmentByID(
    IN p_shipmentID INT
)
BEGIN
    SELECT shipmentID, uid, itemID, Shipping_p_quantity,
           shippingProcess, waybill, shippingDate
    FROM shipment
    WHERE shipmentID = p_shipmentID and shippingProcess = '승인';
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
    WHERE s.shippingProcess = '승인대기';
END$$
DELIMITER ;

