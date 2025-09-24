package controller.admin_controller;

import model.member_service.AdminDAO;
import vo.Members.Admin;
import vo.Members.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class Admin_Controller_Impl implements Admin_Controller{

    AdminDAO adminDAO = new AdminDAO();

    @Override
    public Optional<Admin> searchMyInfoByMid(long mid) {
        return adminDAO.searchMyInfoByMid(mid);
    }

    @Override
    public int updateMyInfo(Admin admin, String userID) {
        return adminDAO.updateMyInfo(admin, userID);
    }

    @Override
    public int deleteMyInfo(String adminID) {
        return adminDAO.deleteMyInfo(adminID);
    }

    @Override
    public User searchUserInfo(String userID) {
        return adminDAO.searchUserInfo(userID);
    }

    @Override
    public List<User> searchUserList() {
        return adminDAO.searchUserList();
    }

    @Override
    public List<User> searchUserListbyAdminCheck() {
        return adminDAO.searchUserListbyAdminCheck();
    }

    @Override
    public int changeUserAdminCheck(String userID) {
        return adminDAO.changeUserAdminCheck(userID);
    }
}
