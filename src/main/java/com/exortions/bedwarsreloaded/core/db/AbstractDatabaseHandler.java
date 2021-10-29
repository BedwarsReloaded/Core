package com.exortions.bedwarsreloaded.core.db;

import lombok.Data;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;

@Data
public abstract class AbstractDatabaseHandler {

    protected final String database;
    protected final String host;
    protected final String port;

    protected final String username;
    protected final String password;

    protected final String url;

    protected Connection connection;

    public AbstractDatabaseHandler(String type, String path, String database, String host, String port, String username, String password) {
        this.database = database;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        if (type.equalsIgnoreCase("mysql")) this.url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        else if (type.equalsIgnoreCase("sqlite")) this.url = "jdbc:sqlite:" + path;
        else throw new IllegalArgumentException("Database type not supported. Please view the config.yml for supported database types.");
    }

    public AbstractDatabaseHandler createConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "An error occurred while trying to connect to database (Is information correct?), User might not have permission (Using password: " + (!password.isEmpty() ? "YES" : "NO") + ")", ex);
            Bukkit.getPluginManager().disablePlugin(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("BedwarsReloaded"), "Could not get plugin 'BedwarsReloaded' from internal plugin"));
        }
        return this;
    }

    public ResultSet query(String sql) {
        try {
            return connection.prepareStatement(sql).executeQuery();
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "An error occurred while trying to query sql statement;", ex);
        }
        return null;
    }

    public boolean execute(String sql) {
        try {
            return connection.prepareStatement(sql).execute();
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "An error occurred while trying to execute sql statement;", ex);
        }
        return false;
    }


    @Override
    public String toString() {
        return "AbstractDatabaseHandler{" +
                "database='" + database + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", connection=" + connection +
                '}';
    }

}
