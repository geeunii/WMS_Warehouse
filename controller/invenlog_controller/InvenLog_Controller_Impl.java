package controller.invenlog_controller;

import model.inventory_service.InvenLogDAO;
import model.inventory_service.InvenLog_interface;

public class InvenLog_Controller_Impl implements InvenLog_Controller {
    InvenLogDAO invenLogDAO = new InvenLogDAO();
    @Override
    public int logInventoryinsert() {
        return invenLogDAO.logInventoryinsert();
    }
}
