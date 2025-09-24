package view.warehouse_view; // 또는 view.section_view

import vo.Warehouses.WarehouseSection;
import java.util.List;
import java.util.Scanner;

public class WarehouseSectionAdminView {

    private final Scanner sc = new Scanner(System.in);

    // 창고 구역 메뉴
    public int warehouseSectionMainMenu() {
        System.out.println("\n[본사 관리자 > 창고 구역 관리]");
        System.out.println("1. 창고 구역 등록");
        System.out.println("2. 창고 구역 수정");
        System.out.println("3. 창고 구역 삭제");
        System.out.println("4. 창고 구역 조회 (ID)");
        System.out.println("5. 뒤로 가기");
        return getIntInput("메뉴를 선택하세요: ");
    }

    // 창고 구역 등록
    public WarehouseSection insertWarehouseSection() {
        System.out.println("\n[창고 구역 등록]");
        String sectionName = getInput("구역 이름 (예: A구역): ");
        int maxVol = getIntInput("최대 허용 부피: ");
        int currentVol = getIntInput("현재 적재 부피: ");
        int wid = getIntInput("소속될 창고 ID: ");
        return WarehouseSection.builder()
                .sectionName(sectionName)
                .maxVol(maxVol)
                .currentVol(currentVol)
                .warehouseID(wid)
                .build();
    }

    // 창고 구역 수정
    public WarehouseSection updateWarehouseSection() {
        System.out.println("\n[창고 구역 수정]");
        int sectionID = getIntInput("수정할 구역 ID: ");
        String sectionName = getInput("수정 구역 이름: ");
        int maxVol = getIntInput("수정 최대 허용 부피: ");
        int currentVol = getIntInput("수정 현재 적재 부피: ");
        int wid = getIntInput("수정 소속 창고 ID: ");
        return WarehouseSection.builder()
                .id(sectionID)
                .sectionName(sectionName)
                .maxVol(maxVol)
                .currentVol(currentVol)
                .warehouseID(wid)
                .build();
    }

    // 구역 삭제
    public int deleteWarehouseSection() {
        System.out.println("\n[창고 구역 삭제]");
        return getIntInput("삭제할 구역 ID: ");
    }

    // 구역 조회
    public int searchWarehouseSection() {
        System.out.println("\n[창고 구역 조회]");
        return getIntInput("조회할 창고 구역 ID: ");
    }


    /// /////////// 기능


    public String getInput(String prompt) {
        System.out.print("> " + prompt);
        return sc.nextLine();
    }

    public int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print("> " + prompt);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자로만 입력해주세요.");
            }
        }
    }

    public void displaySuccess(String message, List<WarehouseSection> sectionList) {
        System.out.println("\n[처리 성공] " + message);
        if (sectionList == null || sectionList.isEmpty()) {
            System.out.println("> 조회된 구역 정보가 없습니다.");
        } else {
            System.out.println("> 총 " + sectionList.size() + " 건의 구역 정보가 조회되었습니다.");
            for (WarehouseSection section : sectionList) {
                System.out.println(section);
            }
        }
    }

    public void displaySuccess(String message, WarehouseSection section) {
        System.out.println("\n[처리 성공] " + message);
        if (section != null) {
            System.out.println("> " + section);
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String message) {
        System.out.println("\n[처리 실패] " + message);
    }
}