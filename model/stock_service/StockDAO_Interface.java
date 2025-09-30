package model.stock_service;

import vo.Items.Item;
import vo.Shippments.Shipment;
import vo.Stocks.Stock;

import java.util.List;

public interface StockDAO_Interface {

   public int updateStockProcess(int stockID, String stockingProcess, int warehouseID, int sectionID);
    public List<Stock> stockRequestInformation();
    public List<Stock> stockMonthlyReport(int year, int month);
    public List<Stock> stockCurrentSearch(int uID);
}
