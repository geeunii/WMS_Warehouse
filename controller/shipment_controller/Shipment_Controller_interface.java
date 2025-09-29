package controller.shipment_controller;

import vo.Shippments.Shipment;

import java.util.List;

public interface Shipment_Controller_interface {


    public int updateShipment(Shipment shipment);
    public List<Shipment>  selectCurrentShipment();
    public List<Shipment> selectShipmentByID(int userID);
    List<Shipment> selectPendingShipment();

}
