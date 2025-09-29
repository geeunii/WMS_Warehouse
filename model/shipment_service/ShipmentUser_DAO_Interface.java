package model.shipment_service;

import vo.Shippments.Shipment;

import java.util.List;

public interface ShipmentUser_DAO_Interface {

    public int createShipment(Shipment shipment);
    public List<Shipment> selectCurrentShipmentUser(int shipmentId);

}
