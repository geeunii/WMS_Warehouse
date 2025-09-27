package controller.shipment_controller;

import java.util.List;

public interface Shipment_Controller_interface {

    int createShipment(Shipment shipment);   // 사용자 출고 요청

    int deleteShipment(int shipmentID, int userID); // 사용자의 출고 요청 취소

    int updateShipment(Shipment shipment);  // 관리자 출고 승인 및 운송장 등록

    List<Shipment> selectCurrentShipment(int warehouseID);  // 관리자 출고 현황 리스트 출력

    String selectWaybillUSER(int shipmentID, int userID);   // 사용자 운송장 확인

    String selectWaybillADMIN(int shipmentID);             // 관리자 운송장 확인

}
