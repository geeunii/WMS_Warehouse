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

    // 창고 정보 출력
    @Override
    public String toString() {
        return "============ [ 창고 정보 ] ============" + "\n" +
                "창고 ID : " + getId() + "\n" +
                "창고 이름 : " + warehouseName + "\n" +
                "창고 주소 : " + warehouseAddress + "\n" +
                "창고 상태 : " + warehouseStatus + "\n" +
                "창고 소재지 : " + warehouseCityName + "\n" +
                "창고 최대 수용량 : " + maxCapacity + "\n" +
                "창고 등록일 : " + regDate + "\n" +
                "창고 면적 : " + warehouseArea + "\n" +
                "층고 : " + floorHeight + "\n" +
                "창고 관리자 ID : " + mid + "\n" +
                "====================================";
    }
}