package controller.shipment_controller;

import model.shipment_service.ShipmentDAO;
import model.shipment_service.ShipmentUser_DAO;
import util.DBUtil;
import vo.Shippments.Shipment;

import java.sql.Connection;
import java.util.List;

public class Shipment_User_Controller implements Shipment_User_Controller_Interface {


    ShipmentUser_DAO shipUser = new ShipmentUser_DAO();


    @Override
    public int createShipment(Shipment shipment) {
        return shipUser.createShipment(shipment);
    }


    @Override
    public List<Shipment> selectCurrentShipmentUser(int userID) {
        return shipUser.selectCurrentShipmentUser(userID);
    }


    @Override
    public List<Shipment> shippingProductSearch(int itemID) {
        return shipUser.shippingProductSearch(itemID);
    }
}
