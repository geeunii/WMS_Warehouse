package model.member_service;

import util.DBUtil;
import vo.Members.Admin;
import vo.Members.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class RegisterImpl implements Register_Interface {
    Connection conn = DBUtil.getConnection();

    @Override
    public int registerUser(User user) {
        String sql = "call registerUser(?,?,?,?,?,?)";

        try (CallableStatement cal = conn.prepareCall(sql)){

            cal.setString(1, user.getName());
            cal.setString(2, user.getUserID());
            cal.setString(3, user.getUserPW());
            cal.setString(4, user.getPhone());
            cal.setString(5, user.getAccount());
            cal.setString(6, String.valueOf(user.getAdminCheck()));

            int rows = cal.executeUpdate();  // 영향받은 행 수 반환
            if (rows > 0) {
                return 1;  // 성공
            } else {
                return 0;  // 실패
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int registerAdmin(Admin admin) {
        String sql = "call registerAdmin(?,?,?,?,?)";

        try(CallableStatement cal = conn.prepareCall(sql)){
            cal.setString(1, String.valueOf(admin.getRole()));
            cal.setString(2, admin.getAdminID());
            cal.setString(3, admin.getAdminPW());
            cal.setString(4, admin.getAdminName());
            cal.setString(5, admin.getAdminPhone());

            int rows = cal.executeUpdate();  // 영향받은 행 수 반환
            if (rows > 0) {
                return 1;  // 성공
            } else {
                return 0;  // 실패
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }
}
