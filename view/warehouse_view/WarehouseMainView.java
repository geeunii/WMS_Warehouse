package view.warehouse_view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WarehouseMainView {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public int warehouseManagerMainMenu() throws IOException {
        System.out.println("""
                ===========[ 창고 통합 관리 ] ==========
                ============== [ 메뉴 ] ==============
                
                    1. 창고 관리
                    2. 창고 구역 관리
                    3. 뒤로 가기
                
                ======================================
                ======================================
                """);

//        System.out.println("\n===========[ 창고 통합 관리 ] ===========");
//        System.out.println("============ [ 메뉴 선택 ] ============");
//        System.out.println("\n   1. 창고 관리");
//        System.out.println("   2. 창고 요금 관리");
//        System.out.println("   3. 창고 구역 관리");
//        System.out.println("   4. 뒤로 가기\n");
//        System.out.println("======================================");
//        System.out.println("======================================");

        return getIntInput("메뉴를 선택하세요: ");
    }

    /**
     * 헬퍼 메서드
     * @param prompt 화면에 보여 줄 안내 메시지
     * @return 사용자가 입력한 정수 값
     * @throws IOException 입력을 받는 도중 오류가 발생한 경우
     */
    private int getIntInput(String prompt) throws IOException { // 나를 호출한 상위 메서드에게 처리를 위임
        while (true) {
            try {
                System.out.print("> " + prompt);
                return Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) { // 내 메서드가 처리할 수 있는 예외
                System.out.println("숫자로만 입력해주세요.");
            }
        }
    }
}