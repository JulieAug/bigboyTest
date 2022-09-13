package bigBoyUtils;

/**
 * @author:zhuli
 * @description
 * @date 2019/9/17
 */
import com.mysql.jdbc.Statement;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DBUtil {
    public DBUtil() {
    }
    
    public static void update(String sql, String propertyName, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = JdbcTool.getConnection(propertyName);
            preparedStatement = connection.prepareStatement(sql);

            for(int i = 0; i < args.length; ++i) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            preparedStatement.executeUpdate();
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            JdbcTool.releaseDB((ResultSet)null, (Statement)preparedStatement, connection);
        }

    }

    public <T> T get(Class<T> clazz, String sql, String propertyName, Object... args) {
        List<T> result = this.getForList(clazz, sql, propertyName, args);
        return result.size() > 0 ? result.get(0) : null;
    }

    public <T> List<T> getForList(Class<T> clazz, String sql, String propertyName, Object... args) {
        List<T> list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcTool.getConnection(propertyName);
            preparedStatement = connection.prepareStatement(sql);

            for(int i = 0; i < args.length; ++i) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            List<Map<String, Object>> values = this.handleResultSetToMapList(resultSet);
            list = this.transfterMapListToBeanList(clazz, values);
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            JdbcTool.releaseDB(resultSet, (Statement)preparedStatement, connection);
        }

        return (List)list;
    }

    public <T> List<T> transfterMapListToBeanList(Class<T> clazz, List<Map<String, Object>> values) throws Exception {
        List<T> result = new ArrayList();
        T bean = null;
        if (values.size() > 0) {
            Iterator var5 = values.iterator();

            while(var5.hasNext()) {
                Map<String, Object> m = (Map)var5.next();
                bean = clazz.newInstance();
                Iterator var7 = m.entrySet().iterator();

                while(var7.hasNext()) {
                    Entry<String, Object> entry = (Entry)var7.next();
                    String propertyName = (String)entry.getKey();
                    Object value = entry.getValue();
                    Field f = bean.getClass().getDeclaredField(propertyName);
                    f.setAccessible(true);
                    f.set(bean, value);
                }

                result.add(bean);
            }
        }

        return result;
    }

    public List<Map<String, Object>> handleResultSetToMapList(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> values = new ArrayList();
        List<String> columnLabels = this.getColumnLabels(resultSet);
        HashMap map = null;

        while(resultSet.next()) {
            map = new HashMap();
            Iterator var5 = columnLabels.iterator();

            while(var5.hasNext()) {
                String columnLabel = (String)var5.next();
                Object value = resultSet.getObject(columnLabel);
                map.put(columnLabel, value);
            }

            values.add(map);
        }

        return values;
    }

    private List<String> getColumnLabels(ResultSet rs) throws SQLException {
        List<String> labels = new ArrayList();
        ResultSetMetaData rsmd = rs.getMetaData();

        for(int i = 0; i < rsmd.getColumnCount(); ++i) {
            labels.add(rsmd.getColumnLabel(i + 1));
        }

        return labels;
    }

    public static <E> E getForValue(String sql, String propertyName, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcTool.getConnection(propertyName);
            preparedStatement = connection.prepareStatement(sql);

            for(int i = 0; i < args.length; ++i) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Object var12 = resultSet.getObject(1);
                return (E) var12;
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            JdbcTool.releaseDB(resultSet, (Statement)preparedStatement, connection);
        }

        return null;
    }
}
