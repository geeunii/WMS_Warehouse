package view.shipment_view;

import controller.shipment_controller.Shipment_Controller;
import controller.shipment_controller.Shipment_User_Controller;
import vo.Shippments.Shipment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ShipmentUserView {
    private final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final static Shipment_User_Controller user_controller = new Shipment_User_Controller();


    public void shipmentUserMenu() {
        // 출고 메뉴
        while (true) {
            System.out.println();
            System.out.print("""
                    ============== [출고 메뉴] ===============

                    1 출고요청
                    2 출고현황 조회
                    3 출고 상품검색
                    0. 이전 메뉴로

                    ===========================================
                    [번호를 입력하세요]: """);
            try {
                int choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1 -> shippingRequest();
                    case 2 -> selectCurrentShipmentUser();
                    case 3 -> shippingProductSearch();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println(" 잘못된 번호입니다. 다시 입력해주세요.");
                }
            } catch (IOException e) {
                System.out.println(" 입출력 오류가 발생했습니다: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(" 숫자를 입력해 주세요.");
            }
        }
    }


    // 출고 요청
    public void shippingRequest() {
        try {
            System.out.println("============== [출고 요청] ===============");

            // 사용자 입력
            System.out.print("아이템 ID 입력: ");
            int itemID = Integer.parseInt(br.readLine());

            System.out.print("유저 ID 입력: ");
            int uid = Integer.parseInt(br.readLine());

            System.out.print("출고 수량 입력: ");
            int shipCount = Integer.parseInt(br.readLine());


            // 2️⃣ Shipment 객체 생성 (입력값 사용!)
            Shipment shipment = new Shipment();
            shipment.setItemID(itemID);           // 입력받은 아이템 ID
            shipment.setUserID(uid);
            shipment.setShipping_p_quantity(shipCount); // 입력받은 출고 수량
            shipment.setShippingProcess("승인 대기");       // 승인 전 상태

            // 3️⃣ DAO 호출해서 출고 생성
            int result = user_controller.createShipment(shipment);

            if (result > 0) {
                System.out.println("출고 요청이 정상적으로 등록되었습니다");
            } else {
                System.out.println("출고 요청 등록 실패");
            }

        } catch (IOException e) {
            System.err.println("입출력 오류 발생: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("숫자를 올바르게 입력해 주세요.");
        }
    }




    // 2 출고 현황 조회 (승인대기, 승인, 거절)
    // 아이디 기준으로 검색
// 유저 출고 현황 조회 화면
    public void selectCurrentShipmentUser() {
        System.out.println("========== [내 출고 현황 조회] ==========");

        try {
            // DAO 호출
            // 유저 ID 입력
            System.out.print("유저 ID 입력: ");
            int userID = Integer.parseInt(br.readLine());
            List<Shipment> list = user_controller.selectCurrentShipmentUser(userID);

            if (list.isEmpty()) {
                System.out.println("출고 내역이 없습니다.");
                return;
            }

            // 출력 헤더
            System.out.printf("%-10s %-10s %-10s %-20s %-10s %-10s %-15s %-15s%n",
                    "출고ID", "유저ID", "아이템ID", "아이템이름", "수량", "상태", "운송장번호", "출고일");
            System.out.println("---------------------------------------------------------------------------------------------");

            // 데이터 출력
            for (Shipment s : list) {
                System.out.printf("%-10d %-10d %-10d %-20s %-10d %-10s %-15s %-15s%n",
                        s.getShipmentID(),
                        s.getUserID(),
                        s.getItemID(),
                        (s.getShipItemName() == null ? "-" : s.getShipItemName()),
                        s.getShipping_p_quantity(),
                        s.getShippingProcess(),
                        (s.getWaybillNumber() == null || s.getWaybillNumber().isEmpty() ? "-" : s.getWaybillNumber()),
                        (s.getShippingDate() == null ? "-" : s.getShippingDate())
                );
            }

            System.out.println("============================================");
        } catch (Exception e) {
            System.err.println("출고 현황 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }













    //  출고 상품검색 (상품 아이디로 입력 받기)
    public void shippingProductSearch() {
        try {
            System.out.print("조회할 상품 ID 입력: ");
            int itemID = Integer.parseInt(br.readLine());

            List<Shipment> list = user_controller.shippingProductSearch(itemID);

            if (list.isEmpty()) {
                System.out.println("조회된 출고 내역이 없습니다.");
                return;
            }

            // 출력 헤더
            System.out.printf("%-10s %-20s %-10s %-10s %-10s %-10s%n",
                    "출고ID", "상품명", "출고수량", "승인여부", "운송장", "아이템ID");
            System.out.println("-----------------------------------------------------------");

            // 데이터 출력
            for (Shipment s : list) {
                System.out.printf("%-10d %-20s %-10d %-10s %-10s %-10d%n",
                        s.getShipmentID(),
                        (s.getShipItemName() == null ? "-" : s.getShipItemName()),
                        s.getShipping_p_quantity(),
                        s.getShippingProcess(),
                        (s.getWaybillNumber() == null || s.getWaybillNumber().isEmpty() ? "-" : s.getWaybillNumber()),
                        s.getItemID()
                );
            }

        } catch (Exception e) {
            System.err.println("상품별 출고 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

