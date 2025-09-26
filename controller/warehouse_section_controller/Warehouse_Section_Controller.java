package controller.warehouse_section_controller;

import vo.Warehouses.WarehouseSection;

import java.util.List;

// 창고 구역 관리
public interface Warehouse_Section_Controller {

    /**
     * 창고 구역 관리 메인 메뉴
     * @param choice 사용자가 메뉴에서 선택한 번호
     */
    void choiceSectionMenu(int choice);

    /**
     * 새로운 구역 등록
     * @return DB에 등록 완료된 WarehouseSection 객체 (ID 포함. auto increment)
     */
    WarehouseSection insertSection();

    /**
     * 특정 구역 수정
     * @return 수정된 행의 수. 성공 1, 실패 0
     */
    int updateSection();

    /**
     * 특정 구역 삭제
     * @return 삭제된 행의 수. 성공 1, 실패 0
     */
    int deleteSection();

    /**
     * 특정 구역 ID로 정보 조회
     * @return 조회된 WarehouseSection 객체
     */
    WarehouseSection selectSectionById();

    /**
     * 특정 창고 ID에 소속된 모든 구역 목록 조회
     * @param warehouseID 조회할 창고 ID
     * @return 해당 창고의 모든 구역 정보 리스트
     */
    List<WarehouseSection> selectSectionWarehouseID(int warehouseID);
}
