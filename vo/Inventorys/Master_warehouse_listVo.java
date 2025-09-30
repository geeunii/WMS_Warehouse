package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Master_warehouse_listVo {
    private int warehouseID;
    private String warehouseName;
    private String warehouseCityName;
    private String warehouseStratus;
    private int currentVol;
    private int canvol;
}
