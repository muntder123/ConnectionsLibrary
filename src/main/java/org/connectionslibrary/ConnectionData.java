package org.connectionslibrary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.connectionslibrary.core.DataTypes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConnectionData {

    private HikariDataSource dataSource;


    public void connect(String host,String port,String user,String password,String database){
        HikariConfig config = new HikariConfig();
        config.setPoolName("by m_iq - MySQL Connection Pool");
        config.setMaximumPoolSize(10);
        config.setMaxLifetime(1800000);
        config.addDataSourceProperty("characterEncoding", "utf8");
        config.addDataSourceProperty("encoding", "UTF-8");
        config.addDataSourceProperty("useSSL", "false");
        config.addDataSourceProperty("useUnicode", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("jdbcCompliantTruncation", "false");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "300");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("socketTimeout", "30000");
        Properties properties = new Properties();
        properties.put("serverName", host);
        properties.put("port", port);
        properties.put("databaseName", database);
        properties.put("user", user);
        properties.put("password", password);


        config.setJdbcUrl("jdbc:mysql://" +
                host + ":" + port + "/" +
                database + "?user=" + user
                + "&password=" + password);


        config.setDataSourceProperties(properties);
        this.dataSource = new HikariDataSource(config);
    }

    public void close() {
        if (isConnected()) dataSource.close();
    }
    public boolean isConnected() {
        return (dataSource != null && !dataSource.isClosed());
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }




}
