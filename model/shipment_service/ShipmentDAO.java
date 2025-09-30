package model.shipment_service;

import util.DBUtil;
import vo.Items.Item;
import vo.Shippments.Shipment;

import java.rmi.server.UID;
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
        String sql = "{CALL sp_updateShipment(?, ?, ?, ?)}";
        try (CallableStatement cal = conn.prepareCall(sql)) {
            cal.setInt(1, s.getShipmentID());
            cal.setString(2, s.getShippingProcess());
            cal.setString(3, s.getWaybillNumber());
            cal.setInt(4, s.getWarehouseID());
            result = cal.executeUpdate();
        } catch (SQLException e) {
            System.err.println("출고 업데이트 실패: " + e.getMessage());
        }
        return result;
    }

    public int processShipmentInventory(int shipmentID) {
        int result = 0;
        // 재고 처리를 위해 만들어둔 프로시저를 호출하는 SQL문입니다.
        String sql = "{CALL sp_process_shipment_by_warehouse(?)}";

        // 데이터베이스 연결 및 CallableStatement 준비
        try (CallableStatement cal = conn.prepareCall(sql)) {

            // SQL문의 첫 번째 물음표(?)에 파라미터로 받은 shipmentID 값을 설정합니다.
            cal.setInt(1, shipmentID);

            // 프로시저를 실행하고, 영향을 받은 행의 수를 결과로 받습니다.
            result = cal.executeUpdate();

        } catch (SQLException e) {
            // 프로시저 내부에서 재고 부족 등으로 에러가 발생하면 여기서 잡힙니다.
            // e.getMessage()에 '재고가 부족하여 출고할 수 없습니다.'와 같은 메시지가 담겨있습니다.
            System.err.println("재고 처리 프로시저 실행 실패: " + e.getMessage());
        }

        return result;
    }

    // 출고 창고 위치 지정 (출고 아이디로)
    // 본인 것만 나오게





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
                    s.setShipItemName(rs.getString("shipItemName")); // 아이템 이름 매핑 추가
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






    // 사용자 출고 지시서
    // 한 사용자의 모든 출고목록을 출력
    @Override
    public List<Shipment> selectShipmentByID(int userID) {
        List<Shipment> list = new ArrayList<>();
        try (CallableStatement cal = conn.prepareCall("{CALL sp_selectShipmentByID(?)}")) {
            cal.setInt(1,userID);

            try (ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Shipment s = new Shipment();
                    s.setShipmentID(rs.getInt("shipmentID"));
                    s.setUserID(rs.getInt("uid"));
                    s.setItemID(rs.getInt("itemID"));
                    s.setShipItemName(rs.getString("shipItemName"));   // 추가
                    s.setShipping_p_quantity(rs.getInt("Shipping_p_quantity"));
                    s.setShippingProcess(rs.getString("shippingProcess"));
                    s.setWaybillNumber(rs.getString("waybill"));
                    s.setWarehouseID(rs.getInt("warehouseID"));
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
                    s.setWaybillNumber(rs.getString("waybill"));
                    s.setWarehouseID(rs.getInt("warehouseID"));
                    shipments.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("출고 현황 조회 실패: " + e.getMessage());
        }
        return shipments;
    }




}




