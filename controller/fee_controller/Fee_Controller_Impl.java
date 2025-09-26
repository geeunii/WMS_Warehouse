package controller.fee_controller;

import model.warehouse_service.FeeDAO;
import util.AppSession;
import view.warehouse_view.WarehouseFeeAdminView;
import vo.Members.Admin;
import vo.Members.Role;
import vo.Warehouses.WarehouseFee;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Fee_Controller_Impl implements Fee_Controller {

    private final FeeDAO feeDAO;
    private final WarehouseFeeAdminView feeView;

    // 싱글톤
    private static Fee_Controller_Impl controller;

    private Fee_Controller_Impl() {
        this.feeDAO = FeeDAO.getInstance();
        this.feeView = new WarehouseFeeAdminView();
    }

    public static Fee_Controller_Impl getInstance() {
        if (controller == null) {
            controller = new Fee_Controller_Impl();
        }
        return controller;
    }


    // 요금 메뉴 선택
    @Override
    public void choiceFeeMenu(int choice) throws IOException {
        switch (choice) {
            case 1: // 창고 요금 등록
                insertFee();
                break;
            case 2: // 창고 요금 수정
                updateFee();
                break;
            case 3: // 요금 삭제
                deleteFee();
                break;
            case 4: // 요금 조회
                int searchChoice = feeView.selectWarehouseFeeMenu();
                handleFeeSearchMenu(searchChoice);
                break;
            case 5: // 뒤로 가기
                break;
            default:
                feeView.displayError("메뉴 번호를 잘못 입력하였음");
                break;
        }
    }

    // 요금 조회 서브 메뉴
    private void handleFeeSearchMenu(int choice) throws IOException {
        switch (choice) {
            case 1: // 요금 ID 조회
                int feeID = feeView.getFeeIdInput();
                selectFeeById(feeID);
                break;
            case 2: // 창고 ID 조회
                int warehouseID = feeView.getWarehouseIdInput();
                selectFeesByWarehouseId(warehouseID);
                break;
            case 3: // 뒤로 가기
                break;
            default:
                feeView.displayError("조회 메뉴 번호를 잘못 입력하였음");
                break;
        }
    }


    /// ////////////////////// 기능

    // 창고 요금 등록
    @Override
    public WarehouseFee insertFee() throws IOException {

        Optional<Admin> currentAdminOpt = AppSession.get().currentAdmin();

        if (currentAdminOpt.isEmpty() || currentAdminOpt.get().getRole() != Role.Master) {
            feeView.displayError("총관리자(Master) 권한이 필요합니다.");
            return null;
        }

        // View 로부터 새로운 요금 정보를 받아옴
        WarehouseFee newFee = feeView.insertWarehouseFee();

        // DAO에 전달하고, 그 결과를 'warehouseFee' 변수에 한 번만 저장
        WarehouseFee warehouseFee = feeDAO.insertFee(newFee);

        if (warehouseFee != null) {
            feeView.displaySuccess("요금 정보 등록 성공 ", warehouseFee);
        } else {
            feeView.displayError("요금 정보 등록 실패");
        }

        return warehouseFee;
    }

    // 창고 요금 수정
    @Override
    public int updateFee() throws IOException {

        Optional<Admin> currentAdminOpt = AppSession.get().currentAdmin();

        if (currentAdminOpt.isEmpty() || currentAdminOpt.get().getRole() != Role.Master) {
            feeView.displayError("총관리자(Master) 권한이 필요합니다.");
            return 0;
        }

        WarehouseFee feeUpdate = feeView.updateWarehouseFee();

        int result = feeDAO.updateFee(feeUpdate);

        if (result > 0) {
            feeView.displaySuccess("요금 정보 수정 성공 ", feeUpdate);
        } else {
            feeView.displayError("요금 정보 수정 실패");
        }
        return result;
    }

    // 창고 요금 삭제
    @Override
    public int deleteFee() throws IOException {
        Optional<Admin> currentAdminOpt = AppSession.get().currentAdmin();

        if (currentAdminOpt.isEmpty() || currentAdminOpt.get().getRole() != Role.Master) {
            feeView.displayError("총관리자(Master) 권한이 필요합니다.");
            return 0;
        }

        int feeDelete = feeView.deleteWarehouseFee();

        int result = feeDAO.deleteFee(feeDelete);

        if (result > 0) {
            feeView.displayMessage("요금 ID " + feeDelete + " 삭제");
        } else {
            feeView.displayError("요금 삭제 실패");
        }

        return result;
    }

    // 요금 ID 로 조회
    @Override
    public WarehouseFee selectFeeById(int feeId) {
        WarehouseFee warehouseFee = feeDAO.selectFeeById(feeId);

        feeView.displaySuccess("요금 ID " + feeId + " 조회 결과: ", warehouseFee);

        return warehouseFee;
    }

    // 창고 ID 로 조회
    @Override
    public List<WarehouseFee> selectFeesByWarehouseId(int warehouseId) {
        List<WarehouseFee> feeList = feeDAO.selectFeeByWarehouseId(warehouseId);
        feeView.displaySuccess("창고 ID " + warehouseId + "의 전체 요금 조회 결과: ", feeList);
        return feeList;
    }
}
