package view.login_register_view;

import controller.login_controller.Login_Controller_Impl;
import controller.register_controller.Register_Controller_Impl;
import util.AppSession;
import view.admin_home_view.Admin_Home_View;
import view.user_home_view.User_Home_View;
import vo.Members.Admin;
import vo.Members.AdminCheck;
import vo.Members.Role;
import vo.Members.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Login_Register_View {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final Login_Controller_Impl login_controller = new Login_Controller_Impl();
    private final Register_Controller_Impl register_controller = new Register_Controller_Impl();
    private final User_Home_View user_Home_View = new User_Home_View();
    private final Admin_Home_View admin_Home_View = new Admin_Home_View();

    // enum 네이밍이 Waiting/Approval/Cancel 라면 그대로 사용
    private final AdminCheck defaultAdminCheck = AdminCheck.Waiting;

    public void userLogin() throws IOException {
        System.out.println("""
                ============ [ 로그인 ] ===========
                """);
        System.out.print("아이디 입력: ");
        String userID = br.readLine();
        System.out.print("비밀번호 입력: ");
        String userPW = br.readLine();

        User found = login_controller.userlogin(userID, userPW); // ← 한 번만 호출
        if (found == null) {
            System.out.println("로그인 실패 (아이디/비밀번호 불일치)");
            return;
        }
        // 승인 상태 확인 (enum 이름이 Approval/WAITING 등 실제 정의에 맞춰 비교)
        if (found.getAdminCheck() != AdminCheck.Approval) {
            System.out.println("승인 대기/거절 상태입니다. 관리자 승인 후 이용 가능합니다.");
            return;
        }

        AppSession.get().setLoginUser(found);
        System.out.println("로그인 성공! 반갑습니다, " + found.getName() + "님.");
        user_Home_View.userHome(); // 다른 클래스에서 AppSession으로 현재 사용자 참조 가능
    }

    public void userRegister() throws IOException {
        User user = new User();
        System.out.println("""
                ============ [ 회원 가입 ] ===========
                """);

        System.out.print("이름 입력: ");
        user.setName(br.readLine());
        System.out.print("아이디 입력: ");
        user.setUserID(br.readLine());
        System.out.print("비밀번호 입력: ");
        user.setUserPW(br.readLine());
        System.out.print("전화번호 입력: ");
        user.setPhone(br.readLine());
        System.out.print("계좌번호 입력: ");
        user.setAccount(br.readLine());

        user.setAdminCheck(defaultAdminCheck); // 기본값: Waiting

        if (register_controller.registerUser(user) == 1) {
            System.out.println("회원가입 성공 (승인 대기 중)");
        } else {
            System.out.println("회원가입 실패");
        }
    }

    public void findUserID() throws IOException {
        System.out.println("""
                ============ [ 아이디 찾기 ] ===========
                """);

        System.out.print("이름 입력: ");
        String userName = br.readLine();
        System.out.print("전화번호 입력: ");
        String phone = br.readLine();

        User found = login_controller.findUserID(userName, phone);

        if (found == null) {
            System.out.println("등록된 아이디가 없습니다.");
        }else if(found.getUserID() != null) {
            System.out.println("사용자 아이디는 : " + found.getUserID() + "입니다.");
        }

    }

    public void changeUserPW() throws IOException {
        System.out.println("""
                ============ [ 비밀번호 변경 ] ===========
                """);

        System.out.print("아이디 입력: ");
        String userID = br.readLine();
        System.out.print("전화번호 입력: ");
        String phone = br.readLine();
        System.out.print("변경하실 비밀번호 입력: ");
        String newPW = br.readLine();

        int r = login_controller.changeUserPassword(userID, phone, newPW);
        switch (r) {
            case 1  -> System.out.println("비밀번호 변경 완료");
            case 0  -> System.out.println("기존 비밀번호와 동일합니다. 다른 비밀번호를 입력하세요.");
            case -1 -> System.out.println("일치하는 사용자(아이디/전화번호)가 없습니다.");
            default -> System.out.println("알 수 없는 결과: " + r);
        }
    }


    // ====================== 관리자 부분 =============================


    public void adminLogin() throws Exception {
        System.out.println("""
                ============ [ 관리자 로그인 ] ===========
                """);
        System.out.print("관리자 아이디 입력: ");
        String adminID = br.readLine();
        System.out.print("관리자 비밀번호 입력: ");
        String adminPW = br.readLine();

        Admin found = login_controller.adminlogin(adminID, adminPW);
        if (found == null) {
            System.out.println("관리자 로그인 실패");
            return;
        }
        AppSession.get().setLoginAdmin(found);
        System.out.println("관리자 로그인 성공! 반갑습니다, " + found.getAdminName() + "님.");
        admin_Home_View.adminHome();
    }

    public void adminRegister() throws IOException {
        Admin admin = new Admin();
        System.out.println("""
                ============ [ 관리자 회원 가입 ] ===========
                """);
        System.out.print("관리자 아이디 입력: ");
        admin.setAdminID(br.readLine());
        System.out.print("관리자 비밀번호 입력: ");
        admin.setAdminPW(br.readLine());
        System.out.print("관리자 이름 입력: ");
        admin.setAdminName(br.readLine());
        System.out.print("관리자 역할 입력 (총관리자/관리자): ");
        String roleInput = br.readLine();
        System.out.print("관리자 전화번호 입력: ");
        admin.setAdminPhone(br.readLine());


        admin.setRole(parseRole(roleInput)); // 문자열 → enum 변환

        if (register_controller.registerAdmin(admin) == 1) {
            System.out.println("관리자 회원가입 성공");
        } else {
            System.out.println("관리자 회원가입 실패");
        }
    }

    private Role parseRole(String s) {
        if (s == null) return Role.Admin;
        s = s.trim();
        if ("총관리자".equals(s)) return Role.Master;
        if ("관리자".equals(s)) return Role.Admin;
        // 기타 입력 방어: 영문도 허용
        if ("MASTER".equalsIgnoreCase(s)) return Role.Master;
        return Role.Admin;
    }

    public void findAdminID() throws IOException {
        System.out.println("""
                ============ [ 관리자 아이디 찾기 ] ===========
                """);

        System.out.print("관리자 이름 입력: ");
        String adminName = br.readLine();
        System.out.print("관리자 전화번호 입력: ");
        String phone = br.readLine();

        Admin found = login_controller.findAdminID(adminName, phone);

        if (found == null) {
            System.out.println("등록된 아이디가 없습니다.");
        }else if(found.getAdminID() != null) {
            System.out.println("사용자 아이디는 : " + found.getAdminID() + "입니다.");
        }
    }

    public void changeAdminPW() throws IOException {

        System.out.println("""
                ============ [ 관리자 비밀번호 변경 ] ===========
                """);

        System.out.print("관리자 ID 입력: ");
        String adminId = br.readLine();

        System.out.print("새 비밀번호 입력: ");
        String newPw = br.readLine();

        int result = login_controller.changeAdminPassword(adminId, newPw);
        switch (result) {
            case 1  -> System.out.println("비밀번호가 변경되었습니다.");
            case 0  -> System.out.println("기존 비밀번호와 동일합니다. 다른 비밀번호를 입력하세요.");
            case -1 -> System.out.println("해당 ID의 관리자가 없습니다.");
            default -> System.out.println("알 수 없는 결과 코드: " + result);
        }
    }
}
