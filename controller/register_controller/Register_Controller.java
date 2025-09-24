package controller.register_controller;

import vo.Members.Admin;
import vo.Members.User;

public interface Register_Controller {
    public int registerUser(User user);
    public int registerAdmin(Admin admin);

}
