### 입출고 프로시저 작성 ###

-- 입고

create procedure insertStockandItem(
    in item_name varchar(100),
    in item_price int,
    in p_weight int,
    in p_assemble varchar(50),
    in p_customerName varchar(50),
    in p_width decimal(10, 2),
    in p_height decimal(10, 2),
    in p_levelheight decimal(10, 2),
    in p_spaceName varchar(50),
    in p_category varchar(50),
    in s_Date DATETIME,
    in s_Process varchar(50),
    in s_Quntity int,
    in p_warehouse_id int,
    in p_section_id int,
    in p_uID int)
begin
    insert into Item(itemName, itemPrice, weight, assemble, customerName, material, width, height, levelHeight,
                     spaceName, category)
        value (item_name, item_price, p_weight, p_assemble, p_customerName, p_width,
               p_height, p_levelheight, p_spaceName, p_category)

    set @new_itemID = LAST_INSERT_ID();

    insert into Stock(stockingDate, stockingProcess, stock_p_quantity, itemID, warehouseID, sectionID, uID)
        value (s_Date, s_Process, s_Quntity, @new_itemID, p_warehouse_id, p_section_id, p_uID)
end;