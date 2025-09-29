package view.shipment_view;

import controller.shipment_controller.Shipment_Controller;
import vo.Shippments.Shipment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ShipmentAdminView {

   static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   static Shipment_Controller shipment_controller = new Shipment_Controller();

    public static void main(String[] args) {
        shipmentAdminMenu();
    }





    public static void shipmentAdminMenu()  {
        try {
        while (true) {
            System.out.println("""
                    ============== [출고 관리자 메뉴] ===============
                    1 출고 요청 승인 / 거절
                    2 출고 리스트 조회 (승인 대기)
                    3 출고 지시서 조회
                    4 출고 현황 조회 (모든 출고 현황)
                    0 이전 메뉴
                    ===========================================
                    [번호를 입력하세요]: """);

            int menu = Integer.parseInt(br.readLine());
            switch (menu) {
                case 1 -> updateShipment();
                case 2 -> selectPendingShipment();
                case 3 -> selectShipmentByID();
                case 4 -> selectCurrentShipment();
                case 0 -> {
                    return;
                }
                default -> System.out.println("잘못된 번호입니다.");
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










    // 1. 출고 요청 승인 / 거절
    public static void updateShipment() {
        try {
            System.out.print("출고 ID 입력: ");
            int shipmentID = Integer.parseInt(br.readLine());
            System.out.print("승인 / 거절 입력: ");
            String status = br.readLine();

            int waybill = 0;
            if (status.equalsIgnoreCase("승인")) {
                System.out.print("운송장 번호 입력: ");
                waybill = Integer.parseInt(br.readLine());
            }

            Shipment s = new Shipment();
            s.setShipmentID(shipmentID);
            s.setShippingProcess(status);
            s.setWaybillNumber(waybill);

            int result = shipment_controller.updateShipment(s);
            System.out.println(result > 0 ? (status.equalsIgnoreCase("승인") ? "출고 승인 완료" : "출고 거절 완료") : "처리 실패");
        } catch (Exception e) {
            System.err.println("처리 오류: " + e.getMessage());
        }
    }





    // 2. 출고 리스트 조회 (승인 대기 인것만)
    public static void selectPendingShipment() {
        try {
            List<Shipment> list = shipment_controller.selectPendingShipment();

            if (list.isEmpty()) {
                System.out.println("조회된 출고 내역이 없습니다.");
            } else {
                System.out.printf("%-10s %-10s %-10s %-15s %-10s %-10s%n", "출고ID", "유저ID", "아이템ID", "상품명", "수량", "상태");
                System.out.println("-------------------------------------------------------");

                for (Shipment s : list) {
                    System.out.printf("%-10d %-10d %-10d %-15s %-10d %-10s%n",
                            s.getShipmentID(),
                            s.getUserID(),
                            s.getItemID(),
                            s.getShipItemName(),         // Item VO 대신 shipItemName 필드 사용
                            s.getShipping_p_quantity(),
                            s.getShippingProcess());
                }
            }
        } catch (Exception e) {
            System.err.println("조회 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }




    // 승인 된것만 출고 지시서 조회(지시서)
    public static void selectShipmentByID() {
        try {
            System.out.print("출고 ID 입력: ");
            int shipmentID = Integer.parseInt(br.readLine());
            List<Shipment> list = shipment_controller.selectShipmentByID(shipmentID);

            if (list != null && !list.isEmpty()) {
                System.out.printf("%-10s %-10s %-10s %-15s %-10s %-10s %-10s%n",
                        "ShipmentID", "UserID", "ItemID", "ItemName", "ItemCount", "Status", "Waybill");

                for (Shipment s : list) {
                    System.out.printf("%-10d %-10d %-10d %-15s %-10d %-10s %-10d%n",
                            s.getShipmentID(), s.getUserID(), s.getItemID(),
                            s.getShipItemName(), s.getShipping_p_quantity(), s.getShippingProcess(),
                            s.getWaybillNumber());
                }
            } else {
                System.out.println("출고 정보 없음");
            }
        } catch (Exception e) {
            System.err.println("조회 오류: " + e.getMessage());
        }
    }





    // 관리자 출고 현황 조회 (모든 출고 현황)
    public static void selectCurrentShipment() {
        System.out.println("============== [관리자 출고 현황 조회] ===============");

        try {
            // DAO 호출
            List<Shipment> shipments = shipment_controller.selectCurrentShipment();

            if (shipments.isEmpty()) {
                System.out.println("등록된 출고 내역이 없습니다.");
                return;
            }

            // 출력 헤더
            System.out.printf("%-10s %-10s %-10s %-15s %-10s %-10s%n",
                    "출고ID", "유저ID", "아이템ID", "수량", "상태", "운송장번호");
            System.out.println("-------------------------------------------------------------");

            // 데이터 출력
            for (Shipment s : shipments) {
                System.out.printf("%-10d %-10d %-10d %-15d %-10s %-10d%n",
                        s.getShipmentID(),
                        s.getUserID(),
                        s.getItemID(),
                        s.getShipping_p_quantity(),
                        s.getShippingProcess(),
                        s.getWaybillNumber());
            }

            System.out.println("=============================================================");

        } catch (Exception e) {
            System.err.println("출고 현황 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }






}