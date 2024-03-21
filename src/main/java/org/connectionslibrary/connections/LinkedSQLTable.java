package org.connectionslibrary.connections;



import org.connectionslibrary.ConnectionData;
import org.connectionslibrary.ConnectionsLibrary;
import org.connectionslibrary.core.DataTypes;

import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.List;

/**
 * @Author <a href="https://github.com/Cobeine">Cobeine</a>
 */

public abstract class LinkedSQLTable {
    private String name;
    public LinkedSQLTable(String name) {
        this.name = name;
    }


    public <T extends SQLObject> T load(SQLEntry where, Class<T> castClass){

        String query = "SELECT * FROM " +
                name +
                " WHERE " +
                where.getKey() + " = ?";

            SQLObject sqlObject = null;
            try (Connection connection = ConnectionsLibrary.connectionsLibrary.getConnectionData().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                try {
                    statement.setObject(1, where.getValue());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    while (resultSet.next()) {
                        Constructor<T> constructor = castClass.getDeclaredConstructor(); // Get the declared constructor
                        constructor.setAccessible(true); // Make it accessible
                        sqlObject = constructor.newInstance();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            Object columnValue = resultSet.getObject(i);
                            int columnType = metaData.getColumnType(i);

                            DataTypes accutalValue = DataTypes.getAccutalValue(columnType);
                            sqlObject.addEntry(SQLEntry.of(columnName,columnValue, accutalValue));
                        }
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
            return (T) sqlObject;

    }


    protected String generateCreateQuery(SQLEntry ... entries) {
            StringBuilder queryBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(name)
                    .append(" (");
        for (SQLEntry entry : entries) {
            queryBuilder.append(entry.getKey()).append(" ").append(entry.getDataTypes().getValue()).append(", ");
        }

        // Add primary key
        boolean primaryKeyAdded = false;
        for (SQLEntry entry : entries) {
            if (entry.isPrimary()) {
                if (!primaryKeyAdded) {
                    queryBuilder.append("PRIMARY KEY (").append(entry.getKey()).append("), ");
                    primaryKeyAdded = true;
                }
            }
        }


        if (queryBuilder.charAt(queryBuilder.length() - 2) == ',') {
            queryBuilder.setLength(queryBuilder.length() - 2);
        }

        queryBuilder.append(")");

        return queryBuilder.toString();
        }

    public void createTable(SQLEntry ... sqlEntries){
        String sql = generateCreateQuery(sqlEntries);
        System.out.println(sql);
        try (Connection connection = ConnectionsLibrary.connectionsLibrary.getConnectionData().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(SQLObject object) {
        List<SQLEntry> entries = object.getEntries();
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ")
                .append(name)
                .append(" (");
        StringBuilder valuesBuilder = new StringBuilder("VALUES (");
        for (SQLEntry entry : entries) {
            queryBuilder.append(entry.getKey()).append(", ");
            valuesBuilder.append("?, ");
        }
        queryBuilder.setLength(queryBuilder.length() - 2); // Remove the last comma and space
        valuesBuilder.setLength(valuesBuilder.length() - 2); // Remove the last comma and space
        queryBuilder.append(") ");
        valuesBuilder.append(") ");
        queryBuilder.append(valuesBuilder);

        // Append ON DUPLICATE KEY UPDATE clause
        queryBuilder.append("ON DUPLICATE KEY UPDATE ");
        for (SQLEntry entry : entries) {
            queryBuilder.append(entry.getKey()).append(" = VALUES(").append(entry.getKey()).append("), ");
        }
        queryBuilder.setLength(queryBuilder.length() - 2); // Remove the last comma and space

        String query = queryBuilder.toString();

        try (Connection connection = ConnectionsLibrary.connectionsLibrary.getConnectionData().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            int i = 1;
            for (SQLEntry entry : entries) {
                statement.setObject(i++, entry.getValue());
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
