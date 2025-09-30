package view.inventory_view;

import vo.Inventorys.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CategorySpace_Choice_Menu {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String selectCategory() throws IOException {
        while (true) {
            System.out.println("""
                    ===========[ 카테고리별 재고 조회 ]===========
                    ============ [ 카테고리 선택 ] =============
                    
                          1. 침대             4. 의자
                          2. 소파             5. 장
                          3. 테이블/책상       6. 뒤로가기
                    
                    ===========================================
                    """);
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1: return "침대";
                case 2: return "소파";
                case 3: return "테이블/책상";
                case 4: return "의자";
                case 5: return "장";
                case 6: return null;
                default: System.out.println("잘못된 번호입니다.");
            }
        }
    }
        public void printCategorySearchResult_Master(List<Master_searchVo_ca> data) {
            if (data == null || data.isEmpty()) {
                System.out.println("해당 카테고리의 재고가 비어있습니다.");
                return;
            }

            String format = "| %-10s | %-8s | %-10s | %7s | %8s | %10s | %-7s | %-5s | %-7s |%n";
            System.out.println("===========================================================================================================================");
            System.out.printf(format, "입고날짜", "등록자 이름", "가구 이름", "수량", "단가", "금액 합계", "창고명", "창고 상태", "카테고리명");
            System.out.println("===========================================================================================================================");

            for (Master_searchVo_ca m : data) {
                System.out.printf(format, m.getStockdate(), m.getCustomername(), m.getItemname(), m.getQuantity(),
                        m.getItemprice(), m.getTotalprice(), m.getWarehousename(), m.getWarehousestate(), m.getCategory());
            }
            System.out.println("===========================================================================================================================");
            System.out.println(" ");
        }

    public void printCategorySearchResult_Admin(List<Admin_searchVo_ca> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("해당 카테고리의 재고가 비어있습니다.");
            return;
        }

        String format = "| %-10s | %-8s | %-10s | %7s | %8s | %10s | %-7s | %-7s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf(format, "입고날짜", "등록자 이름", "가구 이름", "수량", "단가", "금액 합계","보관 구역","카테고리명");
        System.out.println("===========================================================================================================================");

        for (Admin_searchVo_ca m : data) {
            System.out.printf(format, m.getStockdate(), m.getCustomername(), m.getItemname(), m.getQuantity(),
                    m.getItemprice(), m.getTotalprice(),m.getSectionname(), m.getCategory());
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }

    public void printCategorySearchResult_User(List<User_searchVo_ca> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("해당 카테고리의 재고가 비어있습니다.");
            return;
        }

        String format = "| %-10s | %7s | %10s | %-7s | %7s | %n";
        System.out.println("===========================================================================================================================");
        System.out.printf(format, "가구 이름", "가구 수량", "금액 합계", "창고 이름", "카테고리명");
        System.out.println("===========================================================================================================================");

        for (User_searchVo_ca uca : data) {
            System.out.printf(format,
                    uca.getItemname(), uca.getQuantity(), uca.getTotalprice(), uca.getWarehousename(), uca.getCategory()
            );
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");

    }


    public String selectSpace() throws IOException {
        while (true) {
            System.out.println("""
             ===========[ 공간별 재고 조회 ]===========
             ============ [ 공간 선택 ] =============
             
                   1. 거실            3. 주방
                   2. 침실            4. 홈/오피스
                   5. 뒤로가기
             
             =========================================
             """);
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 1: return "거실";
                case 2: return "침실";
                case 3: return "주방";
                case 4: return "홈/오피스";
                case 5: return null;
                default: System.out.println("잘못된 번호입니다.");
            }
        }

    }
    public void printSpaceSearchResult_Master(List<Master_searchVo_sp> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("해당 공간의 재고가 비어있습니다.");
            return;
        }

        String format = "| %-10s | %-7s | %-10s | %7s | %7s | %10s | %-7s | %-5s | %-7s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf(format, "입고날짜", "등록자 이름", "가구 이름", "수량", "단가", "금액 합계", "창고명", "창고 상태", "공간명");
        System.out.println("===========================================================================================================================");

        for (Master_searchVo_sp m : data) {
            System.out.printf(format, m.getStockdate(), m.getCustomername(), m.getItemname(), m.getQuantity(),
                    m.getItemprice(), m.getTotalprice(), m.getWarehousename(), m.getWarehousestate(), m.getSpacename());
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }

    public void printSpaceSearchResult_Admin(List<Admin_searchVo_sp> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("해당 공간의 재고가 비어있습니다.");
            return;
        }

        String format = "| %-12s | %-8s | %-15s | %10s | %8s | %12s | %-7s | %-10s |%n";
        System.out.println("===========================================================================================================================");
        System.out.printf(format, "입고날짜", "등록자 이름", "가구 이름", "수량", "단가", "금액 합계","보관 구역","공간명");
        System.out.println("===========================================================================================================================");

        for (Admin_searchVo_sp m : data) {
            System.out.printf(format, m.getStockdate(), m.getCustomername(), m.getItemname(), m.getQuantity(),
                    m.getItemprice(), m.getTotalprice(),m.getSectionname(), m.getSpacename());
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }

    public void printSpaceSearchResult_User(List<User_searchVo_sp> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("해당 카테고리의 재고가 비어있습니다.");
            return;
        }

        String format = "| %-10s | %7s | %10s | %-7s | %7s | %n";
        System.out.println("===========================================================================================================================");
        System.out.printf(format, "가구 이름", "가구 수량", "금액 합계", "창고 이름", "공간명");
        System.out.println("===========================================================================================================================");

        for (User_searchVo_sp usp : data) {
            System.out.printf(format,
                    usp.getItemname(), usp.getQuantity(), usp.getTotalprice(), usp.getWarehousename(), usp.getSpacename()
            );
        }
        System.out.println("===========================================================================================================================");
        System.out.println(" ");
    }
}
