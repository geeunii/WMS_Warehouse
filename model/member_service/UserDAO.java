package model.member_service;

import util.DBUtil;
import vo.Members.User;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDAO implements User_Interface {
    Connection conn = DBUtil.getConnection();

    @Override
    public int updateUserInfo(User user, String olduserID) {
        String sql = "{call updateUserInfo(?,?,?,?,?)}";

        try(CallableStatement cal = conn.prepareCall(sql)){
            cal.setString(1, user.getName());
            cal.setString(2, user.getUserID());
            cal.setString(3, user.getPhone());
            cal.setString(4, user.getAccount());
            cal.setString(5, olduserID);

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

    @Override
    public int deleteUserInfo(String userID) {
        String sql = "{call deleteUserInfo(?)}";

        try(CallableStatement cal = conn.prepareCall(sql)){
            cal.setString(1, userID);

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
    public Optional<User> searchUserInfo(int uID) {
        String sql = "{call searchUserInfo(?)}";

        try(CallableStatement cal = conn.prepareCall(sql)){
            cal.setInt(1, uID);

            ResultSet rs = cal.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setUid(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setUserID(rs.getString(3));
                user.setPhone(rs.getString(4));
                user.setAccount(rs.getString(5));
                user.setCreateAt(rs.getTimestamp(6));
                return Optional.of(user);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;
    }
}
