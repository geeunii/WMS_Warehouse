package view.warehouse_view;

import java.util.Scanner;

public class WarehouseMainView {
    private final Scanner sc = new Scanner(System.in);

    public int warehouseManagerMainMenu() {
        System.out.println("""
                ===========[ 창고 통합 관리 ] ==========
                ============ [ 메뉴 선택 ] ============
                
                   1. 창고 관리
                   2. 창고 요금 관리
                   3. 창고 구역 관리
                   4. 뒤로 가기
                
                ======================================
                ======================================
                """);
        return getIntInput("메뉴를 선택하세요: ");


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


}
