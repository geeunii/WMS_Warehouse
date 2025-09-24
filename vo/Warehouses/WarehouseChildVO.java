package vo.Warehouses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * WarehouseBaseVO를 상속받아 id를 가지면서,
 * 추가로 특정 창고에 소속된다는 공통적인 특징을 표현하기 위해 warehouseID를 정의
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public abstract class WarehouseChildVO extends WarehouseBaseVO{

    private int warehouseID; // 창고 번호

    public WarehouseChildVO(int id, int warehouseID) {
        super(id);
        this.warehouseID = warehouseID;
    }
}
