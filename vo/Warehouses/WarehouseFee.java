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
}