package model.member_service;

import util.DBUtil;
import vo.Members.Admin;
import vo.Members.AdminCheck;
import vo.Members.Role;
import vo.Members.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginImpl implements Login_Interface{

    static Connection conn = DBUtil.getConnection();

    @Override
    public User userLogin(String userID, String userPW){
        User user = new User();
        String sql = "{ call userlogin(?, ?) }";

        try (CallableStatement cal = conn.prepareCall(sql)){

            cal.setString(1, userID);
            cal.setString(2, userPW);

            try (ResultSet rs = cal.executeQuery()) { // ← SELECT면 이걸로!
                if (rs.next()) {
                    String adminCheck = rs.getString(8); // WAITING/APPROVAL/CANCEL
                    if (!"Approval".equals(adminCheck)) return null;

                    user.setUid(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setUserID(rs.getString(3));
                    user.setUserPW(rs.getString(4));
                    user.setPhone(rs.getString(5));
                    user.setAccount(rs.getString(6));
                    user.setCreateAt(rs.getDate(7));
                    user.setAdminCheck(AdminCheck.valueOf(rs.getString(8)));
                    return user;
                } else {
                    return null;
                }
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public int logout() {
        return 0;
    }

    @Override
    public Admin adminLogin(String adminID, String adminPW) {
        Admin admin = new Admin();
        String sql = "{ call adminlogin(?, ?)} ";

        try (CallableStatement cal = conn.prepareCall(sql)){

            cal.setString(1, adminID);
            cal.setString(2, adminPW);
            try (ResultSet rs = cal.executeQuery()) { // ← SELECT면 이걸로!
                if (rs.next()) {

                    admin.setMid(rs.getInt(1));
                    admin.setRole(Role.valueOf(rs.getString(2)));
                    admin.setAdminID(rs.getString(3));
                    admin.setAdminPW(rs.getString(4));
                    admin.setAdminName(rs.getString(5));
                    return admin;

                } else {
                    return null;
                }
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserID(String userName, String phone) {
        User user = new User();
        String sql = "{ call findUserID(?, ?) }";

        try (CallableStatement cal = conn.prepareCall(sql)){

            cal.setString(1, userName);
            cal.setString(2, phone);

            try (ResultSet rs = cal.executeQuery()) { // ← SELECT면 이걸로!
                if (rs.next()) {
                    user.setUid(rs.getInt(1));
                    user.setName(rs.getString(2));
                    user.setUserID(rs.getString(3));
                    user.setUserPW(rs.getString(4));
                    user.setPhone(rs.getString(5));
                    user.setAccount(rs.getString(6));
                    user.setCreateAt(rs.getDate(7));
                    user.setAdminCheck(AdminCheck.valueOf(rs.getString(8)));
                    return user;
                } else {
                    return null;
                }
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Admin findAdminID(String adminName, String phone) {
        Admin admin = new Admin();
        String sql = "{ call findAdminID(?, ?) }";

        try (CallableStatement cal = conn.prepareCall(sql)){

            cal.setString(1, adminName);
            cal.setString(2, phone);

            try (ResultSet rs = cal.executeQuery()) { // ← SELECT면 이걸로!
                if (rs.next()) {
                    admin.setMid(rs.getInt(1));
                    admin.setRole(Role.valueOf(rs.getString(2)));
                    admin.setAdminID(rs.getString(3));
                    admin.setAdminPW(rs.getString(4));
                    admin.setAdminName(rs.getString(5));
                    return admin;

                } else {
                    return null;
                }
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int changeUserPassword(String userId, String phone, String newPlainPw) {
        // 권장: BCrypt 해시 후 넘기기
        // String hashed = BCrypt.hashpw(newPlainPw, BCrypt.gensalt());
        String hashed = newPlainPw; // 임시 (실서비스는 해시 필수)

        final String sql = "{ call changeUserPassword(?, ?, ?, ?) }";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, userId);
            cs.setString(2, phone);
            cs.setString(3, hashed);
            cs.registerOutParameter(4, java.sql.Types.INTEGER);

            cs.execute();
            return cs.getInt(4);  // -1 없음 / 0 동일 / 1 성공
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int changeAdminPassword(String adminId, String newPlainPw) {
        // 1) 새 비번 해시 (BCrypt 예시) — Maven에 jBCrypt 추가 필요
        // String hashed = BCrypt.hashpw(newPlainPw, BCrypt.gensalt());
        String hashed = newPlainPw; // 해시 안 할 거면 일단 그대로 (권장: 해시)

        final String sql = "{ call changeAdminPassword(?, ?, ?) }";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, adminId);
            cs.setString(2, hashed);
            cs.registerOutParameter(3, java.sql.Types.INTEGER);

            cs.execute();
            return cs.getInt(3);  // -1:없음, 0:변경없음, 1:성공
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
