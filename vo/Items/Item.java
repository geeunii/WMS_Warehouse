package vo.Items;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data


public class Item {
    private int itemID;
    private String itemName;
    private int itemPrice;
    private int weight;
    private String assemble;
    private String customerName ;
    private String material;
    private int volume;
    private float width;
    private float height;
    private float levelHeight;
    private String spaceName;
    private String category;
}
