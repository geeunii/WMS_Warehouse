package controller.stock_controller;

import model.stock_service.StockDAO;
import vo.Items.Item;
import vo.Stocks.Stock;

import java.util.List;

public class Stock_Controller_Impl implements Stock_Controller{

    StockDAO stockDAO = new StockDAO();

    @Override
    public int updateStockProcess(int stockID, String stockProcess, int warehouseID, int sectionID) {
        return stockDAO.updateStockProcess(stockID, stockProcess, warehouseID, sectionID);
    }


    @Override
    public List<Stock> stockRequestInformation() {
        return stockDAO.stockRequestInformation();
    }


    @Override
    public List<Stock> stockMonthlyReport(int year, int month) {
        return stockDAO.stockMonthlyReport(year, month);
    }

    @Override
    public List<Stock> stockCurrentSearch(int uID) {
        return stockDAO.stockCurrentSearch(uID);
    }



}
