package model.warehouse_service;

import util.DBUtil;
import vo.Warehouses.WarehouseFee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 창고 요금(warehouseFee) 데이터에 대한 데이터 접근 객체 클래스
 * 데이터베이스의 WarehouseFee 테이블과 직접 통신하여 데이터를 조작하는 역할
 * 싱글톤 패턴으로 구현되어 프로그램 내에서 단 하나의 인스턴스만 생성
 */
public class FeeDAO implements FeeDAOImpl {

    // --- 싱글톤 패턴 ---
    private static FeeDAO dao;

    // 외부에서 new 키워드로 객체를 생성하는 것을 막기 위함
    private FeeDAO() {

    }

    /**
     * FeeDAO 클래스의 인스턴스를 반환하는 정적 메서드
     * @return FeeDAO 싱글톤 인스턴스
     */
    public static FeeDAO getInstance() {
        if (dao == null) {
            dao = new FeeDAO();
        }
        return dao;
    }

    /**
     * 헬퍼 메서드
     * ResultSet 현재 행 데이터를 WarehouseFee 객체로 변환(매핑)
     * @param resultSet 데이터베이스 조회 결과Set
     * @return 데이터가 채워진 WarehouseFee 객체
     * @throws SQLException DB 관련 예외
     */
    private WarehouseFee mapFee(ResultSet resultSet) throws SQLException {
        WarehouseFee warehouseFee = new WarehouseFee();
        warehouseFee.setId(resultSet.getInt("feeID"));
        warehouseFee.setStartDate(resultSet.getDate("startDate"));
        warehouseFee.setEndDate(resultSet.getDate("endDate"));
        warehouseFee.setPrice(resultSet.getInt("price"));
        warehouseFee.setWarehouseID(resultSet.getInt("warehouseID"));
        return warehouseFee;
    }

    /**
     * 요금 정보를 데이터베이스에 등록
     * @param wf 등록할 요금 정보가 담긴 WarehouseFee 객체
     * @return DB 등록 후, feeID 포함된 WarehouseFee 객체
     */
    @Override
    public WarehouseFee insertFee(WarehouseFee wf) {

        String sql = "{CALL sp_InsertFee(?, ?, ?, ?, ?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

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

    /**
     * 기존 요금 정보 수정
     * @param fee 수정할 정보가 담긴 WarehouseFee 객체 (id 포함)
     * @return 수정된 행의 수. 성공 1, 실패 0
     */
    @Override
    public int updateFee(WarehouseFee fee) {
        String sql = "{Call sp_UpdateFee(?, ?, ?, ?)}";
        int affectedRows = 0;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, fee.getId());
            callableStatement.setDate(2, new Date(fee.getEndDate().getTime())); // 계약 시작일은 고정. 종료일만 수정
            callableStatement.setInt(3, fee.getPrice());
            callableStatement.setInt(4, fee.getWarehouseID());


            affectedRows = callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return affectedRows;
    }

    /**
     * 특정 ID 요금 정보 삭제
     * @param feeId 삭제할 요금 정보의 feeID
     * @return 삭제된 행의 수. 성공 1, 실패 0
     */
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

    /**
     * feeID 를 이용한 특정 요금 정보 하나 조회
     * @param feeId 조회할 요금 정보의 ID
     * @return 조회딘 WarehouseFee 객체. 결과 없으면 null
     */
    @Override
    public WarehouseFee selectFeeById(int feeId) {
        String sql = "{CALL sp_SelectFeeById(?)}";
        WarehouseFee fee = null;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, feeId);

            // SELECT 쿼리니까 executeQuery 사용, 결과 ResultSet
            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) {
                    fee = mapFee(resultSet); // 헬퍼 메서드
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fee;
    }

    /**
     * 특정 창고 ID에 소속된 모든 요금 정보 조회
     * @param warehouseId 요금 정보를 조회할 창고의 ID
     * @return 해당 창고의 모든 요금 정보 리스트. 결과 없으면 빈 리스트
     */
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
