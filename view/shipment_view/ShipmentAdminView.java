package view.shipment_view;

import controller.shipment_controller.Shipment_Controller;

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
                    2 출고 리스트 조회
                    3 출고 지시서 조회
                    4 운송장 등록
                    0 이전 메뉴
                    ===========================================
                    [번호를 입력하세요]: """);

            int menu = Integer.parseInt(br.readLine());
            switch (menu) {
                case 1 -> updateShipmentStatus();
                case 2 -> shippingListSearch();
                case 3 -> shippingPrintSearch();
                case 4 -> waybillRegistration();
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
    public static void updateShipmentStatus() {
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
            s.setShipStatus(status);
            s.setWaybillNumber(waybill);

            int result = shipment_controller.updateShipment(s);
            System.out.println(result > 0 ? (status.equalsIgnoreCase("승인") ? "출고 승인 완료" : "출고 거절 완료") : "처리 실패");
        } catch (Exception e) {
            System.err.println("처리 오류: " + e.getMessage());
        }
    }

    // 2. 출고 리스트 조회
    public static void shippingListSearch() {
        try {
            System.out.print("창고 ID 입력: ");
            int warehouseID = Integer.parseInt(br.readLine());
            List<Shipment> list = shipment_controller.selectCurrentShipment(warehouseID);

            if (list.isEmpty()) System.out.println("조회된 출고 내역이 없습니다.");
            else {
                System.out.printf("%-10s %-10s %-10s %-15s %-10s %-10s%n", "출고ID", "유저ID", "아이템ID", "상품명", "수량", "상태");
                System.out.println("-------------------------------------------------------");
                for (Shipment s : list) {
                    System.out.printf("%-10d %-10d %-10d %-15s %-10d %-10s%n",
                            s.getShipmentID(), s.getUserID(), s.getItemID(),
                            s.getShipItemName(), s.getShipItemCount(), s.getShipStatus());
                }
            }
        } catch (Exception e) {
            System.err.println("조회 오류: " + e.getMessage());
        }
    }

    // 3. 출고 지시서 조회
    public static void shippingPrintSearch() {
        try {
            System.out.print("출고 ID 입력: ");
            int shipmentID = Integer.parseInt(br.readLine());
            Shipment s = shipment_controller.selectShipmentByID(shipmentID);
            if (s != null) {
                System.out.printf("%-10d %-10d %-10d %-15s %-10d %-10s %-10d%n",
                        s.getShipmentID(), s.getUserID(), s.getItemID(),
                        s.getShipItemName(), s.getShipItemCount(), s.getShipStatus(),
                        s.getWaybillNumber());
            } else System.out.println("출고 정보 없음");
        } catch (Exception e) {
            System.err.println("조회 오류: " + e.getMessage());
        }
    }

    // 4. 운송장 등록
    public static void waybillRegistration() {
        try {
            System.out.print("출고 ID 입력: ");
            int shipmentID = Integer.parseInt(br.readLine());
            System.out.print("운송장 번호 입력: ");
            int waybill = Integer.parseInt(br.readLine());

            Shipment s = new Shipment();
            s.setShipmentID(shipmentID);
            s.setWaybillNumber(waybill);

            int result = shipment_controller.registerWaybill(s);
            System.out.println(result > 0 ? "운송장 등록 완료" : "등록 실패");
        } catch (Exception e) {
            System.err.println("등록 오류: " + e.getMessage());
        }
    }

    // 5. 운송장 수정
    public static void waybillUpdate() {
        try {
            System.out.print("출고 ID 입력: ");
            int shipmentID = Integer.parseInt(br.readLine());
            System.out.print("새 운송장 번호 입력: ");
            int waybill = Integer.parseInt(br.readLine());

            Shipment s = new Shipment();
            s.setShipmentID(shipmentID);
            s.setWaybillNumber(waybill);

            int result = shipment_controller.updateWaybill(s);
            System.out.println(result > 0 ? "운송장 수정 완료" : "수정 실패");
        } catch (Exception e) {
            System.err.println("수정 오류: " + e.getMessage());
        }
    }

    // 6. 운송장 조회
    public static void waybillSearch() {
        try {
            System.out.print("출고 ID 입력: ");
            int shipmentID = Integer.parseInt(br.readLine());
            String waybill = shipment_controller.selectWaybillADMIN(shipmentID);
            System.out.println("운송장 번호: " + (waybill != null ? waybill : "등록되지 않음"));
        } catch (Exception e) {
            System.err.println("조회 오류: " + e.getMessage());
        }
    }
}