package vo.Stocks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class Stock {
    private int stockID;
    private Date stockingDate;
    private String stockingProcess;
    private int stock_p_quantity;
    private int itemID;
    private int warehouseID;
    private int sectionID;
    private int uID;
}
