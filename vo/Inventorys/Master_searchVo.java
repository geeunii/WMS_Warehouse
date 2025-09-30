package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

// Master 관리자가 재고 조회 시 나오는 목록
public class Master_searchVo extends searchVo{
    private String warehousename;
    private String warehousestate;
}
