package me.clayclaw.bukkit.vip.database;

public interface IDatabase {

    void enable();

    void disable();

    Object extractData(Class<?> targetClass, String key);

    void insertData(Object object);

    void removeData(Class<?> targetClass, String key);

}
