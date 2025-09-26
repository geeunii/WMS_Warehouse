package controller.register_controller;

import model.member_service.RegisterImpl;
import vo.Members.Admin;
import vo.Members.User;

public class Register_Controller_Impl implements Register_Controller {

    RegisterImpl register = new RegisterImpl();

    @Override
    public int registerUser(User user) {
        return register.registerUser(user);
    }

    @Override
    public int registerAdmin(Admin admin) {
        return register.registerAdmin(admin);
    }
}
