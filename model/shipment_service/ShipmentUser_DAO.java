package model.shipment_service;

import util.DBUtil;
import vo.Shippments.Shipment;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShipmentUser_DAO implements ShipmentUser_DAO_Interface{

    Shipment shipment = new Shipment();
    Connection conn = DBUtil.getConnection();

    // 출고 요청 생성
    @Override
    public int createShipment(Shipment shipment) {
        try (CallableStatement cal = conn.prepareCall("{CALL sp_createShipping(?,?,?)}")) {
            cal.setInt(1, shipment.getItemID());
            cal.setInt(2, shipment.getUserID());
            cal.setInt(3, shipment.getShipping_p_quantity());

            return cal.executeUpdate();
        } catch (SQLException e) {
            System.err.println("출고 요청 실패: " + e.getMessage());
            return 0;
        }
    }





    // 출고번호 기준으로 사용자가 출고현황 조회
    @Override
    public List<Shipment> selectCurrentShipmentUser(int shipmentId) {
        List<Shipment> list = new ArrayList<>();
        String sql = "{CALL sp_selectCurrentShipmentUser(?)}";

        try (CallableStatement cal = conn.prepareCall(sql)) {
            cal.setInt(1, shipment.getShipmentID());

            try (ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Shipment s = new Shipment();
                    s.setShipmentID(rs.getInt("shipmentID"));
                    s.setUserID(rs.getInt("uid"));  // DB 컬럼 이름과 맞춤
                    s.setItemID(rs.getInt("itemID"));
                    s.setShipping_p_quantity(rs.getInt("Shipping_p_quantity"));
                    s.setShippingProcess(rs.getString("shippingProcess"));
                    s.setWaybillNumber(rs.getInt("waybill"));
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("유저 출고 현황 조회 실패: " + e.getMessage());
        }

        return list;
    }
}
