package com.example.d3.tools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangchao
 * @Description:
 * @date 2023/9/6 9:51
 * @vVersion 1.0
 */
public class CollectionTools {
    /**
     * 将查询结果resultset转换为List<Map>
     */

    public static List<Map> convertList(ResultSet rs) throws SQLException {
        List<Map> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取列的数量
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }
}
