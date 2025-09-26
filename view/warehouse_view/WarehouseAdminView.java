package view.warehouse_view;

import vo.Warehouses.Warehouse;

import java.util.List;
import java.util.Scanner;

/**
 * 창고 관리 기능 사용자 인터페이스 담당
 * 사용자와 직접 상호작용
 */
public class WarehouseAdminView {

    private final Scanner sc = new Scanner(System.in);

    /**
     * 창고 관리 메인 메뉴
     * @return 관리자가 선택한 메뉴 번호
     */
    public int warehouseMainMenu() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 관리]");
        System.out.println("1. 창고 등록");
        System.out.println("2. 창고 수정");
        System.out.println("3. 창고 삭제");
        System.out.println("4. 창고 조회");
        System.out.println("5. 뒤로 가기");

        return getIntInput("메뉴를 선택하세요: ");
    }


    /**
     * 1. 창고 등록
     * 필요한 정보를 관리자로부터 순서대로 입력 받음
     * warehouseID -> INT AUTO INCREMENT
     * @return 입력받은 정보가 모두 담긴 새로운 Warehouse 객체
     */
    public Warehouse insertWarehouse() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 관리 > 창고 등록]");

        String wname = getInput("창고 이름: ");
        String waddress = getInput("창고 주소: ");
        String wcity = getInput("창고 도시: ");

        String wstatus = getInput("창고 상태: ");
        int wcapacity = getIntInput("창고 최대 수용량: ");
        int warea = getIntInput("창고 면적: ");
        int wheight = getIntInput("층고: ");
        int mid = getIntInput("담당 관리자 ID: ");

        return Warehouse.builder()
                .warehouseName(wname)
                .warehouseAddress(waddress)
                .warehouseCityName(wcity)
                .warehouseStatus(wstatus)
                .maxCapacity(wcapacity)
                .warehouseArea(warea)
                .floorHeight(wheight)
                .mid(mid)
                .build();
    }

    /**
     * 2. 창고 수정
     * 수정에 필요한 정보를 관리자로부터 순서대로 입력 받음
     * @return 수정할 창고의 ID 와 새로운 정보가 모두 담긴 Warehouse 객체
     */
    public Warehouse updateWarehouse() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 관리 > 창고 수정]");
        // 먼저 창고 ID로 구분
        int wid = getIntInput("수정할 창고의 ID: ");
        // System.out.println("----------------------------");
        String wname = getInput("수정 창고 이름: ");
        String waddress = getInput("수정 창고 주소: ");
        String wcity = getInput("수정 창고 도시: ");
        String wstatus = getInput("수정 창고 상태: ");

        int wcapacity = getIntInput("수정 창고 최대 수용량: ");
        int warea = getIntInput("수정 창고 면적: ");
        int wheight = getIntInput("수정 층고: ");
        int newMid = getIntInput("수정 담당 관리자 ID: ");

        return Warehouse.builder()
                .id(wid) // 수정할 대상의 ID
                .warehouseName(wname)
                .warehouseAddress(waddress)
                .warehouseCityName(wcity)
                .warehouseStatus(wstatus)
                .maxCapacity(wcapacity)
                .warehouseArea(warea)
                .floorHeight(wheight)
                .mid(newMid) // 새로운 관리자 ID를 mid 필드에 설정
                .build();
    }

    /**
     * 3. 창고 삭제
     * 창고 ID를 관리자로부터 입력 받아 삭제
     * @return 관리자가 입력한 삭제할 창고 ID
     */
    public int deleteWarehouse() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 관리 > 창고 삭제]");
        return getIntInput("삭제할 창고의 ID: ");
    }

    /**
     * 4. 창고 조회
     * 창고 조회의 하위 메뉴
     * @return 관리자가 선택한 조회 번호
     */
    public int selectWarehouseMenu() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 관리 > 창고 조회]");
        System.out.println("1. 전체 창고 조회");
        System.out.println("2. 소재지별 창고 조회");
        System.out.println("3. 창고명으로 조회");
        System.out.println("4. 창고 면적 조회");
        System.out.println("5. 창고 상태 조회");
        System.out.println("6. 뒤로 가기");

        return getIntInput("메뉴를 선택하세요: ");
    }


    // --- 창고 관리 입출력 유틸 메서드 ---

    /**
     * 관리자에게 메시지를 보여주고 문자열 한 줄을 입력받아 반환
     * @param prompt 관리자에게 보여줄 메시지
     * @return 관리자가 입력한 문자열
     */
    public String getInput(String prompt) {
        System.out.print("> " + prompt);
        return sc.nextLine();
    }

    /**
     * 관리자에게 메시지를 보여주고 정수를 입력받아 반환
     * 숫자가 아닌 값이 입력(NumberFormatException)되면 메시지 보여주고 재입력 요청
     * @param prompt 관리자에게 보여줄 메시지
     * @return 관리자가 입력한 정수
     */
    public int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print("> " + prompt);
                String inputLine = sc.nextLine();
                return Integer.parseInt(inputLine);
            } catch (NumberFormatException e) {
                System.out.println("숫자로만 입력해주세요.");
            }
        }
    }

    /**
     * 처리 성공 메시지와 함께 Warehouse 객체 리스트 화면에 출력
     * @param message 성공 메시지
     * @param warehouseList 출력할 Warehouse 객체 리스트
     */
    public void displaySuccess(String message, List<Warehouse> warehouseList) {
        System.out.println("\n[처리 성공] " + message);
        if (warehouseList == null || warehouseList.isEmpty()) {
            System.out.println("> 조회된 창고 없음");
        } else {
            System.out.println(warehouseList.size() + " 개의 창고가 조회");
            for (Warehouse wh : warehouseList) {
                System.out.println(wh);
            }
        }
    }

    /**
     * 일반 정보 메시지 출력
     * @param message 출력 메시지
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }


    /**
     * 처리 성공 메시지와 함께 Warehouse 객체를 화면에 출력
     * @param message 성공 메시지
     * @param warehouse 출력할 Warehouse 객체
     */
    public void displaySuccess(String message, Warehouse warehouse) {
        System.out.println("\n[처리 성공] " + message);
        if (warehouse != null) {
            System.out.println("> " + warehouse);
        }
    }

    /**
     * 처리 실패 메시지
     * @param message 실패 메시지
     */
    public void displayError(String message) {
        System.out.println("\n[처리 실패] " + message);
    }

}