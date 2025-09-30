package model.inventory_service;

import util.DBUtil;
import vo.Inventorys.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO implements Inventory_Interface {

    Connection conn = DBUtil.getConnection();

    @Override
    public List<Master_searchVo> Master_inven_search() {
        List<Master_searchVo> minvenList = new ArrayList<Master_searchVo>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select stockdate as '입고 날짜', customername as'등록자 이름', itemname as '가구 이름', quantity as '수량' ,\n" +
                " itemprice as '단가', quantity * itemprice as '금액 합계' , warehousename as '창고명', warehousestatus as '창고 상태'\n" +
                "from invenwareitem;") ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Master_searchVo vo = new Master_searchVo();

                vo.setStockdate(rs.getDate("입고 날짜"));
                vo.setCustomername(rs.getString("등록자 이름"));
                vo.setItemname(rs.getString("가구 이름"));
                vo.setQuantity(rs.getInt("수량"));
                vo.setItemprice(rs.getInt("단가"));
                vo.setTotalprice(rs.getInt("금액 합계"));
                vo.setWarehousename(rs.getString("창고명"));
                vo.setWarehousestate(rs.getString("창고 상태"));

                minvenList.add(vo);
            }
            return minvenList;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Master_searchVo_ca> Master_inven_search_ca(String categoryName) {
        List<Master_searchVo_ca> msvoc =  new ArrayList<>();
        String sql = "select stockdate as '입고 날짜', customername as'등록자 이름', itemname as '가구 이름', quantity as '수량' ,\n" +
                " itemprice as '단가', quantity * itemprice as '금액 합계' , warehousename as '창고명', warehousestatus as '창고 상태', category as '카테고리명'\n" +
                "from invenwareitem where category = ? ; ";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Master_searchVo_ca voca = new Master_searchVo_ca();

                voca.setStockdate(rs.getDate("입고 날짜"));
                voca.setCustomername(rs.getString("등록자 이름"));
                voca.setItemname(rs.getString("가구 이름"));
                voca.setQuantity(rs.getInt("수량"));
                voca.setItemprice(rs.getInt("단가"));
                voca.setTotalprice(rs.getInt("금액 합계"));
                voca.setWarehousename(rs.getString("창고명"));
                voca.setWarehousestate(rs.getString("창고 상태"));
                voca.setCategory(rs.getString("카테고리명"));

                msvoc.add(voca);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return msvoc;

    }

    @Override
    public List<Master_searchVo_sp> Master_inven_search_sp(String spaceName) {
        List<Master_searchVo_sp> msvsp =  new ArrayList<>();
        String sql = "select stockdate as '입고 날짜', customername as'등록자 이름', itemname as '가구 이름', quantity as '수량' ,\n" +
                " itemprice as '단가', quantity * itemprice as '금액 합계' , warehousename as '창고명', warehousestatus as '창고 상태', spacename as '공간명'\n" +
                "from invenwareitem where spacename = ? ; ";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, spaceName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Master_searchVo_sp vosp = new Master_searchVo_sp();

                vosp.setStockdate(rs.getDate("입고 날짜"));
                vosp.setCustomername(rs.getString("등록자 이름"));
                vosp.setItemname(rs.getString("가구 이름"));
                vosp.setQuantity(rs.getInt("수량"));
                vosp.setItemprice(rs.getInt("단가"));
                vosp.setTotalprice(rs.getInt("금액 합계"));
                vosp.setWarehousename(rs.getString("창고명"));
                vosp.setWarehousestate(rs.getString("창고 상태"));
                vosp.setSpacename(rs.getString("공간명"));

                msvsp.add(vosp);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return msvsp;
    }

    @Override
    public List<Admin_searchVo> Admin_inven_search(int mid) {
        List<Admin_searchVo> minvenList = new ArrayList<Admin_searchVo>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select stockdate as '입고 날짜', customername as'등록자 이름', itemname as '가구 이름', quantity as '수량' ,\n" +
                " itemprice as '단가', quantity * itemprice as '금액 합계' ,sectionname as '보관 구역명'\n" +
                "from invenwareitem where mid = ? ;")) {
            ps.setInt(1,mid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Admin_searchVo vo = new Admin_searchVo();

                vo.setStockdate(rs.getDate("입고 날짜"));
                vo.setCustomername(rs.getString("등록자 이름"));
                vo.setItemname(rs.getString("가구 이름"));
                vo.setQuantity(rs.getInt("수량"));
                vo.setItemprice(rs.getInt("단가"));
                vo.setTotalprice(rs.getInt("금액 합계"));
                vo.setSectionname(rs.getString("보관 구역명"));

                minvenList.add(vo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return minvenList;
    }

    @Override
    public List<Admin_searchVo_ca> Admin_inven_search_ca(int mid,String categoryName) {
        List<Admin_searchVo_ca> ainvencaList = new ArrayList<Admin_searchVo_ca>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select stockdate as '입고 날짜', customername as'등록자 이름', itemname as '가구 이름', quantity as '수량' ,\n" +
                " itemprice as '단가', quantity * itemprice as '금액 합계' ,sectionname as '보관 구역명',category as '카테고리명'\n" +
                "from invenwareitem where mid = ? and category = ? ;")) {
            ps.setInt(1,mid);
            ps.setString(2,categoryName);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Admin_searchVo_ca voca = new Admin_searchVo_ca();

                voca.setStockdate(rs.getDate("입고 날짜"));
                voca.setCustomername(rs.getString("등록자 이름"));
                voca.setItemname(rs.getString("가구 이름"));
                voca.setQuantity(rs.getInt("수량"));
                voca.setItemprice(rs.getInt("단가"));
                voca.setTotalprice(rs.getInt("금액 합계"));
                voca.setSectionname(rs.getString("보관 구역명"));
                voca.setCategory(rs.getString("카테고리명"));

                ainvencaList.add(voca);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ainvencaList;
    }

    @Override
    public List<Admin_searchVo_sp> Admin_inven_search_sp(int mid, String spaceName) {

        List<Admin_searchVo_sp> ainvenspList = new ArrayList<Admin_searchVo_sp>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select stockdate as '입고 날짜', customername as'등록자 이름', itemname as '가구 이름', quantity as '수량' ,\n" +
                " itemprice as '단가', quantity * itemprice as '금액 합계' ,sectionname as '보관 구역명',spacename as '공간명'\n" +
                "from invenwareitem where mid = ? and spacename = ? ;")) {
            ps.setInt(1,mid);
            ps.setString(2,spaceName);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Admin_searchVo_sp vosp = new Admin_searchVo_sp();

                vosp.setStockdate(rs.getDate("입고 날짜"));
                vosp.setCustomername(rs.getString("등록자 이름"));
                vosp.setItemname(rs.getString("가구 이름"));
                vosp.setQuantity(rs.getInt("수량"));
                vosp.setItemprice(rs.getInt("단가"));
                vosp.setTotalprice(rs.getInt("금액 합계"));
                vosp.setSectionname(rs.getString("보관 구역명"));
                vosp.setSpacename(rs.getString("공간명"));

                ainvenspList.add(vosp);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ainvenspList;
    }

    @Override
    public List<User_searchVo> User_inven_search(int uid) {
        List<User_searchVo> uinvenList = new ArrayList<User_searchVo>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select itemname as '가구 이름', quantity as'가구 수량', quantity * itemprice as '금액 합계', warehousecityname as '창고 이름'\n" +
                "from invenwareitem where uid = ? ;") ) {
            ps.setInt(1,uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User_searchVo vo = new User_searchVo();
                vo.setItemname(rs.getString("가구 이름"));
                vo.setQuantity(rs.getString("가구 수량"));
                vo.setTotalprice(rs.getString("금액 합계"));
                vo.setWarehousename(rs.getString("창고 이름"));
                uinvenList.add(vo);
            }
            return uinvenList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User_searchVo_ca> User_inven_search_ca(int uid, String categoryName) {
        List<User_searchVo_ca> uinvencaList = new ArrayList<User_searchVo_ca>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select itemname as '가구 이름', quantity as'가구 수량', quantity * itemprice as '금액 합계', warehousecityname as '창고 이름',category as '카테고리명'\n" +
                "from invenwareitem where uid = ? and category= ? ;") ) {
            ps.setInt(1,uid);
            ps.setString(2,categoryName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User_searchVo_ca voca= new User_searchVo_ca();
                voca.setItemname(rs.getString("가구 이름"));
                voca.setQuantity(rs.getString("가구 수량"));
                voca.setTotalprice(rs.getString("금액 합계"));
                voca.setWarehousename(rs.getString("창고 이름"));
                voca.setCategory(rs.getString("카테고리명"));
                uinvencaList.add(voca);
            }
            return uinvencaList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User_searchVo_sp> User_inven_search_sp(int uid, String spaceName) {
        List<User_searchVo_sp> uinvenspList = new ArrayList<User_searchVo_sp>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select itemname as '가구 이름', quantity as'가구 수량', quantity * itemprice as '금액 합계', warehousecityname as '창고 이름',category as '공간명'\n" +
                "from invenwareitem where uid = ? and spacename= ? ;") ) {
            ps.setInt(1,uid);
            ps.setString(2,spaceName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User_searchVo_sp vosp= new User_searchVo_sp();
                vosp.setItemname(rs.getString("가구 이름"));
                vosp.setQuantity(rs.getString("가구 수량"));
                vosp.setTotalprice(rs.getString("금액 합계"));
                vosp.setWarehousename(rs.getString("창고 이름"));
                vosp.setSpacename(rs.getString("공간명"));
                uinvenspList.add(vosp);
            }
            return uinvenspList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Master_warehouse_listVo> Master_warehouse_list() {
        List<Master_warehouse_listVo> mwlivo = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select warehouseid as '창고 번호', warehousename as '창고 이름', warehousecityname as '창고 위치 지역', \n" +
                "warehouseStatus as '창고 상태', currentvol as '창고 현재 사용량', (maxvol - currentvol) as '창고 수용 가능량'\n" +
                "from invenwareitem;")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Master_warehouse_listVo mvo = new Master_warehouse_listVo();
                mvo.setWarehouseID(rs.getInt("창고 번호"));
                mvo.setWarehouseName(rs.getString("창고 이름"));
                mvo.setWarehouseCityName(rs.getString("창고 위치 지역"));
                mvo.setWarehouseStratus(rs.getString("창고 상태"));
                mvo.setCurrentVol(rs.getInt("창고 현재 사용량"));
                mvo.setCanvol(rs.getInt("창고 수용 가능량"));

                mwlivo.add(mvo);
            }
            return mwlivo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Admin_warehouse_listVo> Admin_warehouse_list(int mid) {
        List<Admin_warehouse_listVo> awlivo = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select warehousename as '창고 이름', warehousestatus as '창고 상태', sectionname as '창고 구역', \n" +
                "currentvol as '창고 현재 사용량', (maxvol - currentvol) as '창고 수용 가능량'\n" +
                "from invenwareitem where mid = ? ;")) {
            ps.setInt(1,mid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Admin_warehouse_listVo avo = new Admin_warehouse_listVo();
                avo.setWarehouseName(rs.getString("창고 이름"));
                avo.setWarehouseStatus(rs.getString("창고 상태"));
                avo.setSectionName(rs.getString("창고 구역"));
                avo.setCurrentVol(rs.getInt("창고 현재 사용량"));
                avo.setCanvol(rs.getInt("창고 수용 가능량"));

                awlivo.add(avo);
            }
            return awlivo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Master_correspond_listVo> Master_correspond_list() {
        List<Master_correspond_listVo> mclvo = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select warehouseid as '창고 번호', warehousename as '창고 이름', customername as '등록자 이름', \n" +
                "itemid as '물품 아이디', quantity as '수량', quantity * itemprice as '총 가격'\n" +
                "from invenwareitem;")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Master_correspond_listVo mcl = new Master_correspond_listVo();
                mcl.setWarehouseID(rs.getInt("창고 번호"));
                mcl.setWarehouseName(rs.getString("창고 이름"));
                mcl.setCustomerName(rs.getString("등록자 이름"));
                mcl.setItemID(rs.getInt("물품 아이디"));
                mcl.setQuantity(rs.getInt("수량"));
                mcl.setTotalprice(rs.getInt("총 가격"));

                mclvo.add(mcl);
            }
            return mclvo;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Admin_correspond_listVo> Admin_correspond_list(int mid) {
        List<Admin_correspond_listVo> aclvo = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("" +
                "select customername as '등록자 이름', itemid as '가구 아이디',\n" +
                "quantity as '수량', quantity * itemprice as '총 가격'\n" +
                "from invenwareitem;")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Admin_correspond_listVo acl = new Admin_correspond_listVo();
                acl.setCustomerName(rs.getString("등록자 이름"));
                acl.setItemID(rs.getInt("가구 아이디"));
                acl.setQuantity(rs.getInt("수량"));
                acl.setTotalprice(rs.getInt("총 가격"));

                aclvo.add(acl);
            }
            return aclvo;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

