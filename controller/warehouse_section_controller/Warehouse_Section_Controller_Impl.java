package controller.warehouse_section_controller;

import model.warehouse_service.WarehouseSectionDAO;
import util.AppSession;
import view.warehouse_view.WarehouseSectionAdminView;
import vo.Members.Admin;
import vo.Members.Role;
import vo.Warehouses.WarehouseSection;

import java.util.List;
import java.util.Optional;

public class Warehouse_Section_Controller_Impl implements Warehouse_Section_Controller {

    private final WarehouseSectionDAO sectionDAO;
    private final WarehouseSectionAdminView sectionAdminView;

    // 싱글톤
    private static Warehouse_Section_Controller_Impl controller;

    private Warehouse_Section_Controller_Impl() {
        this.sectionDAO = WarehouseSectionDAO.getInstance();
        this.sectionAdminView = new WarehouseSectionAdminView();
    }

    public static Warehouse_Section_Controller_Impl getInstance() {
        if (controller == null) {
            controller = new Warehouse_Section_Controller_Impl();
        }

        return controller;
    }

    // 구역 선택 메뉴
    @Override
    public void choiceSectionMenu(int choice) {
        switch (choice) {
            case 1: // 구역 등록
                insertSection();
                break;
            case 2: // 구역 수정
                updateSection();
                break;
            case 3: // 구역 삭제
                deleteSection();
                break;
            case 4: // 구역 조회
                selectSectionById();
                break;
            case 5:
                break;
            default:
                sectionAdminView.displayError("메뉴 번호를 잘못 입력하였음");
                break;
        }
    }

    // 구역 등록(입력)
    @Override
    public WarehouseSection insertSection() {

        Optional<Admin> currentAdminOpt = AppSession.get().currentAdmin();

        if (currentAdminOpt.isEmpty() || currentAdminOpt.get().getRole() != Role.Master) {
            sectionAdminView.displayError("총관리자(Master) 권한이 필요합니다.");
            return null;
        }

        // View 호출 -> 사용자로부터 등록할 구역 정보 받아옴
        WarehouseSection newSection = sectionAdminView.insertWarehouseSection();
        // DAO 에 정보 전달-> DB에 저장 요청 -> ID가 부여된 객체 받음
        WarehouseSection insertSection = sectionDAO.insertSection(newSection);

        if (insertSection != null) {
            sectionAdminView.displaySuccess("구역 정보 등록 성공 ", insertSection);
        } else {
            sectionAdminView.displayError("구역 정보 등록 실패");
        }

        return insertSection;
    }

    // 구역 수정
    @Override
    public int updateSection() {

        Optional<Admin> currentAdminOpt = AppSession.get().currentAdmin();

        if (currentAdminOpt.isEmpty() || currentAdminOpt.get().getRole() != Role.Master) {
            sectionAdminView.displayError("총관리자(Master) 권한이 필요합니다.");
            return 0;
        }

        WarehouseSection sectionUpdate = sectionAdminView.updateWarehouseSection();

        int result = sectionDAO.updateSection(sectionUpdate);

        if (result > 0) {
            sectionAdminView.displaySuccess("구역 정보 수정 성공 ", sectionUpdate);
        } else {
            sectionAdminView.displayError("구역 정보 수정 실패");
        }

        return result;
    }

    // 구역 삭제
    @Override
    public int deleteSection() {

        Optional<Admin> currentAdminOpt = AppSession.get().currentAdmin();

        if (currentAdminOpt.isEmpty() || currentAdminOpt.get().getRole() != Role.Master) {
            sectionAdminView.displayError("총관리자(Master) 권한이 필요합니다.");
            return 0;
        }

        int sectionDelete = sectionAdminView.deleteWarehouseSection();

        int result = sectionDAO.deleteSection(sectionDelete);

        if (result > 0) {
            sectionAdminView.displayMessage("구역 ID: " + sectionDelete + "삭제 완료");
        } else {
            sectionAdminView.displayError("구역 정보 삭제 실패");
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
        List<WarehouseSection> sectionList = sectionDAO.getSections(warehouseID);
        sectionAdminView.displaySuccess("창고 ID " + warehouseID + "의 전체 구역 조회 결과", sectionList);
        return sectionList;
    }

}
