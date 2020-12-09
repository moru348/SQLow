package me.moru3;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {

    private List<Map<String, Object>> result;
    private List<String> columnNames;
    private int nowLow = -1;

    public Result(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        for(int i = 1; i<=rsMetaData.getColumnCount(); i++) {
            columnNames.add(rsMetaData.getColumnName(i));
        }
        while(rs.next()) {
            Map<String, Object> temp = new HashMap<>();
            for(String label : columnNames) {
                temp.put(label, rs.getObject(label));
            }
            result.add(temp);
        }
    }

    public boolean next() {
        nowLow++;
        return nowLow != result.size();
    }

    public int size() {
        return result.size();
    }

    public Map<String, Object> getRow(int index) {
        return result.get(index);
    }

    public String getString(int index) {
        return result.get(nowLow).get(result.get(nowLow).keySet().toArray()[index].toString()).toString();
    }

    public String getString(String key) {
        return result.get(nowLow).get(result.get(nowLow).get(key).toString()).toString();
    }

    public int getInt(String key) {
        return Integer.parseInt(result.get(nowLow).get(result.get(nowLow).get(key).toString()).toString());
    }

    public AbstractMap.SimpleEntry<Class<?>, Object> getConvertValue(int index, DataType<?> type) {
        return new AbstractMap.SimpleEntry<>(type.toClass(), type.getConvR().apply(String.valueOf(result.get(nowLow).get(result.get(nowLow).keySet().toArray()[index].toString()))));
    }

    public AbstractMap.SimpleEntry<Class<?>, Object> getConvertValue(String key, DataType<?> type) {
        return new AbstractMap.SimpleEntry<>(type.toClass(), type.getConvR().apply(result.get(nowLow).get(result.get(nowLow).get(key).toString())));
    }
}