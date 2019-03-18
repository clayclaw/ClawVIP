package me.clayclaw.bukkit.vip.database;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.IService;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class DatabaseService implements IService {

    public static final String DB_PLAYERDATA_TABLE_NAME = "VIPlayers";
    public static final String DB_KEYDATA_TABLE_NAME = "VIPKeys";

    private IDatabase database;
    private ArrayList<Callable> callList;

    @Override
    public void enable() {
        callList = new ArrayList<>();
        try {
            database = DatabaseType.valueOf(ClawVIP.getConfigOption().getSaving().toUpperCase())
                    .targetClass.newInstance();
            database.enable();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {
        callList.forEach(callable -> {
            try {
                callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        database.disable();
    }

    public IDatabase getDatabase() {
        return database;
    }

    public void addCallable(Callable callable){
        callList.add(callable);
    }

    enum DatabaseType {

        MYSQL(MySQLDatabase.class),
        YAML(YamlDatabase.class);

        Class<? extends IDatabase> targetClass;

        DatabaseType(Class<? extends IDatabase> targetClass) {
            this.targetClass = targetClass;
        }

    }

}
