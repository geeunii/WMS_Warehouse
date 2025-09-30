package view.inventory_view;

import controller.inventory_controller.Inventory_Controller_Impl;
import util.AppSession;
import vo.Inventorys.Admin_searchVo_ca;
import vo.Inventorys.User_searchVo;
import vo.Inventorys.User_searchVo_ca;
import vo.Inventorys.User_searchVo_sp;
import vo.Members.Admin;
import vo.Members.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InventoryUserVIew {

    CategorySpace_Choice_Menu CategorySpace_Choice_Menu = new CategorySpace_Choice_Menu();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Inventory_Controller_Impl inventoryController = new Inventory_Controller_Impl();

    public void Inventory_User_Menu() throws IOException {

        var opt = AppSession.get().currentUser();
        if (opt.isEmpty()) {
            System.out.println("로그인이 필요합니다.");
            return;
        }
        User sessionUser = opt.get();
        int uid = sessionUser.getUid();

        while (true) {
            System.out.println("""
             ===============[ 재고 관리 화면 ]==============
             ================ [ 메뉴 선택 ] ===============
             
             1. 재고 조회               3. 공간별 재고 조회
             2. 카테고리별 재고 조회      4. 뒤로 가기
             
             ==============================================
             """);
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1-> User_InvenSerarch(uid);
                case 2-> User_InvenSearch_Category(uid);
                case 3-> User_InvenSearch_Space(uid);
                case 4-> {
                    return;
                }
                default -> System.out.println("메뉴에 있는 번호를 선택해주세요.");
            }
        }

    }
    private void User_InvenSerarch(int uid){

        String format = "| %-12s | %-8s | %-15s | %10s | %8s | %12s | %-10s | %-7s |%n";
        System.out.println("=========================================================================================================");
        System.out.printf("| %-12s | %-8s | %-15s | %10s | %8s | %12s | %-10s | %-7s |%n",
                "입고 날짜", "등록자 이름", "가구 이름", "수량", "단가", "금액 합계", "창고명", "창고 상태");
        System.out.println("=========================================================================================================");

        List<User_searchVo> usvo = new ArrayList<>();
        usvo = inventoryController.User_inven_search(uid);
        if (usvo.isEmpty()) {
            System.out.println("재고가 비어있습니다.");
        } else {
            for (User_searchVo u : usvo) {
                System.out.printf(format,
                        u.getItemname(), u.getQuantity(), u.getTotalprice(),u.getWarehousename()
                );
            }
        }
    }

    private void User_InvenSearch_Category(int uid) throws IOException {
        String categoryName = CategorySpace_Choice_Menu.selectCategory();
        if (categoryName != null && !categoryName.isEmpty()) {
            List<User_searchVo_ca> usvoca = inventoryController.User_inven_search_ca(uid, categoryName);
            CategorySpace_Choice_Menu.printCategorySearchResult_User(usvoca);
        }
    }

    private void User_InvenSearch_Space(int uid) throws IOException {
        String categoryName = CategorySpace_Choice_Menu.selectCategory();
        if (categoryName != null && !categoryName.isEmpty()) {
            List<User_searchVo_sp> usvosp = inventoryController.User_inven_search_sp(uid, categoryName);
            CategorySpace_Choice_Menu.printSpaceSearchResult_User(usvosp);
        }

    }
}
