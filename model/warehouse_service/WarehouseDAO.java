package model.warehouse_service;

import util.DBUtil;
import vo.Warehouses.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code WarehouseDAO} 클래스는 창고 데이터를 관리하기 위한 데이터 접근 객체(DAO)
 * 데이터베이스에 연결되어 창고 데이터를 추가, 조회하는 기능을 제공
 * <p>
 * 클래스 주요 기능
 * - 데이터베이스 연결 및 쿼리 실행
 * - 창고 데이터 세부적 추가, 특정 창고 조회
 */
public class WarehouseDAO implements WarehouseDAOImpl {

    // DAO 인스턴스 싱글톤으로 구현
    private static WarehouseDAO dao;

    // 생성자를 private 설정, 외부 객체 생성 제한
    private WarehouseDAO() {

    }

    /**
     * DAO 인스턴스 반환하는 싱글톤 정적 메서드
     *
     * @return {@code WarehouseDAO} 인스턴스
     */
    public static WarehouseDAO getInstance() {
        if (dao == null) dao = new WarehouseDAO();
        return dao;
    }

    // 창고 데이터 관리 리스트
    private final List<Warehouse> warehouseList = new ArrayList<>();

    // 데이터베이스 연결 객체
    private Connection connection;


    /**
     * 헬퍼 메서드
     * ResultSet 의 현재 행 데이터를 Warehouse 객채로 변환(매핑)
     *
     * @param resultSet 데이터베이스 조회 결과Set
     * @return 데이터가 채워진 Warehouse 객체
     * @throws SQLException DB 관련 예외
     */
    private Warehouse mapWarehouse(ResultSet resultSet) throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(resultSet.getInt("warehouseID"));
        warehouse.setWarehouseName(resultSet.getString("warehouseName"));
        warehouse.setWarehouseAddress(resultSet.getString("warehouseAddress"));
        warehouse.setWarehouseStatus(resultSet.getString("warehouseStatus"));
        warehouse.setWarehouseCityName(resultSet.getString("warehouseCityName"));
        warehouse.setMaxCapacity(resultSet.getInt("maxCapacity"));
        warehouse.setWarehouseArea(resultSet.getInt("warehouseArea"));
        warehouse.setRegDate(resultSet.getDate("regDate"));
        warehouse.setFloorHeight(resultSet.getInt("floorHeight"));
        warehouse.setMid(resultSet.getInt("mid"));

        return warehouse;
    }

    // SQL 문 실행을 위한 PreparedStatement 객체
    // private PreparedStatement preparedStatement;

    // SQL 쿼리 결과를 저장하는 ResultSet 객체
    // private ResultSet resultSet;

    /**
     * 데이터베이스 연결 종료 메서드
     */
