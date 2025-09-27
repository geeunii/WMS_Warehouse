package controller.shipment_controller;

import model.shipment_service.ShipmentDAO;
import model.shipment_service.Shipment_DAO_Interface;
import vo.Shippments.Shipment;

import java.util.List;

public class Shipment_Controller implements Shipment_DAO_Interface {

    private final Shipment_DAO_Interface dao;

    public Shipment_Controller() {
        this.dao = new ShipmentDAO(); // DAO 초기화
    }

    @Override
    public int createShipment(Shipment shipment) {
        return dao.createShipment(shipment);
    }

    @Override
    public int deleteShipment(int shipmentID, int userID) {
        return dao.deleteShipment(shipmentID, userID);
    }

    @Override
    public int updateShipment(Shipment shipment) {
        return dao.updateShipment(shipment);
    }

    @Override
    public List<Shipment> selectCurrentShipment(int warehouseID) {
        return dao.selectCurrentShipment(warehouseID);
    }

    @Override
    public Shipment selectShipmentByID(int shipmentID) {
        return dao.selectShipmentByID(shipmentID);
    }

    @Override
    public int registerWaybill(Shipment shipment) {
        return dao.registerWaybill(shipment);
    }

    @Override
    public int updateWaybill(Shipment shipment) {
        return dao.updateWaybill(shipment);
    }

    @Override
    public String selectWaybillADMIN(int shipmentID) {
        return dao.selectWaybillADMIN(shipmentID);
    }

    @Override
    public List<Shipment> searchShipmentByProduct(String keyword) {
        return dao.searchShipmentByProduct(keyword);
    }

    @Override
    public String selectWaybillUSER(int shipmentID, int userID) {
        return dao.selectWaybillUSER(shipmentID, userID);
    }
}