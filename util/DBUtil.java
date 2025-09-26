package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {

    private static ResourceBundle bundle;

    static{
        bundle = ResourceBundle.getBundle("util.dbinfo");

        try{
            Class.forName(bundle.getString("driver"));
            System.out.println("드라이버 로딩 성공");
        }catch (Exception e) {
            System.out.println("드라이버 로딩 실패");
        }
    }

    public static Connection getConnection() {

        try{
            return DriverManager.getConnection(
                    bundle.getString("url"),
                    bundle.getString("username"),
                    bundle.getString("userpassword")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
