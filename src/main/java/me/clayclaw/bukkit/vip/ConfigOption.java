package me.clayclaw.bukkit.vip;

import java.util.HashMap;
import java.util.List;

public class ConfigOption {

    private String language;
    private boolean uuidMode;
    private String saving;
    private String dateFormat;

    private String mySQLHost;
    private String mySQLPort;
    private String mySQLDatabase;
    private String mySQLUsername;
    private String mySQLPassword;

    private int redeemCodeLength;
    private String redeemCodeInvolved;

    private String vipDefaultGroup;
    private String vipGroupPlaceholder;
    private String vipCmdMoveGroup;
    private String vipCmdAddPerm;
    private String vipCmdDelPerm;
    private List<String> vipCmdOnActivate;
    private List<String> vipCmdOnExpire;

    private HashMap<String, VIPGroupOption> groupOption;
    private HashMap<String, KeyOption> keyOption;

    public ConfigOption() {
        setLanguage(ClawVIP.getInstance().getConfig().getString("Option.Language"));
        setUUIDMode(ClawVIP.getInstance().getConfig().getBoolean("Option.UUIDMode"));
        setSaving(ClawVIP.getInstance().getConfig().getString("Option.Saving"));
        setDateFormat(ClawVIP.getInstance().getConfig().getString("Option.DateFormat"));
        setMySQLHost(ClawVIP.getInstance().getConfig().getString("MySQL.host"));
        setMySQLPort(ClawVIP.getInstance().getConfig().getString("MySQL.port"));
        setMySQLDatabase(ClawVIP.getInstance().getConfig().getString("MySQL.database"));
        setMySQLUsername(ClawVIP.getInstance().getConfig().getString("MySQL.username"));
        setMySQLPassword(ClawVIP.getInstance().getConfig().getString("MySQL.password"));
        setRedeemCodeLength(ClawVIP.getInstance().getConfig().getInt("RedeemCode.Length"));
        setRedeemCodeInvolved(ClawVIP.getInstance().getConfig().getString("RedeemCode.Involved"));
        setVipDefaultGroup(ClawVIP.getInstance().getConfig().getString("VIP.DefaultGroup"));
        setVipGroupPlaceholder(ClawVIP.getInstance().getConfig().getString("VIP.GroupPlaceholder"));
        setVipCmdMoveGroup(ClawVIP.getInstance().getConfig().getString("VIP.Command.MoveGroup"));
        setVipCmdAddPerm(ClawVIP.getInstance().getConfig().getString("VIP.Command.AddPerm"));
        setVipCmdDelPerm(ClawVIP.getInstance().getConfig().getString("VIP.Command.DelPerm"));
        setVipCmdOnActivate(ClawVIP.getInstance().getConfig().getStringList("VIP.Command.OnActivate"));
        setVipCmdOnExpire(ClawVIP.getInstance().getConfig().getStringList("VIP.Command.OnExpire"));
        groupOption = new HashMap<>();
        keyOption = new HashMap<>();
        ClawVIP.getInstance().getConfig().getConfigurationSection("VIP.Group").getKeys(false).forEach(s -> {
            VIPGroupOption go = new VIPGroupOption();
            go.setFriendlyName(ClawVIP.getInstance().getConfig().getString("VIP.Group." + s + ".FriendlyName"));
            go.setMoveGroup(ClawVIP.getInstance().getConfig().getBoolean("VIP.Group." + s + ".MoveGroup"));
            go.setPermGroup(ClawVIP.getInstance().getConfig().getString("VIP.Group+." + s + ".PermGroup"));
            go.setExtraPerm(ClawVIP.getInstance().getConfig().getStringList("VIP.Group." + s + ".ExtraPerm"));
            go.setPriority(ClawVIP.getInstance().getConfig().getInt("VIP.Group." + s + ".Priority"));
            groupOption.put(s, go);
        });
        ClawVIP.getInstance().getConfig().getConfigurationSection("RedeemCode.Gifts")
                .getKeys(false).forEach(s -> {
                    KeyOption ko = new KeyOption();
                    ko.setVipGroup(
                            ClawVIP.getInstance().getConfig().getString("RedeemCode.Gifts." + s + ".VIPGroup"));
                    ko.setDays(ClawVIP.getInstance().getConfig().getInt("RedeemCode.Gifts."+s+".days"));
                    keyOption.put(s, ko);
                });
    }

