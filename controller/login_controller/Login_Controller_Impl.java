package controller.login_controller;

import model.member_service.LoginImpl;
import vo.Members.Admin;
import vo.Members.User;

import java.sql.SQLException;

public class Login_Controller_Impl implements Login_Controller{

    LoginImpl login = new LoginImpl();

    @Override
    public User userlogin(String userID, String userPW){
        return login.userLogin(userID, userPW);
    }

    @Override
    public int logout() {
        return login.logout();
    }

    @Override
    public Admin adminlogin(String adminID, String adminPW) {
        return login.adminLogin(adminID, adminPW);
    }

    @Override
    public User findUserID(String userName, String phone) {
        return login.findUserID(userName, phone);
    }

    @Override
    public Admin findAdminID(String adminName, String phone) {
        return login.findAdminID(adminName, phone);
    }

    @Override
    public int changeUserPassword(String userID, String phone, String password) {
        return login.changeUserPassword(userID, phone, password);
    }

    @Override
    public int changeAdminPassword(String adminID, String password) {
        return login.changeAdminPassword(adminID, password);
    }
}
