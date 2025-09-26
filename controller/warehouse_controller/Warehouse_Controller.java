package controller.warehouse_controller;

import vo.Members.Admin;
import vo.Warehouses.Warehouse;

import java.io.IOException;
import java.util.List;

/**
 * 창고 관리 기능 컨트롤러 인터페이스
 * 관리자가 요청할 수 있는 창고 관련 기능 정의
 */
public interface Warehouse_Controller {


    /**
     * 새로운 창고 등록하도록 요청
     *
     * @param warehouseName     창고 이름
     * @param warehouseAddress  창고 주소
     * @param warehouseStatus   창고 상태
     * @param warehouseCityName 창고 도시 이름
     * @param maxCapacity       창고 최대 수용 용량
     * @param warehouseArea     창고 면적
     * @param floorHeight       층고
     * @param mid               관리자 ID
     * @param regDate           등록일
     */
//    void insertWarehouse(
//            String warehouseName,
//            String warehouseAddress,
//            String warehouseStatus,
//            String warehouseCityName,
//            int maxCapacity,
//            int warehouseArea,
//            int floorHeight,
//            int mid,
//            Date regDate);

    /**
     * 관리자 창고 선택 메뉴
     *
     * @param choice 선택된 메뉴 번호
     */
    void choiceWarehouseMenu(int choice) throws IOException;

    // 창고 등록(입력) 기능
    // Warehouse insertWarehouse();

    // 창고 ID 로 조회 후 현재 창고 정보 수정
    int updateWarehouse() throws IOException;

    // 창고 ID 로 조회 후 창고 삭제
    int deleteWarehouse() throws IOException;


    // 창고 등록 - 총관리자만 가능
    Warehouse insertWarehouse() throws IOException;

    /**
     * 창고 정보 이름으로 조회 후 화면에 표시하도록 요청
     *
     * @param wname 창고 이름
     */
    List<Warehouse> selectByName(String wname);

    // 모든 창고 목록 조회 후 화면에 표시하도록 요청
    List<Warehouse> selectAllWarehouse();

    /**
     * 창고 정보 지역으로 조회 후 화면에 표시하도록 요청
     *
     * @param waddress 창고 주소
     */
    List<Warehouse> selectByLocation(String waddress);

    /**
     * 창고 정보 사이즈(면적)로 조회 후 화면에 표시하도록 요청
     *
     * @param wsize 창고 면적
     */
    List<Warehouse> selectBySize(int wsize);

    /**
     * 창고 ID 로 조회 후 현재 창고의 상태를 화면에 표시하도록 요청
     *
     * @param wid 창고 아이디
     */
    String getWarehouseStatus(int wid);
}
