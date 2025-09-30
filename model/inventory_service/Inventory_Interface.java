package model.inventory_service;

import vo.Inventorys.*;

import java.util.List;

public interface Inventory_Interface {
    // vo 생성 완료
    // 총관리자의 재고 조회
    public List<Master_searchVo> Master_inven_search();
    // 총관리자의 카테고리별 재고 조회
    public List<Master_searchVo_ca> Master_inven_search_ca(String categoryName);
    // 총관리자의 공간별 재고 조회
    public List<Master_searchVo_sp> Master_inven_search_sp(String spaceName);
    // 창고 관리자의 재고 조회
    public List<Admin_searchVo> Admin_inven_search(int mid);
    // 창고 관리자의 카테고리별 재고 조회
    public List<Admin_searchVo_ca> Admin_inven_search_ca(int mid, String categoryName);
    // 창고 관리자의 공간별 재고 조회
    public List<Admin_searchVo_sp> Admin_inven_search_sp(int mid, String spaceName);

    //vo 미생성
    public List<User_searchVo> User_inven_search(int uid);
    public List<User_searchVo_ca> User_inven_search_ca(int uid, String categoryName);
    public List<User_searchVo_sp> User_inven_search_sp(int uid, String spaceName);

    public List<Master_warehouse_listVo> Master_warehouse_list();
    public List<Admin_warehouse_listVo> Admin_warehouse_list(int mid);

    public List<Master_correspond_listVo> Master_correspond_list();
    public List<Admin_correspond_listVo> Admin_correspond_list(int mid);


}
