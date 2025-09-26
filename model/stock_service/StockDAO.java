package model.stock_service;


import util.DBUtil;
import vo.Items.Item;
import vo.Stocks.Stock;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class StockDAO implements StockDAO_Interface {

    Connection con = DBUtil.getConnection();

    @Override
    public int insertStock(Stock stock, Item item) {
        String sql = "{call insertStockandItem(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)}";

        try(CallableStatement cal = con.prepareCall(sql)) {

            cal.setString(1, item.getItemName());
            cal.setInt(2, item.getItemPrice());
            cal.setInt(3, item.getWeight());
            cal.setString(4, item.getAssemble());
            cal.setString(5, item.getCustomerName());
            cal.setFloat(6, item.getWidth());
            cal.setFloat(7, item.getHeight());
            cal.setFloat(8, item.getLevelHeight());
            cal.setString(9, item.getSpaceName());
            cal.setString(10, item.getCategory());

            cal.setDate(11, (java.sql.Date) new Date());
            cal.setString(12, stock.getStockingProcess());
            cal.setInt(13, stock.getStock_p_quantity());
            cal.setInt(14, stock.getWarehouseID());
            cal.setInt(15, stock.getStockID());
            cal.setInt(16, stock.getUID());


            int row = cal.executeUpdate();

            if (row > 0) {
                return 1;
            }else{
                return 0;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteStock(int stockID) {
        return 0;
    }
}
