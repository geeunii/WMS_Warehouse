package model.member_service;

import vo.Members.Admin;
import vo.Members.User;

public interface Register_Interface {
    public int registerUser(User user);
    public int registerAdmin(Admin admin);
}
