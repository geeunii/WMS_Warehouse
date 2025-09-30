package view.inventory_view;

import controller.inventory_controller.Inventory_Controller_Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InventoryAdminView {
    CategorySpace_Choice_Menu CategorySpace_Choice_Menu = new CategorySpace_Choice_Menu();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Inventory_Controller_Impl inventoryController = new Inventory_Controller_Impl();

    public void Inventory_Management_Menu() throws IOException {
        while (true) {
            System.out.println("""
             ===========[ 재고 관리 화면 ]============
             ============ [ 메뉴 선택 ] =============
             
             1. 재고 조회               4. 창고 현황 리스트
             2. 카테고리별 재고 조회      5. 거래처 현황 리스트
             3. 공간별 재고 조회         6. 재고 실사 등록
             7. 뒤로 가기
             """);
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1-> Manager_InvenSearch();
                case 2-> Manager_InvenSearch_Category();
                case 3-> Manager_InvenSearch_Space();
                case 4-> Manager_WareHouse_Status_list();
                case 5-> Manager_Correspondent_Status_list();
                case 6-> Inventory_log_Update();
            case 7-> {
                    return;
                }
                default -> System.out.println("메뉴에 있는 번호를 선택해주세요.");
            }
        }

    }

    private void Manager_InvenSearch() {
        System.out.println("============================================================================");
        System.out.println("   입고날짜   | 거래처 이름 | 가구 이름 | 수량 | 금약 합계 | 보관 창고명 | 창고 상태");
        System.out.println("============================================================================");

    }
    private void Manager_InvenSearch_Category() throws IOException {
        CategorySpace_Choice_Menu.category_choice_Menu();
    }
    private void Manager_InvenSearch_Space() throws IOException {
        CategorySpace_Choice_Menu.Space_choice_Menu();
    }

    private void Manager_WareHouse_Status_list() {

    }

    private void Manager_Correspondent_Status_list() {

    }

    private void Inventory_log_Update() {

    }

}
