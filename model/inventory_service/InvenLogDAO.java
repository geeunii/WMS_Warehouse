package model.inventory_service;

import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;

public class InvenLogDAO implements InvenLog_interface {

    public int logInventoryinsert() {
        int result = 0;
        String sql = "{call log_inventory_insert()}" ;

        Connection conn = DBUtil.getConnection();

        try (CallableStatement cal = conn.prepareCall(sql)){
            result = cal.executeUpdate();
            System.out.println("재고 실사 완료하였습니다.");
        }catch (Exception e) {
            System.out.println("재고 실사 실패 : " + e.getMessage());
        }
        return result;
    }
}
