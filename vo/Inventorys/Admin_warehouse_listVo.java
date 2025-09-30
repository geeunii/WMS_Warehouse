package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Admin_warehouse_listVo {
    private String warehouseName;
    private String warehouseStatus;
    private String sectionName;
    private int currentVol;
    private int canvol;
}
