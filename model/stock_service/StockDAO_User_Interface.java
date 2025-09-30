package model.stock_service;

import vo.Items.Item;
import vo.Stocks.Stock;

import java.util.List;

public interface StockDAO_User_Interface {


    /**
     * 4.7 입고 현황 조회
     * 사용자 입고 현황 조회
     */
    public List<Stock> stockCurrentSearch(int uID);

    /**
     * 4.5 입고고지서 출력
     * 특정 입고 내역 출력
     */
    public Stock stockPrint(int stockID, int userID);

    /**
     * 4.1 입고 요청
     * 사용자 입고요청 정보 입력
     */
    public int stockRequest(Item item, Stock stock);


    public int deleteStock(int stockID);

}
