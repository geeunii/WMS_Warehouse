package model.warehouse_service;

import vo.Warehouses.Warehouse;

import java.util.List;

public interface WarehouseDAOImpl {

    /**
     * 새로운 창고 데이터를 입력하고, 생성된 ID가 포함된 객체를 반환
     * @param wh ID가 없는 신규 Warehouse 객체
     * @return DB에 저장된 후 ID까지 부여받은 Warehouse 객체, 실패 시 null
     */
    Warehouse insertWarehouse(Warehouse wh);

    /**
     * 특정 이름(또는 이름의 일부)으로 창고를 검색
     * @param wname 검색할 창고 이름
     * @return 검색 조건에 맞는 Warehouse 객체 리스트 (결과가 없으면 빈 리스트)
     */
    List<Warehouse> searchByName(String wname);

    /**
     * 모든 창고 목록을 조회
     * @return 모든 Warehouse 객체 리스트 (결과가 없으면 빈 리스트)
     */
    List<Warehouse> searchAllWarehouse();

    /**
     * 특정 지역(주소의 일부)으로 창고를 검색
     * @param waddress 검색할 주소
     * @return 검색 조건에 맞는 Warehouse 객체 리스트 (결과가 없으면 빈 리스트)
     */
    List<Warehouse> selectByLocation(String waddress);

    /**
     * 특정 사이즈로 창고를 검색
     * @param wsize 검색할 사이즈
     * @return 검색 조건에 맞는 Warehouse 객체 리스트 (결과가 없으면 빈 리스트)
     */
    List<Warehouse> selectBySize(int wsize);

    /**
     * 특정 창고의 상태를 조회
     * @param wid 조회할 창고의 ID
     * @return 창고의 상태(Status) 문자열 (결과가 없으면 null)
     */
    String getWarehouseStatus(int wid);

    /**
     * 창고 수정 기능
     * @param wh 수정할 창고. (ID 포함)
     * @return 수정된 행의 수
     */
    int updateWarehouse(Warehouse wh);

    /**
     * 창고 삭제 기능
     * @param warehouseID 삭제할 창고 ID
     * @return 삭제된 행
     */
    int deleteWarehouse(int warehouseID);

    /**
     * 창고 ID 단일 조회 기능
     * @param warehouseId 창고 ID
     * @return 창고 ID를 등록된 창고 가져옴.
     */
    Warehouse selectId(int warehouseId);
}