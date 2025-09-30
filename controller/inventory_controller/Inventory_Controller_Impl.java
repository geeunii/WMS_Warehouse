package controller.inventory_controller;

import model.inventory_service.InventoryDAO;
import vo.Inventorys.*;

import java.util.List;

public class Inventory_Controller_Impl implements Inventory_Controller {
    InventoryDAO idao = new InventoryDAO();

    @Override
    public List<Master_searchVo> Master_inven_search() {
        return idao.Master_inven_search();
    }

    @Override
    public List<Master_searchVo_ca> Master_inven_search_ca(String categoryName) {
        return idao.Master_inven_search_ca(categoryName);
    }

    @Override
    public List<Master_searchVo_sp> Master_inven_search_sp(String spaceName) {
        return idao.Master_inven_search_sp(spaceName);
    }

    @Override
    public List<Admin_searchVo> Admin_inven_search(int mid) {
        return idao.Admin_inven_search(mid);
    }

    @Override
    public List<Admin_searchVo_ca> Admin_inven_search_ca(int mid,String categoryName) {
        return idao.Admin_inven_search_ca(mid,categoryName);
    }

    @Override
    public List<Admin_searchVo_sp> Admin_inven_search_sp(int mid, String spaceName) {
        return idao.Admin_inven_search_sp(mid, spaceName);
    }

    @Override
    public List<User_searchVo> User_inven_search(int uid) {
        return idao.User_inven_search(uid);
    }

    @Override
    public List<User_searchVo_ca> User_inven_search_ca(int uid, String categoryName) {
        return idao.User_inven_search_ca(uid,categoryName);
    }

    @Override
    public List<User_searchVo_sp> User_inven_search_sp(int uid, String spaceName) {
        return idao.User_inven_search_sp(uid,spaceName);
    }

    @Override
    public List<Master_warehouse_listVo> Master_warehouse_list() {
        return idao.Master_warehouse_list();
    }

    @Override
    public List<Admin_warehouse_listVo> Admin_warehouse_list(int mid) {
        return idao.Admin_warehouse_list(mid);
    }

    @Override
    public List<Master_correspond_listVo> Master_correspond_list() {
        return idao.Master_correspond_list();
    }

    @Override
    public List<Admin_correspond_listVo> Admin_correspond_list(int mid) {
        return idao.Admin_correspond_list(mid);
    }
}
