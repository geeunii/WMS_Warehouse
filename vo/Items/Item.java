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
    private assemble assembleType;
    private String brandName;
    private String material;
    private int volume;
    private int width;
    private int height;
    private int levelHeight;
    private String spaceName;
    private String category;
}

enum assemble{
    Module,not_Module
}