    public HashMap<String, KeyOption> getKeyOption() {
        return keyOption;
    }

    public HashMap<String, VIPGroupOption> getGroupOption() {
        return groupOption;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setVipCmdOnExpire(List<String> vipCmdOnExpire) {
        this.vipCmdOnExpire = vipCmdOnExpire;
    }

    public List<String> getVipCmdOnExpire() {
        return vipCmdOnExpire;
    }

    public void setVipCmdOnActivate(List<String> vipCmdOnActivate) {
        this.vipCmdOnActivate = vipCmdOnActivate;
    }

    public List<String> getVipCmdOnActivate() {
        return vipCmdOnActivate;
    }

    public void setVipCmdDelPerm(String vipCmdDelPerm) {
        this.vipCmdDelPerm = vipCmdDelPerm;
    }

    public String getVipCmdDelPerm() {
        return vipCmdDelPerm;
    }

    public String getVipCmdAddPerm() {
        return vipCmdAddPerm;
    }

    public void setVipCmdAddPerm(String vipCmdAddPerm) {
        this.vipCmdAddPerm = vipCmdAddPerm;
    }

    public void setVipCmdMoveGroup(String vipCmdMoveGroup) {
        this.vipCmdMoveGroup = vipCmdMoveGroup;
    }

    public String getVipCmdMoveGroup() {
        return vipCmdMoveGroup;
    }

    public String getVipGroupPlaceholder() {
        return vipGroupPlaceholder;
    }

    public void setVipGroupPlaceholder(String vipGroupPlaceholder) {
        this.vipGroupPlaceholder = vipGroupPlaceholder;
    }

    public String getVipDefaultGroup() {
        return vipDefaultGroup;
    }

    public void setVipDefaultGroup(String vipDefaultGroup) {
        this.vipDefaultGroup = vipDefaultGroup;
    }

    public void setRedeemCodeInvolved(String redeemCodeInvolved) {
        this.redeemCodeInvolved = redeemCodeInvolved;
    }

    public String getRedeemCodeInvolved() {
        return redeemCodeInvolved;
    }

    public void setRedeemCodeLength(int redeemCodeLength) {
        this.redeemCodeLength = redeemCodeLength;
    }

    public int getRedeemCodeLength() {
        return redeemCodeLength;
    }

    public String getSaving() {
        return saving;
    }

    public void setSaving(String saving) {
        this.saving = saving;
    }

    public boolean isUUIDMode() {
        return uuidMode;
    }

    public void setUUIDMode(boolean uuidMode) {
        this.uuidMode = uuidMode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setMySQLPassword(String mySQLPassword) {
        this.mySQLPassword = mySQLPassword;
    }

    public String getMySQLPassword() {
        return mySQLPassword;
    }

    public void setMySQLUsername(String mySQLUsername) {
        this.mySQLUsername = mySQLUsername;
    }

    public String getMySQLUsername() {
        return mySQLUsername;
    }

    public String getMySQLDatabase() {
        return mySQLDatabase;
    }

    public void setMySQLDatabase(String mySQLDatabase) {
        this.mySQLDatabase = mySQLDatabase;
    }

    public String getMySQLPort() {
        return mySQLPort;
    }

    public void setMySQLPort(String mySQLPort) {
        this.mySQLPort = mySQLPort;
    }

    public void setMySQLHost(String mySQLHost) {
        this.mySQLHost = mySQLHost;
    }

    public String getMySQLHost() {
        return mySQLHost;
    }

    public class VIPGroupOption {

        private String friendlyName;
        private boolean moveGroup;
        private String permGroup;
        private List<String> extraPerm;
        private int priority;

        public String getFriendlyName() {
            return friendlyName;
        }

        public void setFriendlyName(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public boolean isMoveGroup() {
            return moveGroup;
        }

        public void setMoveGroup(boolean moveGroup) {
            this.moveGroup = moveGroup;
        }

        public String getPermGroup() {
            return permGroup;
        }

        public void setPermGroup(String permGroup) {
            this.permGroup = permGroup;
        }

        public List<String> getExtraPerm() {
            return extraPerm;
        }

        public void setExtraPerm(List<String> extraPerm) {
            this.extraPerm = extraPerm;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

    }

    public class KeyOption {

        private String vipGroup;
        private int days;

        public String getVipGroup() {
            return vipGroup;
        }

        public void setVipGroup(String vipGroup) {
            this.vipGroup = vipGroup;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }
    }

}
