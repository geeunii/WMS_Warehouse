package view.admin_home_view;

import controller.notice_controller.NoticeControllerImpl;
import controller.request_controller.Request_Controller;
import util.AppSession;
import view.inventory_view.InventoryAdminView;
import view.member_view.AdminView;
import view.request_view.RequestAdminView;
import view.shipment_view.ShipmentAdminView;
import view.stock_view.StockAdminView;
import view.warehouse_view.WarehouseAdminView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Admin_Home_View {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    AdminView adminView = new AdminView();
    RequestAdminView requestAdminView = new RequestAdminView();
    InventoryAdminView inventoryAdminView = new InventoryAdminView();
    StockAdminView stockAdminView = new StockAdminView();
    ShipmentAdminView shipmentAdminView = new ShipmentAdminView();
    WarehouseAdminView warehouseAdminView = new WarehouseAdminView();

    public void adminHome() throws IOException {
        System.out.println("""
                ===========[ 관리자 홈 화면 ] ==========
                ============ [ 메뉴 선택 ] ============
                
                1. 회원 관리          4. 입고 관리
                2. 게시판 관리           5. 출고 관리
                3. 재고 관리            6. 창고 관리
                7. 로그 아웃
                """);

        int choice = Integer.parseInt(br.readLine());

        switch (choice) {
            case 1 -> adminView.adminMenu();

            case 2 -> {
                boolean runMenu = true;
                while (runMenu) {
                    int menuChoice = requestAdminView.mainMenu();
                    switch (menuChoice) {
                        case 1 -> new Request_Controller(0, true).run();
                        case 2 -> new NoticeControllerImpl(true).run();
                        case 3 -> {
                            System.out.println("뒤로갑니다.");
                            runMenu = false; // while 종료
                        }
                    }
                }

            }
        }
    }

}






