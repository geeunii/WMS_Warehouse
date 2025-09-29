package controller.fee_controller;

//import vo.Warehouses.WarehouseFee;

import vo.Warehouses.WarehouseFee;

import java.io.IOException;
import java.util.List;

public interface Fee_Controller {

    /**
     * 요금 관리 메인 메뉴의 선택에 따라 기능 분류
     * @param choice 사용자의 메뉴 선택 번호
     */
    void choiceFeeMenu(int choice) throws IOException;

    /**
     * View 로부터 요금 등록 요청을 받아 처리
     * @return 등록 완료된 WarehouseFee 객체 (생성된 ID 포함)
     */
    WarehouseFee insertFee() throws IOException;

    /**
     * View 로부터 요금 수정 요청을 받아 처리
     * @return 수정된 행의 수 (성공: 1, 실패: 0)
     */
    int updateFee() throws IOException;

    /**
     * View 로부터 요금 삭제 요청을 받아 처리
     * @return 삭제된 행의 수 (성공: 1, 실패: 0)
     */
    int deleteFee() throws IOException;

    /**
     * 요금 ID로 특정 요금 정보를 조회
     * @param feeId 조회할 요금의 ID
     * @return 조회된 WarehouseFee 객체
     */
    WarehouseFee selectFeeById(int feeId);

    /**
     * 창고 ID로 해당 창고의 모든 요금 정보를 조회
     * @param warehouseId 조회할 창고의 ID
     * @return 조회된 WarehouseFee 객체 리스트
     */
    List<WarehouseFee> selectFeesByWarehouseId(int warehouseId);

}
