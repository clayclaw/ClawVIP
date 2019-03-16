package me.clayclaw.bukkit.vip.database;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.database.pojo.DatabaseKeyDataPOJO;
import me.clayclaw.bukkit.vip.database.pojo.DatabasePlayerDataPOJO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;

public class MySQLDatabase implements IDatabase {

    private Connection connection;

    @Override
    public void enable() {
        if (!isConnected()) {
            String host = ClawVIP.getConfigOption().getMySQLHost();
            String port = ClawVIP.getConfigOption().getMySQLPort();
            String database = ClawVIP.getConfigOption().getMySQLDatabase();
            String username = ClawVIP.getConfigOption().getMySQLUsername();
            String password = ClawVIP.getConfigOption().getMySQLPassword();
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                Bukkit.getLogger().info(ChatColor.GREEN + "[ClawVIP] 正在开启对SQL的连接");
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getLogger().info(ChatColor.RED + "[ClawVIP] 开启SQL连接失败");
            }
        }
        Bukkit.getScheduler().runTaskLater(ClawVIP.getInstance(), () -> {
            try {
                PreparedStatement ps1 = getConnection().prepareStatement(
                        "CREATE TABLE IF NOT EXISTS " + DatabaseService.DB_PLAYERDATA_TABLE_NAME +
                                " (Id VARCHAR(100),DueDate DATETIME,GroupName VARCHAR(100),OriginalGroup VARCHAR(100)" +
                                ",PRIMARY KEY (Id))");
                ps1.executeUpdate();
                PreparedStatement ps2 = getConnection().prepareStatement(
                        "CREATE TABLE IF NOT EXISTS " + DatabaseService.DB_KEYDATA_TABLE_NAME +
                                " (VKey VARCHAR(100),KeyGroup VARCHAR(100),PRIMARY KEY (VKey))");
                ps2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 1);

    }

    @Override
    public void disable() {
        if (isConnected()) {
            try {
                connection.close();
                Bukkit.getLogger().info(ChatColor.GREEN + "[ClawVIP] 正在关闭对SQL的连接");
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getLogger().info(ChatColor.RED + "[ClawVIP] 关闭SQL连接失败");
            }
        }
    }

    @Override
    public Object extractData(Class<?> targetClass, String key) {
        if (!isConnected()) return null;
        if (targetClass.isAssignableFrom(DatabasePlayerDataPOJO.class)) {
            try {
                DatabasePlayerDataPOJO pojo = new DatabasePlayerDataPOJO();
                PreparedStatement ps = getConnection().prepareStatement(
                        "SELECT * FROM " + DatabaseService.DB_PLAYERDATA_TABLE_NAME + " WHERE Id = ?");
                ps.setString(1, key);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    pojo.setPlayerId(rs.getString("Id"));
                    pojo.setDueDate(rs.getDate("DueDate"));
                    pojo.setVipGroup(rs.getString("GroupName"));
                    pojo.setOriginalGroup(rs.getString("OriginalGroup"));
                    return pojo;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (targetClass.isAssignableFrom(DatabaseKeyDataPOJO.class)) {
            try {
                DatabaseKeyDataPOJO pojo = new DatabaseKeyDataPOJO();
                PreparedStatement ps = getConnection().prepareStatement(
                        "SELECT * FROM " + DatabaseService.DB_KEYDATA_TABLE_NAME + " WHERE VKey = ?");
                ps.setString(1, key);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    pojo.setKey(rs.getString("VKey"));
                    pojo.setKeyGroup(rs.getString("KeyGroup"));
                    return pojo;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void insertData(Object object) {
        if (!isConnected()) return;
        if (object instanceof DatabasePlayerDataPOJO) {
            try {
                DatabasePlayerDataPOJO pojo = (DatabasePlayerDataPOJO) object;
                PreparedStatement ps =
                        getConnection().prepareStatement(
                                "REPLACE INTO " + DatabaseService.DB_PLAYERDATA_TABLE_NAME +
                                        " (Id,DueDate,GroupName,OriginalGroup) VALUES (?,?,?,?)");
                ps.setString(1, pojo.getPlayerId());
                ps.setDate(2, pojo.getDueDate());
                ps.setString(3, pojo.getVipGroup());
                ps.setString(4, pojo.getOriginalGroup());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (object instanceof DatabaseKeyDataPOJO) {
            try {
                DatabaseKeyDataPOJO pojo = (DatabaseKeyDataPOJO) object;
                PreparedStatement ps =
                        getConnection().prepareStatement(
                                "INSERT IGNORE INTO " + DatabaseService.DB_KEYDATA_TABLE_NAME +
                                        " (VKey,KeyGroup) VALUES (?,?)");
                ps.setString(1, pojo.getKey());
                ps.setString(2, pojo.getKeyGroup());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeData(Class<?> targetClass, String key) {
        if (targetClass.isAssignableFrom(DatabasePlayerDataPOJO.class)) {
            try {
                PreparedStatement ps = getConnection().prepareStatement(
                        "DELETE FROM " + DatabaseService.DB_PLAYERDATA_TABLE_NAME + " WHERE Id = ?");
                ps.setString(1, key);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (targetClass.isAssignableFrom(DatabaseKeyDataPOJO.class)) {
            try {
                PreparedStatement ps = getConnection().prepareStatement(
                        "DELETE FROM " + DatabaseService.DB_KEYDATA_TABLE_NAME + " WHERE VKey = ?");
                ps.setString(1, key);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }
}
