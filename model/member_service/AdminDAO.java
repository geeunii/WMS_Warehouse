package model.member_service;

import util.DBUtil;
import vo.Members.Admin;
import vo.Members.AdminCheck;
import vo.Members.Role;
import vo.Members.User;

import javax.swing.text.html.Option;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDAO implements Admin_Interface{

    Connection conn = DBUtil.getConnection();

    @Override
    public Optional<Admin> searchMyInfoByMid(long mid) {
        // Admin - 내 정보 조회
        try (CallableStatement cal = conn.prepareCall("{ call sp_admin_get_by_mid(?) }")) {
            cal.setInt(1, (int) mid);
            try (ResultSet rs = cal.executeQuery()) {
                if (rs.next()) {
                    Admin a = new Admin();
                    a.setAdminID(rs.getString("adminID"));
                    a.setAdminPW(rs.getString("adminPW"));
                    a.setAdminName(rs.getString("adminName"));
                    a.setRole(Role.valueOf(rs.getString("role")));
                    a.setAdminPhone(rs.getString("adminPhone"));
                    // role, createAt 등 나머지 매핑
                    return Optional.of(a);
                }
                return null;
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int updateMyInfo(Admin admin, String adminID) {
        // Admin - 내 정보 수정
        try (CallableStatement cal = conn.prepareCall("{ call sp_admin_update_by_id(?,?,?,?,?,?) }")) {
            cal.setString(1, admin.getAdminID());
            cal.setString(2, admin.getAdminPW());   // null 가능
            cal.setString(3, admin.getAdminName()); // null 가능
            cal.setString(4, admin.getRole().toString());
            cal.setString(5, admin.getAdminPhone());
            cal.setString(6, adminID);

            int row = cal.executeUpdate();

            if (row > 0) {
                return 1;
            }else{
                return 0;
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteMyInfo(String adminID) {
        String sql = "{ call sp_admin_delete_by_id(?) }";
        try(CallableStatement cal = conn.prepareCall(sql)){
            cal.setString(1, adminID);

            int rows = cal.executeUpdate();

            if (rows > 0) {
                return 1;
            }else{
                return 0;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public User searchUserInfo(String userID) {
        String sql = "{ call sp_user_get_by_id(?) }";

        try(CallableStatement cal = conn.prepareCall(sql)){
            cal.setString(1, userID);

            ResultSet rs = cal.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setUserID(rs.getString(3));
                user.setUserPW(rs.getString(4));
                user.setPhone(rs.getString(5));
                user.setAccount(rs.getString(6));
                user.setCreateAt(rs.getDate(7));
                user.setAdminCheck(AdminCheck.valueOf(rs.getString(8)));

                return user;
            }else{
                return null;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> searchUserList() {
        List<User> userList = new ArrayList<User>();
        String sql = "{ call sp_user_list_all() }";

        try(CallableStatement cal = conn.prepareCall(sql)){

            ResultSet rs = cal.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setUserID(rs.getString(3));
                user.setUserPW(rs.getString(4));
                user.setPhone(rs.getString(5));
                user.setAccount(rs.getString(6));
                user.setCreateAt(rs.getDate(7));
                user.setAdminCheck(AdminCheck.valueOf(rs.getString(8)));
                userList.add(user);
            }
            return userList;

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> searchUserListbyAdminCheck() {
        List<User> userList = new ArrayList<User>();
        String sql = "{ call sp_user_list_by_status(?) }";
        try(CallableStatement cal = conn.prepareCall(sql)) {

            cal.setString(1, "Waiting");

            ResultSet rs = cal.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setUserID(rs.getString(3));
                user.setPhone(rs.getString(4));
                user.setAccount(rs.getString(5));
                user.setCreateAt(rs.getDate(6));
                user.setAdminCheck(AdminCheck.valueOf(rs.getString(7)));

                userList.add(user);
            }
            return userList;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
// private final AdminCheck defaultAdminCheck = AdminCheck.Waiting;
    @Override
    public int changeUserAdminCheck(String userID) {
        String sql = "{ call sp_user_change_admin_check(?, ?) }";

        try(CallableStatement cal = conn.prepareCall(sql)){
            cal.setString(1, userID);
            cal.setString(2, AdminCheck.Approval.toString());

            int rows = cal.executeUpdate();

            if (rows > 0) {
                return 1;
            }else
                return 0;


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
