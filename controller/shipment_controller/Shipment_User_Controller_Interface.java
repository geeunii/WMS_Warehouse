package controller.shipment_controller;

import vo.Shippments.Shipment;

import java.util.List;

public interface Shipment_User_Controller_Interface {

    public int createShipment(Shipment shipment);
    public List<Shipment> selectCurrentShipment(int shipmentID);
}
