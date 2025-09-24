package controller.warehouse_section_controller;

import vo.Warehouses.WarehouseSection;

import java.util.List;

public interface Warehouse_Section_Controller {

    void choiceSectionMenu(int choice);

    WarehouseSection insertSection();

    int updateSection();

    int deleteSection();

    WarehouseSection selectSectionById();

    List<WarehouseSection> selectSectionWarehouseID(int warehouseID);
}
