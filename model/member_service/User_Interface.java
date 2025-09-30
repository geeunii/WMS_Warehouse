package model.member_service;

import vo.Members.User;

import java.util.Optional;

public interface User_Interface {

    public int updateUserInfo(User user, String olduserID);
    public int deleteUserInfo(String userID);
    public Optional<User> searchUserInfo(int uID);
}
