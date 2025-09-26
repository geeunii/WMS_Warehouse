package controller.login_controller;

import vo.Members.Admin;
import vo.Members.User;

import java.sql.SQLException;

public interface Login_Controller {

    public User userlogin(String userID, String userPW);
    public int logout();
    public Admin adminlogin(String adminID, String adminPW);
    public User findUserID(String userName, String phone);
    public Admin findAdminID(String adminName, String phone);
    public int changeUserPassword(String userID, String phone, String password);
    public int changeAdminPassword(String adminID, String password);
}
