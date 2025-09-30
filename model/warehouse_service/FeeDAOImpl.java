package model.warehouse_service;

import vo.Warehouses.WarehouseFee;

import java.util.List;

/**
 * 창고 요금 데이터하기 위한 DAO 인터페이스
 * 요금 등록, 수정, 삭제, 조회 기능을 가짐
 */
public interface FeeDAOImpl {

    /**
     * 새로운 창고 요금 데이터를 입력하고, DB에 생성된 WarehouseID 까지 포함된 객체를 반환
     * @param wf warehouseID와 요금 정보가 담긴 신규 WarehouseFee 객체
     * @return DB의 Primary Key(feeID)가 설정된 WarehouseFee 객체, 실패 시 null
     */
    WarehouseFee insertFee(WarehouseFee wf);


    /**
     * 기존 요금 정보를 수정 (예: 계약 기간, 단가 변경)
     * @param fee 수정할 정보가 담긴 WarehouseFee 객체 (id 포함 필수)
     * @return 성공적으로 수정된 row의 수 (보통 1), 실패 시 0
     */
    int updateFee(WarehouseFee fee);


    /**
     * 특정 요금 정보를 id(feeID)를 이용해 삭제
     * @param feeId 삭제할 요금 정보의 ID
     * @return 성공적으로 삭제된 row의 수 (보통 1), 실패 시 0
     */
    int deleteFee(int feeId);

    /**
     * 요금 ID(PK)를 이용해 특정 요금 정보 하나를 조회
     * @param feeId 조회할 요금 정보의 ID
     * @return 조회된 WarehouseFee 객체, 없으면 null
     */
    WarehouseFee selectFeeById(int feeId);

    /**
     * 특정 창고에 소속된 모든 요금 정보를 조회
     * @param warehouseId 정보를 조회할 창고의 ID
     * @return 해당 창고의 모든 요금 정보 리스트, 결과가 없으면 빈 리스트
     */
    List<WarehouseFee> selectFeeByWarehouseId(int warehouseId);


}