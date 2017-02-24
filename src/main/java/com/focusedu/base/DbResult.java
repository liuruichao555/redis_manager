package com.focusedu.base;

import org.junit.experimental.max.CouldNotReadCoreException;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * DbResult
 *
 * @author liuruichao
 * @date 15/9/16 上午11:23
 */
public class DbResult implements Serializable {
    private int curIndex = -1;
    private int count = 0;
    private List<List<Object>> listValues;
    private List<Map<String, Object>> mapValues;
    private List<String> columnNames;

    public DbResult(ResultSet rs) throws SQLException {
        int totalColumn = rs.getMetaData().getColumnCount();
        columnNames = new ArrayList<>(totalColumn);
        listValues = new ArrayList<>();
        mapValues = new ArrayList<>();
        boolean isFlag = true;
        while (rs.next()) {
            // index访问
            List<Object> list = new ArrayList<>(totalColumn);
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <= totalColumn; i++) {
                list.add(rs.getObject(i));
                map.put(rs.getMetaData().getColumnName(i).toLowerCase(), rs.getObject(i));
                if (isFlag) {
                    columnNames.add(rs.getMetaData().getColumnName(i));
                }
            }
            isFlag = false;
            listValues.add(list);
            mapValues.add(map);
        }
        count = listValues.size();
    }

    public Object get(int columnIndex) {
        if (curIndex < 0 || curIndex >= count) {
            throw new IndexOutOfBoundsException("index error. count : " + count + ", curIndex : " + curIndex);
        }
        List<Object> values = listValues.get(curIndex);
        return values.get(columnIndex);
    }

    public Object get(String columnName) {
        if (curIndex < 0 || curIndex >= count) {
            throw new IndexOutOfBoundsException("index error. count : " + count + ", curIndex : " + curIndex);
        }
        Map<String, Object> map = mapValues.get(curIndex);
        return map.get(columnName.toLowerCase());
    }

    public boolean next() {
        if (++curIndex >= count) {
            --curIndex;
            return false;
        }
        return true;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
