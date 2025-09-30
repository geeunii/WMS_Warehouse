package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data


public class Inventory {
    private int eID;
    private int quantity;
    private int warehouseID;
    private int sectionID;
    private int itemID;
    private Date stockDate;
    private Date shipDate;
}
