package model.warehouse_service;

import util.DBUtil;
import vo.Warehouses.WarehouseFee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO implements FeeDAOImpl {

    // 싱글톤 패턴
    private static FeeDAO dao;

    private FeeDAO() {

    }

    public static FeeDAO getInstance() {
        if (dao == null) {
            dao = new FeeDAO();
        }
        return dao;
    }

    private WarehouseFee mapFee(ResultSet resultSet) throws SQLException {
        WarehouseFee warehouseFee = new WarehouseFee();
        warehouseFee.setStartDate(resultSet.getDate("startDate"));
        warehouseFee.setEndDate(resultSet.getDate("endDate"));
        warehouseFee.setPrice(resultSet.getInt("price"));
        warehouseFee.setWarehouseID(resultSet.getInt("warehouseID"));
        return warehouseFee;
    }

    // 요금 등록
    @Override
    public WarehouseFee insertFee(WarehouseFee wf) {

        String sql = "{CALL sp_InsertFee(?, ?, ?, ?, ?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            // startDate 설정 (java.util.Date -> java.sql.Date 변환 필요)
            callableStatement.setDate(1, new Date(wf.getStartDate().getTime()));
            callableStatement.setDate(2, new Date(wf.getEndDate().getTime()));
            callableStatement.setInt(3, wf.getPrice());
            callableStatement.setInt(4, wf.getWarehouseID());

            callableStatement.registerOutParameter(5, Types.INTEGER);

            callableStatement.executeUpdate();

            int feeID = callableStatement.getInt(5);

            wf.setId(feeID);
            return wf;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 사용자 요금 정보 수정
    @Override
    public int updateFee(WarehouseFee fee) {
        String sql = "{Call sp_UpdateFee(?, ?, ?, ?)}";
        int affectedRows = 0;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, fee.getId());
            callableStatement.setDate(2, new Date(fee.getEndDate().getTime()));
            callableStatement.setInt(3, fee.getPrice());
            callableStatement.setInt(4, fee.getWarehouseID());


            affectedRows = callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return affectedRows;
    }

    // 요금 삭제
    @Override
    public int deleteFee(int feeId) {
        String sql = "{CALL sp_DeleteFee(?)}";
        int affectedRows = 0;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, feeId);
            affectedRows = callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    // 특정 요금 정보 조회
    @Override
    public WarehouseFee selectFeeById(int feeId) {
        String sql = "{CALL sp_SelectFeeById(?)}";
        WarehouseFee fee = null;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, feeId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    fee = mapFee(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fee;
    }

    // 특정 창고에 속한 모든 창고 조회
    @Override
    public List<WarehouseFee> selectFeeByWarehouseId(int warehouseId) {
        List<WarehouseFee> feeList = new ArrayList<>();
        String sql = "{CALL sp_SelectFeesByWarehouseId(?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, warehouseId);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    feeList.add(mapFee(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeList;
    }
}
