package controller.warehouse_controller;

import controller.fee_controller.Fee_Controller_Impl;
import controller.warehouse_section_controller.Warehouse_Section_Controller_Impl;
import view.admin_home_view.Admin_Home_View;
import view.warehouse_view.WarehouseAdminView;
import view.warehouse_view.WarehouseFeeAdminView;
import view.warehouse_view.WarehouseMainView;
import view.warehouse_view.WarehouseSectionAdminView;
import vo.Members.Admin;

import java.io.IOException;

public class WarehouseMain_Controller_Impl implements WarehouseMain_Controller{

    private final WarehouseMainView warehouseMainView = new WarehouseMainView();
    private final Admin_Home_View adminHomeView = new Admin_Home_View();

    private final Warehouse_Controller_Impl warehouseController = Warehouse_Controller_Impl.getInstance();
    private final Fee_Controller_Impl feeController = Fee_Controller_Impl.getInstance();
    private final Warehouse_Section_Controller_Impl sectionController = Warehouse_Section_Controller_Impl.getInstance();

    private static WarehouseMain_Controller_Impl controller;

    private WarehouseMain_Controller_Impl() {

    }

    public static WarehouseMain_Controller_Impl getInstance() {
        if (controller == null) {
            controller = new WarehouseMain_Controller_Impl();
        }
        return controller;
    }


    @Override
    public void start() {
        while (true) {
            // int choice = adminHomeView.adminHome();
            int choice = warehouseMainView.warehouseManagerMainMenu();
            if (choice == 4) {
                return;
            }

            switch (choice) {
                case 1: { // 창고 관리
                    WarehouseAdminView view = new WarehouseAdminView();
                    int adminChoice = view.warehouseMainMenu();
                    warehouseController.choiceWarehouseMenu(adminChoice);
                    break;
                }
                case 2: { // 창고 요금 관리
                    WarehouseFeeAdminView view = new WarehouseFeeAdminView();
                    int feeChoice = view.warehouseFeeMainMenu();
                    feeController.choiceFeeMenu(feeChoice);
                    break;
                }
                case 3: { // 창고 구역 관리
                    WarehouseSectionAdminView view = new WarehouseSectionAdminView();
                    int sectionChoice = view.warehouseSectionMainMenu();
                    sectionController.choiceSectionMenu(sectionChoice);
                    break;
                }
            }
        }
    }
}