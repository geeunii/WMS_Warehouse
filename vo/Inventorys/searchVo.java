package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

// 조회 했을때 공통으로 나오는 목록
public class searchVo {

    private Date stockdate;
    private String customername;
    private String itemname;
    private int quantity;
    private int itemprice;
    private int totalprice;

}
