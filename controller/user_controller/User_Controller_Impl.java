package controller.user_controller;

import model.member_service.UserDAO;
import vo.Members.User;

import java.util.Optional;

public class User_Controller_Impl implements User_Controller {
    UserDAO userDAO = new UserDAO();
    @Override
    public int updateUserInfo(User user, String olduserID) {
        return userDAO.updateUserInfo(user, olduserID);
    }

    @Override
    public int deleteUserInfo(String userID) {
        return userDAO.deleteUserInfo(userID);
    }

    @Override
    public Optional<User> searchUserInfo(int uID) {
        return userDAO.searchUserInfo(uID);
    }
}
