package model.stock_service;


import util.DBUtil;
import vo.Items.Item;
import vo.Stocks.Stock;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockDAO implements StockDAO_Interface {

    Connection conn = DBUtil.getConnection();


    // 입고 승인 / 거절
    @Override
    public int updateStockProcess(int stockID, String stockProcess, int warehouseID, int sectionID) {
        int result = 0;
        String sql = "{CALL sp_updateStockProcess(?, ?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, stockID);
            cs.setString(2, stockProcess);
            cs.setInt(3, warehouseID);
            cs.setInt(4, sectionID);

            result = cs.executeUpdate();
        } catch (SQLException e) {
            System.err.println("입고 승인/거절 처리 실패: " + e.getMessage());
        }
        return result;
    }





    // 입고 조회 (승인 전)
    @Override
    // 승인 대기 중인 입고 요청 조회
    public List<Stock> stockRequestInformation() {
        List<Stock> list = new ArrayList<>();
        String sql = "{CALL sp_stockRequestInformation()}";

        try (CallableStatement cal = conn.prepareCall(sql);
             ResultSet rs = cal.executeQuery()) {

            while (rs.next()) {
                Stock stock = new Stock();
                stock.setStockID(rs.getInt("stockID"));
                stock.setStockingDate(rs.getDate("stockingDate"));
                stock.setStockingProcess(rs.getString("stockingProcess"));
                stock.setStock_p_quantity(rs.getInt("stock_p_quantity"));
                stock.setItemID(rs.getInt("itemID"));
                stock.setWarehouseID(rs.getInt("warehouseID"));
                stock.setSectionID(rs.getInt("sectionID"));
                stock.setUID(rs.getInt("uID"));

                // Item 객체 생성해서 Stock 안에 넣기
                Item item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setItemName(rs.getString("itemName"));
                item.setItemPrice(rs.getInt("itemPrice"));
                item.setWeight(rs.getInt("weight"));
                item.setAssemble(rs.getString("assemble"));
                item.setCustomerName(rs.getString("customerName"));
                item.setMaterial(rs.getString("material"));
                item.setVolume(rs.getInt("volume"));
                item.setWidth(rs.getFloat("width"));
                item.setHeight(rs.getFloat("height"));
                item.setLevelHeight(rs.getFloat("levelHeight"));
                item.setSpaceName(rs.getString("spaceName"));
                item.setCategory(rs.getString("category"));

                stock.setItem(item); // Stock에 Item 주입

                list.add(stock);
            }

        } catch (SQLException e) {
            System.err.println("승인 대기 입고 요청 조회 실패: " + e.getMessage());
        }

        return list;
    }




    // 월별 입고 현황 조회
    @Override
    public List<Stock> stockMonthlyReport(int year, int month) {
        List<Stock> list = new ArrayList<>();
        String sql = "{CALL sp_stockMonthlyReport(?, ?)}";

        try(CallableStatement cal = conn.prepareCall(sql)) {
            cal.setInt(1, year);
            cal.setInt(2, month);

            try(ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setStockID(rs.getInt("stockID"));
                    stock.setStockingDate(rs.getDate("stockingDate"));
                    stock.setStockingProcess(rs.getString("stockingProcess"));
                    stock.setStock_p_quantity(rs.getInt("stock_p_quantity"));
                    stock.setItemID(rs.getInt("itemID"));
                    stock.setWarehouseID(rs.getInt("warehouseID"));
                    stock.setSectionID(rs.getInt("sectionID"));
                    stock.setUID(rs.getInt("uID"));

                    // Item 정보 매핑
                    Item item = new Item();
                    item.setItemID(rs.getInt("itemID"));
                    item.setItemName(rs.getString("itemName"));
                    item.setItemPrice(rs.getInt("itemPrice"));

                    stock.setItem(item);
                    list.add(stock);
                }
            }
        } catch (SQLException e) {
            System.err.println("월별 입고현황 조회 실패: " + e.getMessage());
        }

        return list;
    }



    // 사용자 입고 요청 조회 (ID기준으로)
    @Override
    public List<Stock> stockCurrentSearch(int uID) {
        List<Stock> list = new ArrayList<>();
        String sql = "{CALL sp_stockCurrentSearch(?)}";

        try (CallableStatement cal = conn.prepareCall(sql)) {
            cal.setInt(1, uID);

            try (ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Stock s = new Stock();
                    s.setStockID(rs.getInt("stockID"));
                    s.setStockingDate(rs.getDate("stockingDate"));
                    s.setStockingProcess(rs.getString("stockingProcess"));
                    s.setStock_p_quantity(rs.getInt("stock_p_quantity"));
                    s.setItemID(rs.getInt("itemID"));
                    s.setWarehouseID(rs.getInt("warehouseID"));
                    s.setSectionID(rs.getInt("sectionID"));
                    s.setUID(rs.getInt("uID"));
                    // Item 정보 필요하면 여기서 VO에 넣어도 됨
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("입고 현황 조회 실패: " + e.getMessage());
        }

        return list;
    }
}

