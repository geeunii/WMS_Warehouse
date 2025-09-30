package vo.Inventorys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User_searchVo {

    private String itemname;
    private String quantity;
    private String totalprice;
    private String warehousename;
}
