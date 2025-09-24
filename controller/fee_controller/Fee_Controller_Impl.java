package controller.fee_controller;

import model.warehouse_service.FeeDAO;
import view.warehouse_view.WarehouseFeeAdminView;
import vo.Warehouses.WarehouseFee;

import java.util.List;

public class Fee_Controller_Impl implements Fee_Controller {

    private final FeeDAO feeDAO;
    private final WarehouseFeeAdminView feeView;

    private static Fee_Controller_Impl controller;

    public Fee_Controller_Impl() {
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
    public void choiceFeeMenu(int choice) {
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
                feeView.displayError("메뉴 번호를 잘못 입력하였습니다.");
                break;
        }
    }

    private void handleFeeSearchMenu(int choice) {
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
                feeView.displayError("조회 메뉴 번호를 잘못 입력하였습니다.");
                break;
        }
    }


    /// ////////////////////// 기능

    // 창고 요금 등록
    @Override
    public WarehouseFee insertFee() {
        WarehouseFee newFee = feeView.insertWarehouseFee();
        WarehouseFee warehouseFee = feeDAO.insertFee(newFee);

        if (insertFee() != null) {
            feeView.displaySuccess("요금 정보가 등록되었습니다.", insertFee());
        } else {
            feeView.displayError("요금 정보 등록에 실패하였습니다.");
        }
        return insertFee();
    }

    // 창고 요금 수정
    @Override
    public int updateFee() {
        WarehouseFee feeUpdate = feeView.updateWarehouseFee();

        int result = feeDAO.updateFee(feeUpdate);

        if (result > 0) {
            feeView.displaySuccess("요금 정보가 수정되었습니다.", feeUpdate);
        } else {
            feeView.displayError("요금 정보 수정이 실패하였습니다.");
        }
        return result;
    }

    // 창고 요금 삭제
    @Override
    public int deleteFee() {
        int feeDelete = feeView.deleteWarehouseFee();

        int result = feeDAO.deleteFee(feeDelete);

        if (result > 0) {
            feeView.displayMessage("요금 ID " + feeDelete + "가 삭제되었습니다.");
        } else {
            feeView.displayError("요금 삭제가 실패하였습니다.");
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
