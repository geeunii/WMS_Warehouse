// util/AppSession.java
package util;

import vo.Members.Admin;
import vo.Members.User;

import java.util.Optional;

public final class AppSession {
    private static final AppSession INSTANCE = new AppSession();

    private User  loginUser;
    private Admin loginAdmin;

    private AppSession() {}

    public static AppSession get() { return INSTANCE; }

    // 로그인/로그아웃
    public void setLoginUser(User u)  { this.loginUser = u; this.loginAdmin = null; }
    public void setLoginAdmin(Admin a){ this.loginAdmin = a; this.loginUser  = null; }
    public void logout()              { this.loginUser = null; this.loginAdmin = null; }

    // 조회
    public Optional<User>  currentUser()  { return Optional.ofNullable(loginUser); }
    public Optional<Admin> currentAdmin() { return Optional.ofNullable(loginAdmin); }

    public boolean isUser()  { return loginUser  != null; }
    public boolean isAdmin() { return loginAdmin != null; }
}
