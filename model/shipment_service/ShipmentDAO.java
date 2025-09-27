package model.shipment_service;

import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO implements Shipment_DAO_Interface {
    Shipment shipment = new Shipment();
    Connection conn = DBUtil.getConnection();


    // 출고 요청 생성
    @Override
    public int createShipment(Shipment shipment) {
        try (CallableStatement cal = conn.prepareCall("{CALL sp_createShipping(?,?,?)}")) {
            cal.setInt(1, shipment.getItemID());
            cal.setInt(2, shipment.getUserID());
            cal.setInt(3, shipment.getShipItemCount());

            return cal.executeUpdate();
        } catch (SQLException e) {
            System.err.println("출고 요청 실패: " + e.getMessage());
            return 0;
        }
    }



    // 출고 요청 삭제
    @Override
    public int deleteShipment(int shipmentID, int userID) {
        try (CallableStatement cal = conn.prepareCall("{CALL sp_deleteShipment(?,?)}")) {
            cal.setInt(1, shipmentID);
            cal.setInt(2, userID);
            return cal.executeUpdate();
        } catch (SQLException e) {
            System.err.println("출고 삭제 실패: " + e.getMessage());
            return 0;
        }
    }

    // 출고 승인 / 거절
    @Override
    public int updateShipment(Shipment shipment) {
        try (CallableStatement cal = conn.prepareCall("{CALL sp_updateShipment(?,?,?)}")) {
            cal.setInt(1, shipment.getShipmentID());
            cal.setString(2, shipment.getShipStatus());
            cal.setInt(3, shipment.getWaybillNumber());
            return cal.executeUpdate();
        } catch (SQLException e) {
            System.err.println("출고 승인/거절 실패: " + e.getMessage());
            return 0;
        }
    }

    // 관리자 출고 현황 조회 (창고 기준)
    @Override
    public List<Shipment> selectCurrentShipment(int warehouseID) {
        List<Shipment> shipments = new ArrayList<>();
        try (CallableStatement cal = conn.prepareCall("{CALL sp_selectCurrentShipment(?)}")) {
            cal.setInt(1, warehouseID);
            try (ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Shipment s = new Shipment();
                    s.setShipmentID(rs.getInt("shipmentID"));
                    s.setUserID(rs.getInt("userID"));
                    s.setItemID(rs.getInt("itemID"));
                    s.setWarehouseID(rs.getInt("warehouseID"));
                    s.setShipItemName(rs.getString("shipItemName"));
                    s.setShipItemCount(rs.getInt("shipItemCount"));
                    s.setShipStatus(rs.getString("shipStatus"));
                    s.setWaybillNumber(rs.getInt("waybillNumber"));
                    s.setShipLocation(rs.getString("Location"));
                    shipments.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("출고 현황 조회 실패: " + e.getMessage());
        }
        return shipments;
    }




    @Override
    public List<Shipment> searchShipmentByProduct(String keyword) {
        return List.of();
    }




    @Override
    public String selectWaybillUSER(int shipmentID, int userID) {
        return "";
    }




    // 출고 ID 기준 조회 (지시서)
    @Override
    public Shipment selectShipmentByID(int shipmentID) {
        Shipment s = null;
        try (CallableStatement cal = conn.prepareCall("{CALL sp_selectShipmentByID(?)}")) {
            cal.setInt(1, shipmentID);
            try (ResultSet rs = cal.executeQuery()) {
                if (rs.next()) {
                    s = new Shipment();
                    s.setShipmentID(rs.getInt("shipmentID"));
                    s.setUserID(rs.getInt("userID"));
                    s.setItemID(rs.getInt("itemID"));
                    s.setShipItemName(rs.getString("shipItemName"));
                    s.setShipItemCount(rs.getInt("shipItemCount"));
                    s.setShipStatus(rs.getString("shipStatus"));
                    s.setWaybillNumber(rs.getInt("waybillNumber"));
                }
            }
        } catch (SQLException e) {
            System.err.println("출고 지시서 조회 실패: " + e.getMessage());
        }
        return s;
    }

    // 운송장 등록
    @Override
    public int registerWaybill(Shipment shipment) {
        try (CallableStatement cal = conn.prepareCall("{CALL sp_registerWaybill(?,?)}")) {
            cal.setInt(1, shipment.getShipmentID());
            cal.setInt(2, shipment.getWaybillNumber());
            return cal.executeUpdate();
        } catch (SQLException e) {
            System.err.println("운송장 등록 실패: " + e.getMessage());
            return 0;
        }
    }

    // 운송장 수정
    @Override
    public int updateWaybill(Shipment shipment) {
        try (CallableStatement cal = conn.prepareCall("{CALL sp_updateWaybill(?,?)}")) {
            cal.setInt(1, shipment.getShipmentID());
            cal.setInt(2, shipment.getWaybillNumber());
            return cal.executeUpdate();
        } catch (SQLException e) {
            System.err.println("운송장 수정 실패: " + e.getMessage());
            return 0;
        }
    }

    // 관리자 운송장 조회
    @Override
    public String selectWaybillADMIN(int shipmentID) {
        String waybill = null;
        try (CallableStatement cal = conn.prepareCall("{CALL sp_selectWayBill_Admin(?)}")) {
            cal.setInt(1, shipmentID);
            try (ResultSet rs = cal.executeQuery()) {
                if (rs.next()) {
                    waybill = rs.getString("waybillNumber");
                }
            }
        } catch (SQLException e) {
            System.err.println("운송장 조회 실패: " + e.getMessage());
        }
        return waybill;
    }
}


