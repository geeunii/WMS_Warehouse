package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin_correspond_listVo {
    private String customerName;
    private int itemID;
    private int quantity;
    private int totalprice;
//    private int 섹터점유율;
}
