package pl.grzegorz2047.openguild2047.database;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Created by grzeg on 13.08.2017.
 */
public class MySQLImplementationStrategy implements SQLImplementationStrategy {


    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String dbName;

    public MySQLImplementationStrategy(String host, int port, String user, String password, String dbName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
    }

    @Override
    public HikariDataSource getDataSource() {
        HikariDataSource hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", dbName);
        hikari.addDataSourceProperty("user", user);
        hikari.addDataSourceProperty("password", password);
        hikari.addDataSourceProperty("cachePrepStmts", true);
        hikari.addDataSourceProperty("prepStmtCacheSize", 250);
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        return hikari;
    }

}
