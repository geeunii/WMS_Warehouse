package model.stock_service;

import util.DBUtil;
import vo.Items.Item;
import vo.Shippments.Shipment;
import vo.Stocks.Stock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO_User implements StockDAO_User_Interface {

    Connection conn = DBUtil.getConnection();




    // 사용자 입고 고지서 조회 (승인 된것만)
    @Override
    public Stock stockPrint(int stockID) {
        Stock s = new Stock();
        String sql = "{CALL sp_stockPrint(?)}";

        try(CallableStatement cal = conn.prepareCall(sql)) {
            cal.setInt(1, stockID);

            try (ResultSet rs = cal.executeQuery()) {
                if (rs.next()) {
                   s = new Stock();
                    s.setStockID(rs.getInt("stockID"));
                    s.setStockingDate(rs.getDate("stockingDate"));
                    s.setStockingProcess(rs.getString("stockingProcess"));
                    s.setStock_p_quantity(rs.getInt("stock_p_quantity"));
                    s.setItemID(rs.getInt("itemID"));
                    s.setWarehouseID(rs.getInt("warehouseID"));
                    s.setSectionID(rs.getInt("sectionID"));
                    s.setUID(rs.getInt("uID"));
                    // Item 정보 필요하면 VO에 추가 가능
                }
            }
        } catch (SQLException e) {
            System.err.println("입고고지서 조회 실패: " + e.getMessage());
        }

        return s;
    }





    // 사용자 입고요청
    @Override
    public int stockRequest(Item item, Stock stock) {
        int result = 0;

        String sql = "{CALL sp_StockRequest(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cal = conn.prepareCall(sql)) {

            // 1) 프로시저 파라미터 세팅 (Item + Stock)
            cal.setString(1, item.getItemName());
            cal.setInt(2, item.getItemPrice());
            cal.setInt(3, item.getWeight());
            cal.setString(4, item.getAssemble());
            cal.setString(5, item.getCustomerName());
            cal.setString(6, item.getMaterial());
            cal.setDouble(7, item.getWidth());
            cal.setDouble(8, item.getHeight());
            cal.setDouble(9, item.getLevelHeight());
            cal.setString(10, item.getSpaceName());
            cal.setString(11, item.getCategory());
            cal.setInt(12, stock.getStock_p_quantity());
            cal.setInt(13, stock.getWarehouseID());
            cal.setInt(14, stock.getSectionID());
            cal.setInt(15, stock.getUID());

            // 2) 실행
            cal.execute(); // executeUpdate() 대신 execute() 사용
            result = 1;    // 실행 성공이면 1로 세팅

        } catch (SQLException e) {
            System.err.println("입고 요청 실패: " + e.getMessage());
            e.printStackTrace();
            result = 0;
        }
        return result;
    }




    // 입고 요청 삭제
    @Override
    public int deleteStock(int stockID) {
        int result = 0;
        String sql = "{CALL sp_deleteStock(?)}";

        try(CallableStatement cal = conn.prepareCall(sql)) {
            cal.setInt(1,stockID);
            result = cal.executeUpdate();

        } catch (SQLException e) {
            System.err.println("입고 삭제 실패: " + e.getMessage());
        }
        return result;
    }






    // 입고 현황 조회
    @Override
    public List<Stock> stockCurrentSearch(int userID) {
        List<Stock> stockList = new ArrayList<>();

        String sql = "{CALL sp_stockCurrentSearch(?)}";

        try (CallableStatement cal = conn.prepareCall(sql)) {
            cal.setInt(1, userID);

            try (ResultSet rs = cal.executeQuery()) {
                while (rs.next()) {
                    Stock s = new Stock();
                    s.setStockID(rs.getInt("stockID"));
                    s.setStockingDate(rs.getDate("stockingDate"));
                    s.setStockingProcess(rs.getString("stockingProcess"));
                    s.setStock_p_quantity(rs.getInt("stock_p_quantity"));
                    s.setWarehouseID(rs.getInt("warehouseID"));
                    s.setSectionID(rs.getInt("sectionID"));
                    s.setUID(rs.getInt("uID"));

                    // Item 객체 생성 후 Stock VO에 연결
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

                    s.setItem(item);

                    stockList.add(s);
                }
            }
        } catch (SQLException e) {
            System.err.println("승인된 입고현황 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
        }

        return stockList;
    }
}

