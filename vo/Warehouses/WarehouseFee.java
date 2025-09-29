package vo.Warehouses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class WarehouseFee extends WarehouseChildVO {

    /**
     * 상속받는 필드
     * private int id;          // DB의 feeID (PK)와 매칭
     * private int warehouseID; // DB의 warehouseID (FK)와 매칭
     */

    // WarehouseFee 고유 필드
    private Date startDate;
    private Date endDate;
    private int price;

    public WarehouseFee() {

    }

    @Override
    public String toString() {
        return "============ [ 창고 요금 ] ============" + "\n" +
                "요금 ID : " + getId() + "\n" +
                "창고 ID : " + getWarehouseID() + "\n" +
                "창고 요금 : " + price + "원\n" +
                "창고 계약 시작일 : " + startDate + "\n" +
                "창고 계약 종료일 : " + endDate + "\n" +
                "====================================";
    }
}