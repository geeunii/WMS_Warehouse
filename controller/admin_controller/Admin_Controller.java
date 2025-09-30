package controller.admin_controller;

import vo.Members.Admin;
import vo.Members.User;

import java.util.List;
import java.util.Optional;

public interface Admin_Controller {
    Optional<Admin> searchMyInfoByMid(long mid);
    public int updateMyInfo(Admin admin, String userID);
    public int deleteMyInfo(String adminID);
    public User searchUserInfo(String userID);
    public List<User> searchUserList();
    public List<User> searchUserListbyAdminCheck();
    public int changeUserAdminCheck(String userID);
}
