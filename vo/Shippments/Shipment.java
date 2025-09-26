package vo.Shippments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class Shipment {

    // 출고 기본 정보
    private int userID;             // 유저 번호
    private int shipmentID;         // 출고 번호
    private int itemID;             // 아이템 아이디
    private int warehouseID;        // 창고 아이디
    private String shipItemName;    // 상품 이름
    private int shipping_p_quantity;      // 출고 수량
    private String shippingProcess;      // 출고 승인/거절 상태
    private String shippingDate;        // 출고 요청/처리 날짜
    private String shipLocation;    // 출고 위치 (창고 위치)

    // 운송장 관련
    private int waybillNumber;      // 운송장 번호
    private String waybillDate;     // 운송장 등록 날짜
}
