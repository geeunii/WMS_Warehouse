package controller.shipment_controller;

import model.shipment_service.ShipmentDAO;
import model.shipment_service.Shipment_DAO_Interface;
import vo.Shippments.Shipment;

import java.util.List;

public class Shipment_Controller implements Shipment_Controller_interface {

    ShipmentDAO ship = new ShipmentDAO();


    @Override
    public int updateShipment(Shipment shipment) {
        return ship.updateShipment(shipment);
    }

    @Override
    public List<Shipment> selectCurrentShipment() {
        return ship.selectCurrentShipment();
    }

    @Override
    public List<Shipment> selectShipmentByID(int userID) {
        return ship.selectShipmentByID(userID);
    }


    @Override
    public List<Shipment> selectPendingShipment() {
        return ship.selectPendingShipment();
    }
}