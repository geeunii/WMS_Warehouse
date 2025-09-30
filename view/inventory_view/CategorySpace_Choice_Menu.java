package view.inventory_view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CategorySpace_Choice_Menu {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void category_choice_Menu() throws IOException {
        while (true) {
            System.out.println("""
             ===========[ 카테고리별 재고 조회 ]===========
             ============ [ 카테고리 선택 ] =============
             
                   1. 침대             4. 의자
                   2. 소파             5. 장
                   3. 테이블/책상       6. 뒤로가기
             """);
            int choice = Integer.parseInt(br.readLine());

            switch (choice) {
                case 6 -> {return;}
            }
        }

    }

    public void Space_choice_Menu() throws IOException {
        while (true) {
            System.out.println("""
             ===========[ 공간별 재고 조회 ]===========
             ============ [ 공간 선택 ] =============
             
                   1. 거실            3. 주방
                   2. 침실            4. 홈/오피스
                   5. 뒤로가기
             """);
            int choice = Integer.parseInt(br.readLine());
            switch (choice) {
                case 5 -> {return;}
            }
        }

    }
}
