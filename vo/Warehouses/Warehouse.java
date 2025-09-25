package vo.Warehouses;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // 상속 관계에서 부모 클래스의 필드까지 포함하여 두 객체를 비교
@SuperBuilder
@ToString(callSuper = true)
// VO (Value Object) / DTO (Data Transfer Object)
// 창고 건물 클래스
public class Warehouse extends WarehouseBaseVO {

    /**
     * 상속받는 필드
     * private int id; // DB의 warehouseID (PK)와 매칭
     */

    // Warehouse 고유 필드
    private String warehouseName;
    private String warehouseAddress;
    private String warehouseStatus;
    private String warehouseCityName;
    private int maxCapacity;
    private Date regDate;
    private int warehouseArea;
    private int floorHeight;
    private int mid; // 관리자 ID (FK)

}