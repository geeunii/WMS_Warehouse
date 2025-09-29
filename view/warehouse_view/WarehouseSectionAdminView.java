package view.warehouse_view; // 또는 view.section_view

import vo.Warehouses.WarehouseSection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 창고 구역 관리 기능 사용자 인터페이스 담당
 */
public class WarehouseSectionAdminView {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // private final Scanner sc = new Scanner(System.in);

    /**
     * 창고 구역 관리 메인 메뉴
     * @return 관리자가 선택한 메뉴 번호
     */
    public int warehouseSectionMainMenu() throws IOException {
        System.out.println("""
                ===========[ 창고 구역 관리 ] ==========
                ============ [ 메뉴 선택 ] ============

                   1. 구역 등록
                   2. 구역 수정
                   3. 구역 삭제
                   4. 구역 조회
                   5. 뒤로 가기

                ======================================
                ======================================
                """);

//        System.out.println("\n[본사 관리자 > 창고 통합 관리 > 창고 구역 관리]");
//        System.out.println("1. 창고 구역 등록");
//        System.out.println("2. 창고 구역 수정");
//        System.out.println("3. 창고 구역 삭제");
//        System.out.println("4. 창고 구역 조회 (ID)");
//        System.out.println("5. 뒤로 가기");

        return getIntInput("메뉴를 선택하세요: ");
    }

    /**
     * 1. 창고 구역 등록
     * 필요한 정보를 관리자로부터 순서대로 입력 받음
     * @return 입력받은 정보가 모두 담긴 새로운 WarehouseSection 객체
     */
//    public WarehouseSection insertWarehouseSection() throws IOException {
//        System.out.println("\n============ [ 구역 등록 ] ============");
//        int wid = getIntInput("구역 등록 창고 ID: ");
//        String sectionName = getInput("구역 이름 (예: A구역): ");
//        int maxVol = getIntInput("최대 허용 부피: ");
//        int currentVol = getIntInput("현재 적재 부피: ");
//        return WarehouseSection.builder()
//                .warehouseID(wid)
//                .sectionName(sectionName)
//                .maxVol(maxVol)
//                .currentVol(currentVol)
//                .build();
//    }

    // 창고 등록 V2
    /**
     * [추가] 사용자로부터 1개의 구역 정보를 입력받는 메서드
     */
    public WarehouseSection insertWarehouseSectionV2() throws IOException {
        System.out.println("\n============ [ 구역 등록 ] ============");
        String sectionName = getInput("구역 이름: ");
        int maxVol = getIntInput("최대 허용 부피: ");
        return WarehouseSection.builder()
                .sectionName(sectionName)
                .maxVol(maxVol)
                .build();
    }

    /**
     * [추가] 구역을 계속 추가할지 사용자에게 물어보는 메서드
     */
    public boolean askToAddAnother() throws IOException {
        String answer = getInput("구역을 계속 추가하시겠습니까? (y/n): ");
        return answer.equalsIgnoreCase("y");
    }

    /**
     * [추가] 컨트롤러가 사용할 창고 ID 입력 메서드
     */
    public int getWarehouseIdInput() throws IOException {
        return getIntInput("구역을 조회할 창고 ID를 입력하세요: ");
    }



    /**
     * 2. 창고 구역 수정
     * 수정에 필요한 정보를 관리자로부터 순서대로 입력 받음
     * @return 수정할 창고 구역의 ID 와 창고 ID, 새로운 정보가 담긴 WarehouseSection 객체
     */
//    public WarehouseSection updateWarehouseSection() throws IOException {
//        System.out.println("\n============ [ 구역 수정 ] ============");
//        int sectionID = getIntInput("구역 수정 ID: ");
//        int wid = getIntInput("구역 수정 창고 ID: ");
//        String sectionName = getInput("구역 수정 이름: ");
//        int maxVol = getIntInput("구역 수정 최대 허용 부피: ");
//        int currentVol = getIntInput("구역 수정 현재 적재 부피: ");
//        return WarehouseSection.builder()
//                .id(sectionID)
//                .warehouseID(wid)
//                .sectionName(sectionName)
//                .maxVol(maxVol)
//                .currentVol(currentVol)
//                .build();
//    }