//    private void disConnect() {
//        if (resultSet != null)
//            try {
//                resultSet.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        if (preparedStatement != null)
//            try {
//                preparedStatement.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        if (connection != null)
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//    }

    /**
     * 새로운 창고 정보 데이터베이스에 등록
     *
     * @param wh 등록할 창고 정보가 담긴 Warehouse 객체 (id는 auto increment)
     * @return DB에 등록, 자동 생성된 warehouseID 가 포함된 객체
     */
    @Override
    public Warehouse insertWarehouse(Warehouse wh) {
        // 프로시저 호출
        String sql = "{CALL sp_InsertWarehouse(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            // IN 파라미터 값 설정
            callableStatement.setString(1, wh.getWarehouseName());
            callableStatement.setString(2, wh.getWarehouseAddress());
            callableStatement.setString(3, wh.getWarehouseStatus());
            callableStatement.setString(4, wh.getWarehouseCityName());
            // callableStatement.setInt(5, wh.getMaxCapacity());
            callableStatement.setInt(5, wh.getWarehouseArea());
            callableStatement.setInt(6, wh.getFloorHeight());
            callableStatement.setInt(7, wh.getMid());

            // OUT 파라미터 등록
            callableStatement.registerOutParameter(8, Types.INTEGER);

            // 프로시저 실행
            callableStatement.executeUpdate();

            // OUT 파라미터 값 가져오기
            int newWarehouseID = callableStatement.getInt(8);

            // 객체에 ID 설정 후 반환
//            wh.setId(newWarehouseID);
//            return wh;
            return this.selectId(newWarehouseID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 실패 시 null
    }

    /**
     * 창고 이름으로 창고 목록 검색
     *
     * @param wname 검색할 창고 이름
     * @return 검색 조건에 맞는 Warehouse 객체 리스트
     */
    public List<Warehouse> searchByName(String wname) {
        List<Warehouse> warehouseList = new ArrayList<>();
        String sql = "{CALL sp_searchByName(?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setString(1, wname);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) { // 여러 개일수도 있으니 while 문
                    warehouseList.add(mapWarehouse(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouseList;
    }

    /**
     * 모든 창고 목록 조회
     *
     * @return 모든 Warehouse 객체가 담긴 리스트
     */
    @Override
    public List<Warehouse> searchAllWarehouse() {
        List<Warehouse> warehouseList = new ArrayList<>();
        String sql = "{CALL sp_searchAllWarehouse()}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql);
             ResultSet resultSet = callableStatement.executeQuery()) {

            while (resultSet.next()) {
                // 헬퍼 메서드를 사용해 객체로 변환 후 리스트에 추가
                warehouseList.add(mapWarehouse(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouseList;
    }

    /**
     * 창고 소재지(주소) 로 창고 목록 검색
     *
     * @param waddress 검색할 창고 주소
     * @return 검색 조건에 맞는 Warehouse 객체 리스트
     */
    public List<Warehouse> selectByLocation(String waddress) {
        List<Warehouse> warehouseList = new ArrayList<>();
        String sql = "{CALL sp_selectByLocation(?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setString(1, waddress);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    warehouseList.add(mapWarehouse(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouseList;
    }

    /**
     * 창고 면적으로 창고 목록 검색
     *
     * @param wsize 검색할 창고 면적
     * @return 검색 조건에 맞는 Warehouse 객체 리스트
     */
    public List<Warehouse> selectBySize(int wsize) {
        List<Warehouse> warehouseList = new ArrayList<>();
        String sql = "{Call sp_selectBySize(?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, wsize);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    warehouseList.add(mapWarehouse(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouseList;
    }

    /**
     * 창고 ID 로 해당 창고 운영 상태 조회
     *
     * @param wid 조회할 창고의 ID
     * @return 창고의 운영 상태
     */
    @Override
    public String getWarehouseStatus(int wid) {
        String status = null; // 결과를 담을 변수, 초기값은 null

        String sql = "{CALL sp_getWarehouseStatusById(?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, wid);

            try (ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()) { // ID 로 조회하므로 결과는 최대 1개 -> if 사용
                    status = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 기존 창고 정보 수정
     *
     * @param wh 수정할 정보가 담긴 Warehouse 객체 (id 포함)
     * @return 수정 행의 수. 성공 1, 실패 0
     */
    public int updateWarehouse(Warehouse wh) {
        String sql = "{CALL sp_updateWarehouse(?, ?, ?, ?, ?, ?, ?, ?)}";
        int affectedRows = 0;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, wh.getId()); // p_warehouseID
            callableStatement.setString(2, wh.getWarehouseName());
            callableStatement.setString(3, wh.getWarehouseAddress());
            callableStatement.setString(4, wh.getWarehouseStatus());
            callableStatement.setString(5, wh.getWarehouseCityName());
            // callableStatement.setInt(6, wh.getMaxCapacity());
            callableStatement.setInt(6, wh.getWarehouseArea());
            callableStatement.setInt(7, wh.getFloorHeight());
            callableStatement.setInt(8, wh.getMid());

            affectedRows = callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    /**
     * 특정 ID 창고 삭제
     *
     * @param warehouseId 삭제할 창고 ID
     * @return 삭제 행 수. 성공 1, 실패 0
     */
    public int deleteWarehouse(int warehouseId) {
        String sql = "{CALL sp_deleteWarehouse(?)}";
        int affectedRows = 0;

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, warehouseId);

            affectedRows = callableStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    /**
     * ID 단일 조회
     * @param warehouseId 창고 ID
     * @return 창고 ID 를 통해 DB 등록된 창고 불러옴.
     */
    public Warehouse selectId(int warehouseId) {
        Warehouse warehouse = null;
        String sql = "{CALL sp_GetWarehouseId(?)}";

        try (Connection connection = DBUtil.getConnection();
             CallableStatement callableStatement = connection.prepareCall(sql)) {

            callableStatement.setInt(1, warehouseId);
            try (ResultSet rs = callableStatement.executeQuery()) {
                if (rs.next()) {
                    warehouse = mapWarehouse(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }
}
