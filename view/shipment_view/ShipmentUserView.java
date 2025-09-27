package view.shipment_view;

import controller.shipment_controller.Shipment_Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ShipmentUserView {
    private final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final static Shipment_Controller shipment_controller = new Shipment_Controller();

    public static void main(String[] args) {
        shipmentUserMenu();


    }


    public static void shipmentUserMenu() {
        // 출고 메뉴
        while (true) {
            System.out.println();
            System.out.print("""
                    ============== [출고 메뉴] ===============

                    1 출고요청
                    2 출고 지시서 조회
                    3 출고 상품 검색
                    0. 이전 메뉴로

                    ===========================================
                    [번호를 입력하세요]: """);
            try {
                int choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1 -> shippingRequest();
                    case 2 -> shippingPrintSearch();
                    case 3 -> waybillSearch();
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
    public static void shippingRequest() {
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
            shipment.setShipItemCount(shipCount); // 입력받은 출고 수량
            shipment.setShipStatus("대기");       // 승인 전 상태

            // 3️⃣ DAO 호출해서 출고 생성
            int result = shipment_controller.createShipment(shipment);

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





    // 출고 지시서 조회
    public static void shippingPrintSearch() {
        try {
            System.out.println("============== [출고 지시서 조회] ===============");
            System.out.print("창고 ID 입력: ");
            int warehouseID = Integer.parseInt(br.readLine());
            List<Shipment> shipments = shipment_controller.selectCurrentShipment(warehouseID);

            if (shipments.isEmpty()) {
                System.out.println("조회된 출고 지시서가 없습니다.");
            } else {
                System.out.printf("%-10s %-10s %-10s %-15s %-10s %-10s%n",
                        "출고ID", "유저ID", "아이템ID", "상품이름", "수량", "상태");
                System.out.println("-------------------------------------------------");
                for (Shipment s : shipments) {
                    System.out.printf("%-10d %-10d %-10d %-15s %-10d %-10s%n",
                            s.getShipmentID(), s.getUserID(), s.getItemID(),
                            s.getShipItemName(), s.getShipItemCount(), s.getShipStatus());
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("오류: 올바른 숫자를 입력해주세요.");
        }
    }


    // 출고 상품 검색
    public static void shippingProductSearch() {
        try {
            System.out.println("============== [출고 상품 검색] ===============");
            System.out.print("검색할 상품 이름 입력: ");
            String keyword = br.readLine();
            List<Shipment> shipments = shipment_controller.searchShipmentByProduct(keyword);

            if (shipments.isEmpty()) {
                System.out.println("검색된 출고 상품이 없습니다.");
            } else {
                System.out.printf("%-10s %-10s %-10s %-15s %-10s %-10s%n",
                        "출고ID", "유저ID", "아이템ID", "상품이름", "수량", "상태");
                System.out.println("-------------------------------------------------");
                for (Shipment s : shipments) {
                    System.out.printf("%-10d %-10d %-10d %-15s %-10d %-10s%n",
                            s.getShipmentID(), s.getUserID(), s.getItemID(),
                            s.getShipItemName(), s.getShipItemCount(), s.getShipStatus());
                }
            }
        } catch (IOException e) {
            System.err.println("오류: 입력 처리 중 문제가 발생했습니다.");
        }
    }


    // 운송장 조회
    public static void waybillSearch() {
        try {
            System.out.println("============== [운송장 조회] ===============");
            System.out.print("출고ID 입력: ");
            int shipmentID = Integer.parseInt(br.readLine());
            System.out.print("사용자 ID 입력: ");
            int userID = Integer.parseInt(br.readLine());

            String waybill = shipment_controller.selectWaybillUSER(shipmentID, userID);
            System.out.println(waybill == null ? "조회된 운송장이 없습니다." : "운송장 번호: " + waybill);
        } catch (IOException | NumberFormatException e) {
            System.err.println("오류: 올바른 숫자를 입력해주세요.");
        }
    }
}

