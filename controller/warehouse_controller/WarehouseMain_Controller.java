package controller.warehouse_controller; // Impl 클래스와 동일한 패키지

import vo.Members.Admin;

import java.io.IOException;

/**
 * 창고 통합 관리 메뉴의 흐름 제어를 위한 컨트롤러 인터페이스
 */
public interface WarehouseMain_Controller {

    /**
     * 창고 통합 관리(창고/요금/구역) 메뉴 시스템을 시작
     */
    void start() throws IOException;
}