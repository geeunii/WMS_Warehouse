package controller.stock_controller;

import vo.Items.Item;
import vo.Stocks.Stock;

import java.util.List;

public interface Stock_Controller {

    public int updateStockProcess(int stockID, String stockProcess, int warehouseID, int sectionID);
    public List<Stock> stockRequestInformation();
    public List<Stock> stockMonthlyReport(int year, int month);
    public List<Stock> stockCurrentSearch(int uID);
}

