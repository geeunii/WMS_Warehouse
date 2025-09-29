package model.shipment_service;

import vo.Shippments.Shipment;

import java.util.List;

public interface Shipment_DAO_Interface {



    public int updateShipment(Shipment shipment);
    public List<Shipment>  selectCurrentShipment();
    public List<Shipment> selectShipmentByID(int shipmentID);
    List<Shipment> selectPendingShipment();

}