    public WarehouseSection updateWarehouseSection() throws IOException {
        System.out.println("\n============ [ 구역 수정 ] ============");
        int sectionID = getIntInput("수정할 구역 ID: ");

        // int wid = getIntInput("구역 수정 창고 ID: ");

        String sectionName = getInput("새 구역 이름: ");
        int maxVol = getIntInput("새 최대 허용 부피: ");
        // int currentVol = getIntInput("새 현재 적재 부피: ");

        return WarehouseSection.builder()
                .id(sectionID)
                // [삭제] warehouseID 설정 부분을 삭제합니다.
                // .warehouseID(wid)
                .sectionName(sectionName)
                .maxVol(maxVol)
                // .currentVol(currentVol)
                .build();
    }

    /**
     * 3. 창고 구역 삭제
     * 창고 구역 ID 관리자로부터 입력 받아 삭제
     * @return 관리자가 입력한 삭제 창고 구역 ID
     */
    public int deleteWarehouseSection() throws IOException {
        System.out.println("\n============ [ 구역 삭제 ] ============");
        return getIntInput("삭제할 구역 ID: ");
    }


    /**
     * 4. 창고 구역 조회
     * 창고 구역 ID 관리자로부터 입력 받아 조회
     * @return 관리자가 입력한 조회 창고 구역 ID
     */
    public int searchWarehouseSection() throws IOException {
        System.out.println("\n============ [ 구역 조회 ] ============");
        return getIntInput("조회할 창고 ID: ");
    }

    public int searchWarehouseIDSection() throws IOException {
        System.out.println("\n============ [ 구역 조회 ] ============");
        return getIntInput("조회할 창고 ID: ");
    }


    // --- 창고 구역 관리 입출력 유틸 메서드 ---

    /**
     * 관리자에게 메시지를 보여주고 문자열 한 줄을 입력받아 반환
     * @param prompt 관리자에게 보여줄 메시지
     * @return 관리자가 입력한 문자열
     */
    public String getInput(String prompt) throws IOException {
        System.out.print("> " + prompt);
        return br.readLine();
    }

    /**
     * 관리자에게 메시지를 보여주고 정수를 입력받아 반환
     * 숫자가 아닌 값이 입력(NumberFormatException)되면 메시지 보여주고 재입력 요청
     * @param prompt 관리자에게 보여줄 메시지
     * @return 관리자가 입력한 정수
     */
    public int getIntInput(String prompt) throws IOException {
        while (true) {
            try {
                System.out.print("> " + prompt);
                return Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자로만 입력해주세요.");
            }
        }
    }

    /**
     * 처리 성공 메시지와 함께 WarehouseSection 객체 리스트 화면에 출력
     * @param message 성공 메시지
     * @param sectionList 출력할 WarehouseSection 객체 리스트
     */
    public void displaySuccess(String message, List<WarehouseSection> sectionList) {
        System.out.println("\n[처리 성공] " + message);
        if (sectionList == null || sectionList.isEmpty()) {
            System.out.println("> 조회된 구역 정보 없음");
        } else {
            System.out.println("> " + sectionList.size() + " 건의 구역 정보 조회");
            for (WarehouseSection section : sectionList) {
                System.out.println(section);
            }
        }
    }

    /**
     * 처리 성공 메시지와 함께 WarehouseSection 객체를 화면에 출력
     * @param message 성공 메시지
     * @param section 출력할 WarehouseSection 객체
     */
    public void displaySuccess(String message, WarehouseSection section) {
        System.out.println("\n[처리 성공] " + message);
        if (section != null) {
            System.out.println("> " + section);
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
     * 처리 실패 메시지
     * @param message 실패 메시지
     */
    public void displayError(String message) {
        System.out.println("\n[처리 실패] " + message);
    }
}