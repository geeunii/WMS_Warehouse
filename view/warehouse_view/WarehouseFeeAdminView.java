package view.warehouse_view;

import vo.Warehouses.Warehouse;
import vo.Warehouses.WarehouseFee;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 * 창고 금액 관리 기능 사용자 인터페이스 담당
 */
public class WarehouseFeeAdminView {

    private final Scanner sc = new Scanner(System.in);

    /**
     * 창고 금액 관리 메인 메뉴
     * @return 관리자가 선택한 메뉴 번호
     */
    public int warehouseFeeMainMenu() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리]");
        System.out.println("1. 창고 요금 등록");
        System.out.println("2. 창고 요금 수정");
        System.out.println("3. 창고 요금 삭제");
        System.out.println("4. 창고 요금 조회");
        System.out.println("5. 뒤로 가기");

        return getIntInput("메뉴를 선택하세요: ");
    }


    /**
     * 1. 창고 요금 등록
     * 필요한 정보를 관리자로부터 순서대로 입력 받음
     * WarehouseFee 객체 생성하여 반환
     * @return 입력받은 정보가 모두 담긴 새로운 WarehouseFee 객체
     */
    public WarehouseFee insertWarehouseFee() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리 > 창고 요금 등록]");

        int wid = getIntInput("요금 적용 창고 번호: ");
        int wprice = getIntInput("창고 요금: ");
        Date wstartDate = getInDate("계약 시작일(YYYY-MM-DD): ");
        Date wendDate = getInDate("계약 종료일(YYYY-MM-DD): ");

        // feeID 요금번호
        return WarehouseFee.builder()
                .warehouseID(wid)             // warehouseID 설정
                .price(wprice)                // 요금 설정
                .startDate(wstartDate)        // 시작일 설정
                .endDate(wendDate)            // 종료일 설정
                .build();
    }

    /**
     * 2. 창고 요금 수정
     * 수정에 필요한 정보를 관리자로부터 순서대로 입력 받음
     * @return 수정할 창고 금액 ID, 창고 ID 와 새로운 정보가 모두 담긴 WarehouseFee 객체
     * startDate 는 고정. endDate 만 수정
     */
    public WarehouseFee updateWarehouseFee() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리 > 창고 요금 수정]");
        int feeID = getIntInput("수정할 요금 번호: ");
        int wid = getIntInput("수정 창고 번호: ");
        int wprice = getIntInput("수정 금액: ");
        Date wendDate = getInDate("수정 계약 종료일(YYYY-MM-DD): "); // 계약 시작일은 고정

        return WarehouseFee.builder()
                .id(feeID) // 수정할 ID를 id 필드에 설정
                .warehouseID(wid)
                .price(wprice)
                .endDate(wendDate)
                .build();
    }

    /**
     * 3. 창고 요금 삭제
     * 창고 금액 ID를 관리자로부터 입력 받아 삭제
     * @return 관리자가 입력한 삭제할 창고 요금 ID
     */
    public int deleteWarehouseFee() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리 > 창고 요금 삭제]");
        return getIntInput("삭제할 창고 요금의 ID: ");
    }

    /**
     * 4. 창고 요금 조회
     * 창고 요금 조회의 하위 메뉴
     * @return 관리자가 선택한 조회 번호
     */
    public int selectWarehouseFeeMenu() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 요금 관리 > 창고 요금 조회]");
        System.out.println("1. 요금 ID로 창고 요금 조회");
        System.out.println("2. 창고 ID로 창고 요금 조회");
        System.out.println("3. 뒤로 가기");

        return getIntInput("메뉴를 선택하세요: ");
    }

    /**
     * 1. 요금 ID로 창고 요금 조회
     * 창고 요금 ID (feeID) 필요
     * @return feeID
     */
    public int getFeeIdInput() {
        return getIntInput("조회할 요금 ID를 입력하세요: ");
    }

    /**
     * 2. 창고 ID로 창고 요금 조회
     * 창고 ID (warehouseID) 필요
     * @return warehouseID
     */
    public int getWarehouseIdInput() {
        return getIntInput("조회할 창고 ID를 입력하세요: ");
    }



    // --- 창고 금액 관리 입출력 유틸 메서드 ---

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
     * 처리 성공 메시지와 함께 WarehouseFee 객체 리스트 화면에 출력
     * @param message 성공 메시지
     * @param feeList 출력할 WarehouseFee 객체 리스트
     */
    public void displaySuccess(String message, List<WarehouseFee> feeList) {
        System.out.println("\n[처리 성공] " + message);
        if (feeList == null || feeList.isEmpty()) {
            System.out.println("> 조회된 요금 정보 없음");
        } else {
            System.out.println("> " + feeList.size() + " 건의 요금 정보가 조회");
            for (WarehouseFee fee : feeList) {
                System.out.println(fee); // WarehouseFee 의 toString() 호출
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
     * 처리 성공 메시지와 함께 WarehouseFee 객체를 화면에 출력
     * @param message 성공 메시지
     * @param fee 출력할 WarehouseFee 객체
     */
    public void displaySuccess(String message, WarehouseFee fee) {
        System.out.println("\n[처리 성공] " + message);
        if (fee != null) {
            System.out.println("> " + fee); // WarehouseFee 의 toString() 호출
        }
    }

    /**
     * 처리 실패 메시지
     * @param message 실패 메시지
     */
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
                java.util.Date utilDate = format.parse(inputLine);
                return new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                System.out.println("날짜 형식이 잘못되었습니다. YYYY-MM-DD 형식으로 다시 입력해주세요.");
            }
        }
    }
}