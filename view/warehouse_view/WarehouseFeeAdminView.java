package view.warehouse_view;

import vo.Warehouses.Warehouse;
import vo.Warehouses.WarehouseFee;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class WarehouseFeeAdminView {

    private final Scanner sc = new Scanner(System.in);

    // 로그인 후 2번 '창고관리' 눌렀을 때.
    // 창고 요금 CRUD, 구역 CRUD
    // 조회 눌렀을 시 요금 조회, 구역 조회
    public int warehouseFeeMainMenu() {
        System.out.println("[\n본사 관리자 > 창고 통합 관리 > 창고 요금 관리]");
        System.out.println("1. 창고 요금 등록");
        System.out.println("2. 창고 요금 수정");
        System.out.println("3. 창고 요금 삭제");
        System.out.println("4. 창고 요금 조회");
        System.out.println("5. 뒤로 가기");

        return getIntInput("메뉴를 선택하세요: ");
    }


    // 1. 창고 요금 등록
    public WarehouseFee insertWarehouseFee() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리 > 창고 요금 등록]");

        int wprice = getIntInput("창고 요금: ");
        Date wstartDate = getInDate("계약 시작일(YYYY-MM-DD): ");
        Date wendDate = getInDate("계약 종료일(YYYY-MM-DD): ");
        int wid = getIntInput("요금 적용 창고 번호: ");

        // feeID 요금번호
        return WarehouseFee.builder()
                .price(wprice)                // 요금 설정
                .startDate(wstartDate)        // 시작일 설정
                .endDate(wendDate)            // 종료일 설정
                .warehouseID(wid)             // warehouseID 설정
                .build();
    }

    // 2. 창고 요금 수정
    public WarehouseFee updateWarehouseFee() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리 > 창고 요금 수정]");
        int feeID = getIntInput("수정할 요금 번호: ");
        int wprice = getIntInput("수정 금액: ");
        Date wendDate = getInDate("수정 계약 종료일(YYYY-MM-DD): "); // 계약 시작일은 고정
        int wid = getIntInput("수정 창고 번호: ");

        return WarehouseFee.builder()
                .id(feeID) // 수정할 ID를 id 필드에 설정
                .price(wprice)
                .endDate(wendDate)
                .warehouseID(wid)
                .build();
    }

    // 3. 창고 요금 삭제
    public int deleteWarehouseFee() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리 > 창고 요금 삭제]");
        return getIntInput("삭제할 창고 요금의 ID: ");
    }

    // 4. 창고 요금 조회
    // 요금 번호 조회 feeID
    // 창고 ID 조회
    // 총 2개
    public int selectWarehouseFeeMenu() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리 > 창고 요금 조회]");
        System.out.println("1. 요금 ID로 창고 요금 조회");
        System.out.println("2. 창고 ID로 창고 요금 조회");
        System.out.println("3. 뒤로 가기");

        return getIntInput("메뉴를 선택하세요: ");
    }

    public int getFeeIdInput() {
        return getIntInput("조회할 요금 ID를 입력하세요: ");
    }

    public int getWarehouseIdInput() {
        return getIntInput("조회할 창고 ID를 입력하세요: ");
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

    public void displaySuccess(String message, List<WarehouseFee> feeList) {
        System.out.println("\n[처리 성공] " + message);
        if (feeList == null || feeList.isEmpty()) {
            System.out.println("> 조회된 요금 정보가 없습니다.");
        } else {
            System.out.println("> 총 " + feeList.size() + " 건의 요금 정보가 조회되었습니다.");
            for (WarehouseFee fee : feeList) {
                System.out.println(fee); // WarehouseFee 의 toString() 호출
            }
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }


    public void displaySuccess(String message, WarehouseFee fee) {
        System.out.println("\n[처리 성공] " + message);
        if (fee != null) {
            System.out.println("> " + fee); // WarehouseFee 의 toString() 호출
        }
    }

    public void displayError(String message) {
        System.out.println("\n[처리 실패] " + message);
    }

    /**
     * 사용자로부터 "yyyy-MM-dd" 형식의 날짜를 안전하게 입력받아 Date 객체로 반환
     * @param prompt 사용자에게 보여줄 메시지
     * @return 변환된 Date 객체
     */
    private java.sql.Date getInDate(String prompt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            try {
                System.out.print("> " + prompt);
                String inputLine = sc.nextLine();
                // 1. 먼저 java.util.Date로 파싱
                java.util.Date utilDate = format.parse(inputLine);
                // 2. getTime()으로 long 값을 얻어와 java.sql.Date 객체를 생성하여 반환
                return new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                System.out.println("날짜 형식이 잘못되었습니다. YYYY-MM-DD 형식으로 다시 입력해주세요.");
            }
        }
    }
}