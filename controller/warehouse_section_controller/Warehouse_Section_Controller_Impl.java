package controller.warehouse_section_controller;

import model.warehouse_service.WarehouseSectionDAO;
import view.warehouse_view.WarehouseSectionAdminView;
import vo.Warehouses.WarehouseSection;

import java.util.List;

public class Warehouse_Section_Controller_Impl implements Warehouse_Section_Controller {

    private final WarehouseSectionDAO sectionDAO;
    private final WarehouseSectionAdminView sectionAdminView;

    private static Warehouse_Section_Controller_Impl controller;

    private Warehouse_Section_Controller_Impl() {
        this.sectionDAO = WarehouseSectionDAO.getInstance();
        this.sectionAdminView = new WarehouseSectionAdminView();
    }

    private static Warehouse_Section_Controller_Impl getInstance() {
        if (controller == null) {
            controller = new Warehouse_Section_Controller_Impl();
        }

        return controller;
    }

    // 구역 선택 메뉴
    @Override
    public void choiceSectionMenu(int choice) {
        switch (choice) {
            case 1:
                insertSection();
                break;
            case 2:
                updateSection();
                break;
            case 3:
                deleteSection();
                break;
            case 4:
                selectSectionById();
                break;
            case 5:
                break;
            default:
                sectionAdminView.displayError("메뉴 번호를 잘못 입력하였습니다.");
                break;
        }
    }

    // 구역 등록(입력)
    @Override
    public WarehouseSection insertSection() {
        WarehouseSection newSection = sectionAdminView.insertWarehouseSection();
        WarehouseSection insertSection = sectionDAO.insertSection(newSection);

        if (insertSection != null) {
            sectionAdminView.displaySuccess("구역 정보가 등록되었습니다.", insertSection);
        } else {
            sectionAdminView.displayError("구역 정보 등록에 실패하였습니다.");
        }

        return insertSection;
    }

    // 구역 수정
    @Override
    public int updateSection() {
        WarehouseSection sectionUpdate = sectionAdminView.updateWarehouseSection();

        int result = sectionDAO.updateSection(sectionUpdate);

        if (result > 0) {
            sectionAdminView.displaySuccess("구역 정보가 수정되었습니다.", sectionUpdate);
        } else {
            sectionAdminView.displayError("구역 정보 수정에 실패하였습니다.");
        }

        return result;
    }

    // 구역 삭제
    @Override
    public int deleteSection() {
        int sectionDelete = sectionAdminView.deleteWarehouseSection();

        int result = sectionDAO.deleteSection(sectionDelete);

        if (result > 0) {
            sectionAdminView.displayMessage("구역 ID: " + sectionDelete + "삭제 완료");
        } else {
            sectionAdminView.displayError("구역 정보 삭제에 실패하였습니다.");
        }

        return result;
    }

    // 구역 ID 조회
    @Override
    public WarehouseSection selectSectionById() {
        int sectionId = sectionAdminView.searchWarehouseSection();

        WarehouseSection section = sectionDAO.selectById(sectionId);

        sectionAdminView.displaySuccess("구역 ID " + sectionId + " 조회 결과", section);

        return section;
    }

    @Override
    public List<WarehouseSection> selectSectionWarehouseID(int warehouseID) {
        return List.of();
    }
//    // 창고 ID 로 구역 조회
//    @Override
//    public List<WarehouseSection> selectSectionWarehouseID(int warehouseID) {
//        WarehouseSection warehouseSection = sectionDAO.getSections(warehouseID);
//        return List.of();
//    }
}
