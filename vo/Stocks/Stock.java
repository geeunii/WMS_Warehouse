package vo.Stocks;

import lombok.*;
import vo.Items.Item;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    // Item VO 포함 (조합)
    private Item item;
}
