package vo.Warehouses;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 창고 시스템의 모든 데이터 객체를 위한 최상위 추상 클래스
 * 시스템의 모든 객체(창고, 구역, 요금 등)가 공통적으로 가지는 고유 ID(id)를 정의
 */
@Data
@NoArgsConstructor
@SuperBuilder
public abstract class WarehouseBaseVO {
    private int id; // 고유 식별 번호

    public WarehouseBaseVO(int id) {
        this.id = id;
    }
}
