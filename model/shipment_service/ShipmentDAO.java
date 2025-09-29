package model.shipment_service;

import util.DBUtil;
import vo.Items.Item;
import vo.Shippments.Shipment;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO implements Shipment_DAO_Interface {
    Shipment shipment = new Shipment();
    Connection conn = DBUtil.getConnection();




    // 출고 승인 / 거절
    @Override
    public int updateShipment(Shipment s) {
        int result = 0;
        String sql = "{CALL sp_updateShipment(?, ?, ?)}";
        try (CallableStatement cal = conn.prepareCall(sql)) {
            cal.setInt(1, s.getShipmentID());
            cal.setString(2, s.getShippingProcess());
            cal.setInt(3, s.getWaybillNumber());
            result = cal.executeUpdate();
        } catch (SQLException e) {
            System.err.println("출고 업데이트 실패: " + e.getMessage());
        }
        return result;
    }






    // 출고리스트 조회 (승인 대기 인것만)
    @Override
    public List<Shipment> selectPendingShipment() {
        List<Shipment> list = new ArrayList<>();
        String sql = "{CALL sp_selectPendingShipment()}";

        try (CallableStatement cal = conn.prepareCall(sql)) {
            try (ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Shipment s = new Shipment();
                    s.setShipmentID(rs.getInt("shipmentID"));
                    s.setUserID(rs.getInt("userID"));
                    s.setItemID(rs.getInt("itemID"));
                    s.setShipping_p_quantity(rs.getInt("Shipping_p_quantity"));
                    s.setShippingProcess(rs.getString("shippingProcess"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("승인 대기 출고 조회 실패: " + e.getMessage(), e);
        }

        return list;
    }






    // 승인 된것만 출고 지시서 조회(지시서)
    @Override
    public List<Shipment> selectShipmentByID(int shipmentID) {
        List<Shipment> list = new ArrayList<>();
        try (CallableStatement cal = conn.prepareCall("{CALL sp_selectShipmentByID(?)}")) {
            cal.setInt(1, shipmentID);

            try (ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Shipment s = new Shipment();
                    ;
                    s.setShipmentID(rs.getInt("shipmentID"));
                    s.setUserID(rs.getInt("uid"));
                    s.setItemID(rs.getInt("itemID"));
                    s.setShipping_p_quantity(rs.getInt("Shipping_p_quantity"));
                    s.setShippingProcess(rs.getString("shippingProcess"));
                    s.setWaybillNumber(rs.getInt("waybill"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("출고 지시서 조회 실패: " + e.getMessage());
        }
        return list;
    }









    // 관리자 출고 현황 조회 (모든 출고 현황)
    @Override
    public List<Shipment> selectCurrentShipment() {
        List<Shipment> shipments = new ArrayList<>();
        try (CallableStatement cal = conn.prepareCall("{CALL sp_selectCurrentShipment()}")) {

            try (ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Shipment s = new Shipment();
                    s.setShipmentID(rs.getInt("shipmentID"));
                    s.setUserID(rs.getInt("uid"));
                    s.setItemID(rs.getInt("itemID"));
                    s.setShipping_p_quantity(rs.getInt("Shipping_p_quantity"));
                    s.setShippingProcess(rs.getString("ShippingProcess"));
                    s.setWaybillNumber(rs.getInt("waybill"));
                    shipments.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("출고 현황 조회 실패: " + e.getMessage());
        }
        return shipments;
    }




}




