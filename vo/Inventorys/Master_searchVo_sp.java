package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

// Master 관리자가 공간 별 재고 조회시 나오는 목록
public class Master_searchVo_sp extends Master_searchVo{
    private String spacename;
}
