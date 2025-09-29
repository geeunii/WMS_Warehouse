package view.stock_view;

import controller.stock_controller.Stock_Controller_Impl;
import controller.stock_controller.Stock_User_Controller;
import model.stock_service.StockDAO;
import vo.Stocks.Stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class StockAdminView {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final Stock_Controller_Impl sc = new Stock_Controller_Impl(); // 컨트롤러
    private static final Stock_User_Controller su = new Stock_User_Controller();




    // 입고 메뉴
    public void stockAdminMenu() throws Exception {
        while (true) {
            System.out.println();
            System.out.print("""
                    ============== [입고 메뉴] ===============
                    
                    1. 입고 조회 (승인대기)         2. 입고 승인/취소
                    3. 월별 입고현황               4. 입고 현황 조회
                    0. 이전 메뉴로
                    
                    ===========================================
                    [입고 메뉴를 선택해주세요 : ]: """);

            int menu = Integer.parseInt(br.readLine());

            switch (menu) {
                case 1 -> stockRequestInformation();
                case 2 -> updateStockProcess();
                case 3 -> stockMonthlyReport();
                case 4 -> stockCurrentSearch();
                case 0 -> {
                    System.out.println("이전 메뉴로 돌아갑니다.");
                    return;
                }
                default -> System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
            }
        }
    }


    public void updateStockProcess() {
        try {
            System.out.println("============== [입고 승인 / 취소] ===============");
            System.out.print("입고 번호(stockID) 입력: ");
            int stockID = Integer.parseInt(br.readLine());

            System.out.print("승인 / 거절 입력: ");
            String stockingProcess = br.readLine();

            int warehouseID = 0;
            int sectionID = 0;

            if (stockingProcess.equalsIgnoreCase("승인")) {
                System.out.print("창고 ID 입력: ");
                warehouseID = Integer.parseInt(br.readLine());
                System.out.print("구역 ID 입력: ");
                sectionID = Integer.parseInt(br.readLine());
            }

            int result = sc.updateStockProcess(stockID, stockingProcess, warehouseID, sectionID);
            System.out.println(result > 0 ? "처리 완료" : "처리 실패");

        } catch (Exception e) {
            System.err.println("입고 승인/취소 처리 중 오류: " + e.getMessage());
        }
    }


    public void stockRequestInformation() {
        try {
            System.out.println("============== [입고 요청 현황 조회] ===============");

            List<Stock> stocks = sc.stockRequestInformation(); // DAO에서 승인대기만 가져옴

            if (stocks.isEmpty()) {
                System.out.println("현재 승인대기 상태의 입고 요청이 없습니다.");
                return;
            }

            System.out.printf("%-10s %-10s %-15s %-10s %-15s%n",
                    "입고번호", "아이템ID", "상품명", "수량", "입고 상태");
            System.out.println("------------------------------------------------------------");

            for (Stock stock : stocks) {
                System.out.printf("%-10d %-10d %-15s %-10d %-15s%n",
                        stock.getStockID(),
                        stock.getItemID(),
                        stock.getItem().getItemName(),
                        stock.getStock_p_quantity(),
                        stock.getStockingProcess());
            }

            System.out.println("==========================================================");

        } catch (Exception e) {
            System.err.println("입고 요청 현황 조회 중 오류: " + e.getMessage());
        }
    }


    public void stockMonthlyReport() {
        try {
            System.out.println("============== [월별 입고현황 조회] ===============");
            System.out.print("조회할 연도 입력: ");
            int year = Integer.parseInt(br.readLine());
            System.out.print("조회할 월 입력: ");
            int month = Integer.parseInt(br.readLine());

            List<Stock> stocks = sc.stockMonthlyReport(year, month);

            if (stocks.isEmpty()) {
                System.out.println("해당 월에 입고된 재고가 없습니다.");
                return;
            }

            System.out.printf("%-10s %-10s %-15s %-10s %-15s%n",
                    "입고번호", "아이템ID", "상품명", "수량", "입고 상태");
            System.out.println("------------------------------------------------------------");

            for (Stock stock : stocks) {
                System.out.printf("%-10d %-10d %-15s %-10d %-15s%n",
                        stock.getStockID(),
                        stock.getItemID(),
                        stock.getItem().getItemName(),
                        stock.getStock_p_quantity(),
                        stock.getStockingProcess());
            }

            System.out.println("==========================================================");

        } catch (Exception e) {
            System.err.println("월별 입고현황 조회 중 오류: " + e.getMessage());
        }
    }


    // 4. 입고 현황 조회
    public void stockCurrentSearch() {
        System.out.println("============== [입고 현황 조회] ===============");
        try {
            System.out.print("유저번호를 입력해주세요 : ");
            int uID = Integer.parseInt(br.readLine());
            List<Stock> stocks = sc.stockCurrentSearch(uID); // DAO 호출

            if (stocks.isEmpty()) {
                System.out.println("창고에는 입고된 재고가 없습니다.");
                return;
            }

            System.out.printf("%-10s %-10s %-10s %-15s %-15s%n",
                    "입고번호", "아이템ID", "수량", "상태", "입고일");
            System.out.println("------------------------------------------------------------");

            for (Stock stock : stocks) {
                System.out.printf("%-10d %-10d  %-10d %-15s %-15s%n",
                        stock.getStockID(),
                        stock.getItemID(),
                        stock.getStock_p_quantity(),
                        stock.getStockingProcess(),
                        stock.getStockingDate()); // Stock 테이블 컬럼 사용
            }

            System.out.println("==========================================================");

        } catch (Exception e) {
            System.err.println("입고 현황 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }
}




