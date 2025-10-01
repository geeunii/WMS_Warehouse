package vo.Warehouses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
// 창고 내부 구역
public class WarehouseSection extends WarehouseChildVO {

    /**
     * 상속받는 필드
     * private int id;
     * private int warehouseID;
     */

    // WarehouseSection 고유 필드
    private String sectionName; // 구역 이름
    private int maxVol; // 최대 허용 부피
    private int currentVol; // 현재 적재 부피

//    public WarehouseSection() {
//
//    }

    @Override
    public String toString() {
        return "============ [ 창고 구역 ] ============" + "\n" +
                "구역 ID : " + getId() + "\n" +
                "창고 ID : " + getWarehouseID() + "\n" +
                "구역 이름 : " + sectionName + "\n" +
                "최대 허용 부피 : " + maxVol + "\n" +
                "현재 적재 부피 : " + currentVol + "\n" +
                "====================================";
    }
}
