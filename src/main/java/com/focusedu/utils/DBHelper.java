package com.focusedu.utils;

import com.focusedu.base.DbResult;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * DBHelper
 *
 * @author liuruichao
 * @date 15/9/6 下午1:26
 */
public final class DBHelper {
    private static final String driverClass;
    private static final String url;
    private static final String username;
    private static final String password;

    static {
        Properties props = new Properties();
        try {
            props.load(DBHelper.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverClass = props.getProperty("driverClass");
        url = props.getProperty("jdbcUrl");
        username = props.getProperty("user");
        password = props.getProperty("password");
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConn(Connection conn, Statement state, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (state != null) state.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Object save(String sql, Object[] params) {
        Object key = null;

        Connection conn = null;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstate = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            setParams(pstate, params);
            pstate.execute();
            rs = pstate.getGeneratedKeys();
            if (rs.next()) {
                key = rs.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstate, rs);
        }
        return key;
    }

    public static DbResult query(String sql, Object[] params) {
        DbResult result = null;

        Connection conn = null;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstate = conn.prepareStatement(sql);
            setParams(pstate, params);
            rs = pstate.executeQuery();
            result = new DbResult(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstate, rs);
        }

        return result;
    }

    public static int update(String sql, Object[] params) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstate = conn.prepareStatement(sql);
            setParams(pstate, params);
            count = pstate.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstate, rs);
        }
        return count;
    }

    public static void execute(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement pstate = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstate = conn.prepareStatement(sql);
            setParams(pstate, params);
            pstate.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn, pstate, rs);
        }
    }

    private static void setParams(PreparedStatement pstate, Object[] params) throws SQLException {
        if (pstate != null && params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                pstate.setObject(i + 1, params[i]);
            }
        }
    }
}
