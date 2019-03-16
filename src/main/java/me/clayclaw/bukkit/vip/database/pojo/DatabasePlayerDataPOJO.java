package me.clayclaw.bukkit.vip.database.pojo;

import java.sql.Date;

public class DatabasePlayerDataPOJO {

    private String playerId;
    private String vipGroup;
    private Date dueDate;
    private String originalGroup;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setVipGroup(String vipGroup) {
        this.vipGroup = vipGroup;
    }

    public String getVipGroup() {
        return vipGroup;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getOriginalGroup() {
        return originalGroup;
    }

    public void setOriginalGroup(String defaultGroup) {
        this.originalGroup = defaultGroup;
    }
}
