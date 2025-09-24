package model.member_service;

import vo.Members.Admin;
import vo.Members.User;

import java.util.List;
import java.util.Optional;

public interface Admin_Interface {
    public Optional<Admin> searchMyInfoByMid(long mid);
    public int updateMyInfo(Admin admin, String adminID);
    public int deleteMyInfo(String adminID);
    public User searchUserInfo(String userID);
    public List<User> searchUserList();
    public List<User> searchUserListbyAdminCheck();
    public int changeUserAdminCheck(String userID);
}
