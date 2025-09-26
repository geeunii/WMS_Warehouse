package view.member_view;

import controller.user_controller.User_Controller_Impl;
import util.AppSession;
import vo.Members.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class UserView {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static User_Controller_Impl user_controller = new User_Controller_Impl();

    public boolean memberMenu() throws IOException {
        while (true) {
            System.out.println("""
                ========== [ 내 정보 관리 메뉴 ] ==========
                =============== [ 메뉴 ] ================
                
                  1. 내 정보 조회          2. 내 정보 수정
                
                  3. 회원 탈퇴             4. 종료
                
                =========================================
                [메뉴 선택]: """);

            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 1 -> searchMyInfo();
                case 2 -> updateUserInfo();
                case 3 -> {
                    if(deleteUserInfo()) {
                        return true;
                    }
                }
                case 4 -> { return false; } // ← 루프 종료
                default -> System.out.println("메뉴를 다시 선택하세요.");
            }
        }
    }

    public void searchMyInfo(){
        var opt = AppSession.get().currentUser();
        if (opt.isEmpty()) {
            System.out.println("로그인이 필요합니다.");
            return;
        }
        User user = opt.get();
        Optional<User> meOpt = user_controller.searchUserInfo(user.getUid());
        User me = meOpt.get();

        System.out.println("""
            ============ [ 내 정보 ] ============
            """);
        System.out.println("[이름]       : " + me.getName());
        System.out.println("[아이디]     : " + me.getUserID());
        System.out.println("[전화번호]   : " + me.getPhone());
        System.out.println("[계좌번호]   : " + me.getAccount());
        System.out.println("[가입일]     : " + me.getCreateAt());
    }

    public void updateUserInfo() throws IOException {
        var opt = AppSession.get().currentUser();
        User me = opt.get();
        User user = new User();
        System.out.println("""
            ========== [ 내 정보 수정 ] ==========
            """);
        System.out.print("본인 아이디 입력: ");
        user.setUserID(br.readLine());
        if(!me.getUserID().equals(user.getUserID())) {
            System.out.println("입력하신 아이디가 다릅니다. ");
        }else{
            System.out.print("수정할 이름 입력: ");
            user.setName(br.readLine());
            System.out.print("수정할 아이디 입력: ");
            user.setUserID(br.readLine());
            System.out.print("수정할 전화번호 입력: ");
            user.setPhone(br.readLine());
            System.out.print("수정할 계좌번호 입력: ");
            user.setAccount(br.readLine());

           if(user_controller.updateUserInfo(user, me.getUserID()) == 1){
               System.out.println("변경 성공");
           }else{
               System.out.println("변경 실패"); 
           }
        }
    }

    public boolean  deleteUserInfo() throws IOException {
        System.out.println("""
            ========== [ 내 정보 삭제 ] ==========
            """);
        System.out.print("본인 아이디 입력: ");
        String userID = br.readLine();
        
        if(user_controller.deleteUserInfo(userID) == 1){
            AppSession.get().logout(); // ★ 세션 초기화
            return true;
        }else{
            System.out.println("삭제 실패");
            return false;
        }
    }
}
