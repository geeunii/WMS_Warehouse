package view.inventory_view;

import controller.invenlog_controller.InvenLog_Controller_Impl;
import controller.inventory_controller.Inventory_Controller_Impl;
import util.AppSession;
import vo.Inventorys.*;
import vo.Members.Admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InventoryAdminView {
    CategorySpace_Choice_Menu CategorySpace_Choice_Menu = new CategorySpace_Choice_Menu();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Inventory_Controller_Impl inventoryController = new Inventory_Controller_Impl();
    InvenLog_Controller_Impl invenLogController = new InvenLog_Controller_Impl();

    public void Inventory_Management_Menu() throws IOException {

        var opt = AppSession.get().currentAdmin();
        if (opt.isEmpty()) {
            System.out.println("로그인이 필요합니다.");
            return;
        }
        Admin sessionAdmin = opt.get();           // 세션 보관 중인 관리자
        String role = String.valueOf(sessionAdmin.getRole());
        int mid = sessionAdmin.getMid();

        while (true) {
            System.out.println("""
             ===============[ 재고 관리 화면 ]==============
             ================ [ 메뉴 선택 ] ===============
             
             1. 재고 조회               4. 창고 현황 리스트
             2. 카테고리별 재고 조회      5. 거래처 현황 리스트
             3. 공간별 재고 조회         6. 재고 실사 등록
             7. 뒤로 가기
             
             ==============================================
             """);
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1-> {if (role.equals("Master")) {Master_InvenSearch();} else if(role.equals("Admin")){Admin_InvenSearch(mid);}}
                case 2-> {if (role.equals("Master")) {Master_InvenSearch_Category();} else if (role.equals("Admin")) {Admin_InvenSearch_Category(mid);}}
                case 3-> {if (role.equals("Master")) {Master_InvenSearch_Space();} else if (role.equals("Admin")) {Admin_InvenSearch_Space(mid);}}
                case 4-> {
                    if(role.equals("Master")) {Master_WareHouse_Status_list();}
                    else if(role.equals("Admin")) {Admin_WareHouse_Status_list(mid);}
                }
                case 5-> {
                    if(role.equals("Master")) {Master_Correspondent_Status_list();}
                    else if(role.equals("Admin")) {Admin_Correspondent_Status_list(mid);}
                }
                case 6-> Master_log_Update();
            case 7-> {
                    return;
                }
                default -> System.out.println("메뉴에 있는 번호를 선택해주세요.");
            }
        }

    }

    private void Master_InvenSearch() {
        String format = "| %-11s | %-8s | %-15s | %7s | %8s | %12s | %-10s | %-7s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf("| %-9s | %-8s | %-15s | %7s | %8s | %10s | %-10s | %-7s |%n",
                "입고 날짜", "등록자 이름", "가구 이름", "수량", "단가", "금액 합계", "창고명", "창고 상태");
        System.out.println("===========================================================================================================================");

        List<Master_searchVo> msvo = new ArrayList<>();
        msvo = inventoryController.Master_inven_search();
        if (msvo.isEmpty()) {
            System.out.println("재고가 비어있습니다.");
        } else {
            for (Master_searchVo m : msvo) {
                System.out.printf(format,
                        m.getStockdate(), m.getCustomername(), m.getItemname(), m.getQuantity(),
                        m.getItemprice(), m.getTotalprice(), m.getWarehousename(), m.getWarehousestate()
                );
            }
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }
    private void Master_InvenSearch_Category() throws IOException {
        String categoryName = CategorySpace_Choice_Menu.selectCategory();
        if (categoryName != null && !categoryName.isEmpty()) {
            List<Master_searchVo_ca> msvoca = inventoryController.Master_inven_search_ca(categoryName);
            CategorySpace_Choice_Menu.printCategorySearchResult_Master(msvoca);
        }
    }
    private void Master_InvenSearch_Space() throws IOException {
        String spacename = CategorySpace_Choice_Menu.selectSpace();
        if (spacename != null && !spacename.isEmpty()) {
            List<Master_searchVo_sp> msvosp = inventoryController.Master_inven_search_sp(spacename);
            CategorySpace_Choice_Menu.printSpaceSearchResult_Master(msvosp);
        }
    }

    private void Master_WareHouse_Status_list() {
        String format = "| %-12s | %-8s | %-15s | %10s | %8s | %12s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf("| %-12s | %-8s | %-15s | %10s | %8s | %12s |%n",
                "창고 번호", "창고 이름", "창고 위치 지역", "창고 상태", "창고 현재 사용량", "창고 수용 가능량");
        System.out.println("===========================================================================================================================");

        List<Master_warehouse_listVo> mwvo = new ArrayList<>();
        mwvo = inventoryController.Master_warehouse_list();
        if (mwvo.isEmpty()) {
            System.out.println("재고가 비어있습니다.");
        } else {
            for (Master_warehouse_listVo mw : mwvo) {
                System.out.printf(format,
                       mw.getWarehouseID(), mw.getWarehouseName(), mw.getWarehouseCityName(),
                        mw.getWarehouseStratus(), mw.getCurrentVol(), mw.getCanvol()
                );
            }
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }

    public void Admin_WareHouse_Status_list(int mid) {
        String format = "| %-12s | %-8s | %10s | %8s | %12s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf("| %-12s | %-8s | %10s | %8s | %12s |%n",
                "창고 이름", "창고 상태", "창고 구역", "창고 현재 사용량", "창고 수용 가능량");
        System.out.println("===========================================================================================================================");

        List<Admin_warehouse_listVo> awvo = new ArrayList<>();
        awvo = inventoryController.Admin_warehouse_list(mid);
        if (awvo.isEmpty()) {
            System.out.println("재고가 비어있습니다.");
        } else {
            for (Admin_warehouse_listVo aw : awvo) {
                System.out.printf(format,
                        aw.getWarehouseName(), aw.getWarehouseStatus(), aw.getSectionName(),
                        aw.getCurrentVol(), aw.getCanvol()
                );
            }
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }

    private void Master_Correspondent_Status_list() {
        String format = "| %-12s | %-8s | %-15s | %10s | %8s | %12s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf("| %-12s | %-8s | %-15s | %10s | %8s | %12s %n",
                "창고 번호", "창고 이름", "등록자 이름", "물품 아이디", "수량", "총 가격");
        System.out.println("===========================================================================================================================");

        List<Master_correspond_listVo> mcvo = new ArrayList<>();
        mcvo = inventoryController.Master_correspond_list();
        if (mcvo.isEmpty()) {
            System.out.println("재고가 비어있습니다.");
        } else {
            for (Master_correspond_listVo mc : mcvo) {
                System.out.printf(format,
                        mc.getWarehouseID(),mc.getWarehouseName(),mc.getCustomerName(),
                        mc.getItemID(),mc.getQuantity(),mc.getTotalprice()
                );
            }
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }
    private void Admin_Correspondent_Status_list(int mid) {
        String format = "| %-12s | %-8s | %-15s | %10s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf("| %-12s | %-8s | %-15s | %10s | %n",
                "등록자 이름", "물품 아이디", "수량", "총 가격");
        System.out.println("===========================================================================================================================");

        List<Admin_correspond_listVo> acvo = new ArrayList<>();
        acvo = inventoryController.Admin_correspond_list(mid);
        if (acvo.isEmpty()) {
            System.out.println("재고가 비어있습니다.");
        } else {
            for (Admin_correspond_listVo ac : acvo) {
                System.out.printf(format,
                        ac.getCustomerName(),ac.getItemID(),ac.getQuantity(),ac.getTotalprice()
                );
            }
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }

    //실사
    private void Master_log_Update() {
        int result = invenLogController.logInventoryinsert();

        if(result >0) {
            System.out.println("실사를 완료했습니다.");
        } else {
            System.out.println("실사에 실패했습니다.");
        }
        System.out.println();
    }

    private void Admin_InvenSearch(int mid) {
        String format = "| %-12s | %-8s | %-15s | %10s | %8s | %12s | %-7s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf("| %-12s | %-8s | %-15s | %10s | %8s | %12s | %-7s |%n",
                "입고 날짜", "등록자 이름", "가구 이름", "수량", "단가", "금액 합계", "보관 구역");
        System.out.println("===========================================================================================================================");

        List<Admin_searchVo> asvo = new ArrayList<>();
        asvo = inventoryController.Admin_inven_search(mid);
        if (asvo.isEmpty()) {
            System.out.println("재고가 비어있습니다.");
        } else {
            for (Admin_searchVo a : asvo) {
                System.out.printf(format,
                        a.getStockdate(), a.getCustomername(), a.getItemname(), a.getQuantity(),
                        a.getItemprice(), a.getTotalprice(), a.getSectionname()
                );
            }
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }
    private void Admin_InvenSearch_Category(int mid) throws IOException {
        String categoryName = CategorySpace_Choice_Menu.selectCategory();
        if (categoryName != null && !categoryName.isEmpty()) {
            List<Admin_searchVo_ca> asvoca = inventoryController.Admin_inven_search_ca(mid, categoryName);
            CategorySpace_Choice_Menu.printCategorySearchResult_Admin(asvoca);
        }
    }
    private void Admin_InvenSearch_Space(int mid) throws IOException {
        String spacename = CategorySpace_Choice_Menu.selectCategory();
        if (spacename != null && !spacename.isEmpty()) {
            List<Admin_searchVo_sp> asvosp = inventoryController.Admin_inven_search_sp(mid, spacename);
            CategorySpace_Choice_Menu.printSpaceSearchResult_Admin(asvosp);
        }
    }
}
