package view.warehouse_view;

import vo.Warehouses.Warehouse;

import java.util.List;
import java.util.Scanner;

public class WarehouseAdminView {

    private final Scanner sc = new Scanner(System.in);

    // 로그인 후 2번 '창고관리' 눌렀을 때.
    // 창고 요금 CRUD, 구역 CRUD
    // 조회 눌렀을 시 요금 조회, 구역 조회
    public int warehouseMainMenu() {
        System.out.println("[\n본사 관리자 > 창고 통합 관리 > 창고 관리]");
        System.out.println("1. 창고 등록");
        System.out.println("2. 창고 수정");
        System.out.println("3. 창고 삭제");
        System.out.println("4. 창고 조회");
        System.out.println("5. 뒤로 가기");

        return getIntInput("메뉴를 선택하세요: ");
    }



    // 1. 창고 등록
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

    // 2. 창고 수정
    public Warehouse updateWarehouse() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 관리 > 창고 수정]");
        int wid = getIntInput("수정할 창고의 ID: ");
        System.out.println("----------------------------");
        String wname = getInput("수정 창고 이름: ");
        String waddress = getInput("수정 창고 주소: ");
        String wcity = getInput("수정 창고 도시: ");
        String wstatus = getInput("수정 창고 상태: ");

        int wcapacity = getIntInput("수정 창고 최대 수용량: ");
        int warea = getIntInput("수정 창고 면적: ");
        int wheight = getIntInput("수정 층고: ");
        int newMid = getIntInput("수정 담당 관리자 ID: ");

        return Warehouse.builder()
                .id(wid) // [버그 수정] 수정할 ID를 id 필드에 설정
                .warehouseName(wname)
                .warehouseAddress(waddress)
                .warehouseCityName(wcity)
                .warehouseStatus(wstatus)
                .maxCapacity(wcapacity)
                .warehouseArea(warea)
                .floorHeight(wheight)
                .mid(newMid) // [버그 수정] 새로운 관리자 ID를 mid 필드에 설정
                .build();
    }

    // 3. 창고 삭제
    public int deleteWarehouse() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 관리 > 창고 삭제]");
        return getIntInput("삭제할 창고의 ID: ");
    }

    // 4. 창고 조회
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


    /*
    창고 관리 유틸 메소드
     */
    public String getInput(String prompt) {
        System.out.print("> " + prompt);
        return sc.nextLine();
    }

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

    // List<Warehouse> 출력
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

    public void displayMessage(String message) {
        System.out.println(message);
    }


    public void displaySuccess(String message, Warehouse warehouse) {
        System.out.println("\n[처리 성공] " + message);
        // System.out.println("생성된 창고 ID: " + warehouse.getId());
        // System.out.println("등록 정보: " + warehouse);
    }

    public void displayError(String message) {
        System.out.println("\n[처리 실패] " + message);
    }

}