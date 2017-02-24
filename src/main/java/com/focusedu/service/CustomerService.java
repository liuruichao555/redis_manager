package com.focusedu.service;

import com.focusedu.base.DbResult;
import com.focusedu.model.Customer;
import com.focusedu.utils.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * CustomerService
 *
 * @author liuruichao
 * @date 15/9/14 下午3:30
 */
public class CustomerService {

    public DbResult getCustomerById(int cusId) {
        String sql = "select * from cus_customer_tbl where cus_id = ?";
        return DBHelper.query(sql, new Object[]{ cusId });
    }

    public Customer queryCustomer(int cusId, String email) throws SQLException {
        Customer customer = null;
        Connection conn = DBHelper.getConnection();
        PreparedStatement pstate = conn.prepareStatement("select * from cus_customer_tbl where cus_id = ? or email like ?");
        pstate.setInt(1, cusId);
        pstate.setString(2, "%" + email + "%");
        ResultSet rs = pstate.executeQuery();
        if (rs.next()) {
            customer = new Customer();
            customer.cusId = rs.getInt("cus_id");
            customer.email = rs.getString("email");
            customer.integral = rs.getInt("integral");
            customer.isBind = rs.getInt("is_bind");
            customer.money = rs.getInt("money");
        }

        DBHelper.closeConn(conn, pstate, rs);
        return customer;
    }

    public void updateCustomer(int cusId, double money, int integral, int isBind) throws SQLException {
        Connection conn = DBHelper.getConnection();
        PreparedStatement pstate = conn.prepareStatement("update cus_customer_tbl set " +
                "money = ?, " +
                "integral = ?, is_bind = ? where cus_id = ?");

        pstate.setDouble(1, money);
        pstate.setDouble(2, integral);
        pstate.setDouble(3, isBind);
        pstate.setDouble(4, cusId);

        pstate.execute();

        DBHelper.closeConn(conn, pstate, null);
    }

    public int updateCustomer(Map<String, Object> params) {
        StringBuffer sbu = new StringBuffer();
        sbu.append("update cus_customer_tbl set ");
        Object[] param = new Object[params.size()];
        int index = 0;
        for (String key : params.keySet()) {
            if ("cus_id".equals(key.toLowerCase())) {
                continue;
            }
            String value = (String) params.get(key);
            try {
                if (value.equals("null")) {
                    value = null;
                }
            } catch (Exception e) {
            }
            param[index++] = value;
            sbu.append(key + "=?,");
        }
        sbu.deleteCharAt(sbu.length() - 1);
        sbu.append(" where cus_id = ?");
        param[params.size() - 1] = params.get("cus_id".toUpperCase());
        System.out.println(sbu.toString());
        int count = DBHelper.update(sbu.toString(), param);
        return count;
    }
}
