package view.warehouse_view;

import java.util.Scanner;

public class WarehouseMainView {
    private final Scanner sc = new Scanner(System.in);

    public int warehouseManagerMainMenu() {
        System.out.println("\n[본사 관리자 > 창고 통합 관리]");
        System.out.println("1. 창고 관리");
        System.out.println("2. 창고 요금 관리");
        System.out.println("3. 창고 구역 관리");
        System.out.println("4. 프로그램 종료");
        System.out.print("> 메뉴 선택: ");
        return Integer.parseInt(sc.nextLine());
    }



}
