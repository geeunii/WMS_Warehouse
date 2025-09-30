package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Master_correspond_listVo {
    private int warehouseID;
    private String warehouseName;
    private String customerName;
    private int itemID;
    private int quantity;
    private int totalprice;
//    private int 점유율;

}
