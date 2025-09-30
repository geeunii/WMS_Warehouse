package controller.shipment_controller;

import vo.Shippments.Shipment;

import java.util.List;

public interface Shipment_User_Controller_Interface {

    public int createShipment(Shipment shipment);
    public List<Shipment> selectCurrentShipmentUser(int userID);
    public List<Shipment> shippingProductSearch(int itemID);
}
