package model.warehouse_service;

import util.DBUtil;
import vo.Warehouses.WarehouseSection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseSectionDAO implements WarehouseSectionDAOImpl {

    private static WarehouseSectionDAO dao;

    private WarehouseSectionDAO() {

    }

    public static WarehouseSectionDAO getInstance() {
        if (dao == null) {
            dao = new WarehouseSectionDAO();
        }
        return dao;
    }

    private WarehouseSection mapSection(ResultSet resultSet) throws SQLException {
        WarehouseSection warehouseSection = new WarehouseSection();

        warehouseSection.setSectionName(resultSet.getString("warehouseSectionName"));
        warehouseSection.setMaxVol(resultSet.getInt("maxVol"));
        warehouseSection.setCurrentVol(resultSet.getInt("currentVol"));
        warehouseSection.setWarehouseID(resultSet.getInt("warehouseID"));

        return warehouseSection;
    }

    // 구역(섹션) 등록
    @Override
    public WarehouseSection insertSection(WarehouseSection ws) {

        String sql = "{CALL sp_InsertSection(?, ?, ?, ?, ?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setString(1, ws.getSectionName());
            callableStatement.setInt(2, ws.getMaxVol());
            callableStatement.setInt(3, ws.getCurrentVol());
            callableStatement.setInt(4, ws.getWarehouseID());

            callableStatement.registerOutParameter(5, Types.INTEGER);

            callableStatement.executeUpdate();

            int sectionID = callableStatement.getInt(5);

            ws.setId(sectionID);
            return ws;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 특정 창고 구역 정보 출력
     *
     * @param warehouseID 구역 정보를 조회할 창고의 ID
     * @return list
     */
    @Override
    public List<WarehouseSection> getSections(int warehouseID) {
        List<WarehouseSection> sectionList = new ArrayList<>();

        String sql = "{CALL sp_GetSectionsByWarehouseId(?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, warehouseID);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    sectionList.add(mapSection(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectionList;
    }

    /**
     * 구역 ID(PK)를 이용하여 특정 창고 구역 정보 하나를 조회합니다.
     * @param sectionId 조회할 구역의 ID
     * @return 조회된 WarehouseSection 객체. 결과가 없으면 null을 반환합니다.
     */
    public WarehouseSection selectById(int sectionId) {
        WarehouseSection section = null; // 결과를 담을 객체, 초기값은 null

        String sql = "{CALL sp_GetSectionById(?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement cstmt = connection.prepareCall(sql)) {

            cstmt.setInt(1, sectionId);

            try (ResultSet resultSet = cstmt.executeQuery()) {
                if (resultSet.next()) {
                    section = mapSection(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }


    /**
     * 구역 정보 수정
     *
     * @param ws 수정할 정보가 담긴 WarehouseSection 객체
     * @return affectedRows
     */
    @Override
    public int updateSection(WarehouseSection ws) {

        String sql = "{CALL sp_UpdateSection(? ,? ,? ,?, ?)}";

        int affectedRows = 0;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, ws.getId()); // 1. sectionID
            callableStatement.setString(2, ws.getSectionName()); // 2. sectionName
            callableStatement.setInt(3, ws.getMaxVol()); // 3. maxVol
            callableStatement.setInt(4, ws.getCurrentVol()); // 4. currentVol
            callableStatement.setInt(5, ws.getWarehouseID()); // 5. warehouseID


            affectedRows = callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return affectedRows;
    }


    /**
     * 구역 삭제
     *
     * @param secID 삭제할 구역 정보의 ID
     * @return affectedRows
     */
    @Override
    public int deleteSection(int secID) {

        String sql = "{CALL sp_DeleteSection(?)}";

        int affectedRows = 0;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, secID);
            affectedRows = callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return affectedRows;
    }


}
