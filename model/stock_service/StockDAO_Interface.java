package model.stock_service;

import vo.Items.Item;
import vo.Stocks.Stock;

public interface StockDAO_Interface {

    public int insertStock(Stock stock, Item item);
    public int deleteStock(int stockID);


}
