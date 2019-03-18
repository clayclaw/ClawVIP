package me.clayclaw.bukkit.vip.database;

import me.clayclaw.bukkit.vip.BuiltinMessage;
import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.database.pojo.DatabaseKeyDataPOJO;
import me.clayclaw.bukkit.vip.database.pojo.DatabasePlayerDataPOJO;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class YamlDatabase implements IDatabase {

    private final String KEYDATA_FOLDER = "keydata";
    private final String PLAYERDATA_FOLDER = "playerdata";

    private YamlFileController controller;

    @Override
    public void enable() {
        this.controller = new YamlFileController();
    }

    @Override
    public void disable() {}

    @Override
    public Object extractData(Class<?> targetClass, String key) {
        if (targetClass.isAssignableFrom(DatabasePlayerDataPOJO.class)) {
            DatabasePlayerDataPOJO pojo = new DatabasePlayerDataPOJO();
            controller.loadConfig(PLAYERDATA_FOLDER, key);
            pojo.setPlayerId(controller.getConfig(PLAYERDATA_FOLDER, key).getString("Id"));
            pojo.setOriginalGroup(controller.getConfig(PLAYERDATA_FOLDER, key).getString("OriginalGroup"));
            pojo.setVipGroup(controller.getConfig(PLAYERDATA_FOLDER, key).getString("GroupName"));
            try {
                pojo.setDueDate((controller.getConfig(PLAYERDATA_FOLDER, key).getString("DueDate") == null) ?
                        null : new java.sql.Date(new SimpleDateFormat(ClawVIP.getConfigOption().getDateFormat()).parse(
                        controller.getConfig(PLAYERDATA_FOLDER, key).getString("DueDate")).getTime()));
            } catch (ParseException e) {
                Bukkit.getLogger().warning(BuiltinMessage.getMessage("COVERTERROR")+key);
            }
            return pojo;
        } else if (targetClass.isAssignableFrom(DatabaseKeyDataPOJO.class)) {
            DatabaseKeyDataPOJO pojo = new DatabaseKeyDataPOJO();
            controller.loadConfig(KEYDATA_FOLDER, key);
            pojo.setKey(controller.getConfig(KEYDATA_FOLDER, key).getString("key"));
            pojo.setKeyGroup(controller.getConfig(KEYDATA_FOLDER, key).getString("keyGroup"));
            return pojo;
        }
        return null;
    }

    @Override
    public void insertData(Object object) {
        HashMap<String, Object> objMap = new HashMap<>();
        if (object.getClass().isAssignableFrom(DatabasePlayerDataPOJO.class)) {
            DatabasePlayerDataPOJO pojo = (DatabasePlayerDataPOJO) object;
            objMap.put("Id", pojo.getPlayerId());
            objMap.put("OriginalGroup", pojo.getOriginalGroup());
            objMap.put("GroupName", pojo.getVipGroup());
            objMap.put("DueDate", (pojo.getDueDate() == null)
                    ? null : new SimpleDateFormat(ClawVIP.getConfigOption().getDateFormat()).format(pojo.getDueDate()));
            controller.insertToConfig(PLAYERDATA_FOLDER, pojo.getPlayerId(), objMap);
        } else if (object.getClass().isAssignableFrom(DatabaseKeyDataPOJO.class)) {
            DatabaseKeyDataPOJO pojo = (DatabaseKeyDataPOJO) object;
            objMap.put("key", pojo.getKey());
            objMap.put("keyGroup", pojo.getKeyGroup());
            controller.insertToConfig(KEYDATA_FOLDER, pojo.getKey(), objMap);
        }
    }

    @Override
    public void removeData(Class<?> targetClass, String key) {
        if (targetClass.isAssignableFrom(DatabasePlayerDataPOJO.class)) {
            controller.removeFile(PLAYERDATA_FOLDER, key);
        } else if (targetClass.isAssignableFrom(DatabaseKeyDataPOJO.class)) {
            controller.removeFile(KEYDATA_FOLDER, key);
        }
    }

    private class YamlFileController {

        public YamlFileController(){}

        public boolean exists(String folder, String fileId){
            File target = new File("plugins"+File.separator+"ClawVIP"+
                    File.separator+ folder + File.separator, fileId + ".yml");
            return target.exists();
        }
        private void createNewFile(String folder, String fileId){
            if(exists(folder, fileId)) return;

            new File("plugins"+File.separator+"ClawVIP"+File.separator+folder).mkdir();

            File target = new File("plugins"+File.separator+"ClawVIP"+
                    File.separator+ folder + File.separator, fileId + ".yml");

            try {
                target.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public File getFile(String folder, String fileId){
            createNewFile(folder, fileId);
            return new File("plugins"+File.separator+"ClawVIP"+
                    File.separator+ folder + File.separator, fileId + ".yml");
        }
        public void removeFile(String folder, String fileId){
            if(!exists(folder, fileId)) return;
            getFile(folder, fileId).delete();
        }
        public FileConfiguration getConfig(String folder, String fileId){
            return YamlConfiguration.loadConfiguration(getFile(folder, fileId));
        }
        public void insertToConfig(String folder, String fileId, Map<String, Object> objMap){
            FileConfiguration config = getConfig(folder, fileId);
            objMap.forEach((key, value) -> config.set(key, value));
            try {
                config.save(getFile(folder, fileId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void loadConfig(String folder, String fileId){
            try {
                getConfig(folder, fileId).load(getFile(folder, fileId));
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        public void saveConfig(String folder, String fileId){
            try {
                getConfig(folder, fileId).save(getFile(folder, fileId));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
