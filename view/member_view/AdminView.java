package view.member_view;

import controller.admin_controller.Admin_Controller_Impl;
import util.AppSession;
import vo.Members.Admin;
import vo.Members.Role;
import vo.Members.User;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminView {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Admin_Controller_Impl admin_controller = new Admin_Controller_Impl();

    public void adminMenu() throws IOException {
        while (true) {
            System.out.println("""
                    ========== [ 내 정보 관리 메뉴 ] ==========
                    =============== [ 메뉴 ] ================
                    
                      1. 내 정보 조회          2. 내 정보 수정
                    
                      3. 전체 회원 조회       4. 회원 정보 조회
                    
                      5. 승인 대기 회원 조회   6. 회원 승인 관리
                    
                      7. 내 정보 삭제         8. 종료  
                    
                    =========================================
                    [메뉴 선택]: """);

            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1 -> searchMyInfo();
                case 2 -> updateMyInfo();
                case 3 -> searchUserList();
                case 4 -> searchUserInfo();
                case 5 -> searchUserListbyAdminCheck();
                case 6 -> changeUserAdminCheck();
                case 7 -> deleteMyInfo();
                case 8 -> {
                    return;
                }

            }
        }
    }

    public void searchMyInfo() {
        var opt = AppSession.get().currentAdmin();
        if (opt.isEmpty()) {
            System.out.println("로그인이 필요합니다.");
            return;
        }
        Admin sessionAdmin = opt.get();           // 세션 보관 중인 관리자
        long mid = sessionAdmin.getMid();         // ★ 불변 PK 사용

        var adminOpt = admin_controller.searchMyInfoByMid(mid); // ★ mID로 조회
        if (adminOpt.isEmpty()) {
            System.out.println("존재하지 않는 관리자입니다.");
            return;
        }

        Admin admin = adminOpt.get();             // DB에서 최신 정보
        System.out.println("""
            ========== [ 관리자 정보 ] ==========
            """);
        System.out.println("이름       : " + admin.getAdminName());
        System.out.println("아이디     : " + admin.getAdminID());
        System.out.println("전화번호   : " + admin.getAdminPhone());
        System.out.println("역할       : " + admin.getRole());
    }


    public void updateMyInfo() throws IOException {
        var opt = AppSession.get().currentAdmin();
        if (opt.isEmpty()) {
            return;
        }
        Admin me = opt.get();
        System.out.println("""
                ========== [ 관리자 정보 수정 ] ==========
                """);

        System.out.print("변경 아이디 입력 : ");
        String newadminID = br.readLine();
        System.out.print("변경 비밀번호 입력 (공백 시 유지): ");
        String newadminPW = br.readLine();
        System.out.print("변경 관리자 이름 입력 (공백 시 유지): ");
        String newadminName = br.readLine();
        System.out.print("변경 관지라 역할 입력 (Master, Admin): ");
        String newRole = br.readLine();
        System.out.print("변경 전화번호 입력: ");
        String newAdminPhone = br.readLine();

        Admin admin = new Admin();
        admin.setAdminID(newadminID);
        admin.setAdminName(newadminName);
        admin.setAdminPhone(newAdminPhone);
        admin.setAdminPW(newadminPW);
        admin.setRole(Role.valueOf(newRole));

        int row = admin_controller.updateMyInfo(admin, me.getAdminID());

        if (row > 0) {
            System.out.println("수정 성공");
        } else {
            System.out.println("수정 실패");
        }
    }

    public void searchUserList() {
        List<User> userList = new ArrayList<>();

        System.out.println("""
                ========== [ 사용자 정보 리스트 ] ==========
                """);

        userList = admin_controller.searchUserList();
        if (userList.isEmpty()) {
            System.out.println("회원 정보를 불러오지 못했습니다.");
        } else {
            for (User user : userList) {
                System.out.println("=======================================");
                System.out.println("[회원 번호] : " + user.getUid());
                System.out.println("[회원 이름] : " + user.getName());
                System.out.println("[회원 아이디] : " + user.getUserID());
                System.out.println("[회원 전화번호] : " + user.getPhone());
                System.out.println("[회원 계좌번호] : " + user.getAccount());
                System.out.println("[회원 승인 여부] : " + user.getAdminCheck());
                System.out.println("[회원 계성 생성 일자] : " + user.getCreateAt());
                System.out.println("=======================================");
            }
        }
    }

    public void searchUserInfo() throws IOException {
        System.out.println("""
                ========== [ 사용자 정보 확인 ] ==========
                """);
        System.out.print("확인할 사용자 아이디 입력: ");
        String userID = br.readLine();

        User user = admin_controller.searchUserInfo(userID);
        if (user == null) {
            System.out.println("회원 정보를 불러오지 못했습니다. ");
        } else {
            System.out.println("=======================================");
            System.out.println("[회원 번호] : " + user.getUid());
            System.out.println("[회원 이름] : " + user.getName());
            System.out.println("[회원 아이디] : " + user.getUserID());
            System.out.println("[회원 전화번호] : " + user.getPhone());
            System.out.println("[회원 계좌번호] : " + user.getAccount());
            System.out.println("[회원 승인 여부] : " + user.getAdminCheck());
            System.out.println("[회원 계성 생성 일자] : " + user.getCreateAt());
            System.out.println("=======================================");
        }
    }

    public void searchUserListbyAdminCheck() {
        List<User> userList = new ArrayList<>();

        System.out.println("""
                ========== [ 미승인 사용자 정보 리스트 ] ==========
                """);

        userList = admin_controller.searchUserListbyAdminCheck();
        if (userList.isEmpty()) {
            System.out.println("회원 리스트가 비어있습니다.");
        } else {
            for (User user : userList) {
                System.out.println("=======================================");
                System.out.println("[회원 번호] : " + user.getUid());
                System.out.println("[회원 이름] : " + user.getName());
                System.out.println("[회원 아이디] : " + user.getUserID());
                System.out.println("[회원 전화번호] : " + user.getPhone());
                System.out.println("[회원 계좌번호] : " + user.getAccount());
                System.out.println("[회원 승인 여부] : " + user.getAdminCheck());
                System.out.println("[회원 계성 생성 일자] : " + user.getCreateAt());
                System.out.println("=======================================");
            }
        }
    }

    public void changeUserAdminCheck() throws IOException {
        System.out.println("""
                ========== [ 사용자 승인 관리 ] ==========
                """);

        System.out.print("승인 할 사용자의 아이디 입력: ");
        String userID = br.readLine();

        int row = admin_controller.changeUserAdminCheck(userID);

        if (row > 0) {
            System.out.println("승인 완료");
        }else{
            System.out.println("승인 변경 실패");
        }

    }

    public void deleteMyInfo() throws IOException {
        System.out.println("""
                ========== [ 관리자 정보 삭제 ] ==========
                """);
        System.out.println("삭제하실 관리자의 아이디 입력: ");
        String adminID = br.readLine();

        int row = admin_controller.deleteMyInfo(adminID);
        if (row > 0) {
            System.out.println("삭제 성공");
        }else{
            System.out.println("삭제 실패");
        }
    }

}


