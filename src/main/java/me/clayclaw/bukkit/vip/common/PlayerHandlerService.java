package me.clayclaw.bukkit.vip.common;

import me.clayclaw.bukkit.vip.ClawLib;
import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.ConfigOption;
import me.clayclaw.bukkit.vip.IService;
import me.clayclaw.bukkit.vip.database.DatabaseService;
import me.clayclaw.bukkit.vip.database.pojo.DatabasePlayerDataPOJO;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerHandlerService implements IService {

    private DatabaseService dbService;
    private PlayerEventListener listener;
    private HashMap<Player, VIPlayer> playerHandler;
    private BukkitTask controller;

    @Override
    public void enable() {
        dbService = (DatabaseService) ClawVIP.getInstance().getServiceManager().getService(DatabaseService.class);
        playerHandler = new HashMap<>();
        initTaskTimer();
        Bukkit.getPluginManager().registerEvents(listener = new PlayerEventListener(), ClawVIP.getInstance());
    }

    @Override
    public void disable() {

        dbService.addCallable(() -> {
            controller.cancel();
            playerHandler.values().forEach(VIPlayer::unregister);
            playerHandler.clear();
            return true;
        });

    }

    private void initTaskTimer(){
        controller = Bukkit.getScheduler().runTaskTimer(ClawVIP.getInstance(), () -> {
            playerHandler.values().stream()
                    .filter(vp -> !Objects.isNull(vp.getDueDate()))
                    .filter(vp -> new Date().after(vp.getDueDate()))
                    .forEach(this::removeVIP);
        }, 1, 1);
    }

    public boolean updateVIP(Player player, String targetGroup, boolean isPermanent, int dayAmount){

        VIPlayer vp = getVIPlayer(player);
        ConfigOption.VIPGroupOption go = ClawVIP.getConfigOption().getGroupOption().get(targetGroup);

        vp.setOriginalGroup(
                ClawVIP.getPAPIConvertedString(ClawVIP.getConfigOption().getVipDefaultGroup(), vp.getPlayer()));

        if(Objects.isNull(vp.getGroupName())){
            vp.setGroupName(targetGroup);
        }else{
            int priority = ClawVIP.getConfigOption().getGroupOption().get(vp.getGroupName()).getPriority();
            if(priority <= go.getPriority()){
                vp.setGroupName(targetGroup);
            }else{
                player.sendMessage(ClawVIP.getLanguageString("LowPriority"));
                return false;
            }
        }

        if (isPermanent){
            vp.setDueDate(null);
        } else if(vp.getDueDate() == null){
            vp.setDueDate(DateUtils.addDays(new Date(), dayAmount));
        } else {
            vp.setDueDate(DateUtils.addDays(vp.getDueDate(), dayAmount));
        }

        if(go.isMoveGroup()){
            String moveGroup = ClawLib.replaceIfExists(ClawLib.replaceIfExists(
                    ClawVIP.getConfigOption().getVipCmdMoveGroup(),
                    "%player%", vp.getPlayer().getName()),
                    "%group%", go.getPermGroup());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), moveGroup);
        }

        go.getExtraPerm().forEach(permS ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ClawLib.replaceIfExists(ClawLib.replaceIfExists(
                ClawVIP.getConfigOption().getVipCmdAddPerm(),
                "%player%", vp.getPlayer().getName()),
                "%perm%", permS)));

        ClawVIP.getPAPIConvertedStringList(ClawVIP.getConfigOption().getVipCmdOnActivate(), vp.getPlayer())
                .forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s));

        return true;
    }
    public boolean isVIP(Player player){
        return getVIPlayer(player).getGroupName() != null;
    }
    public Set<Player> getVIPOnline(){
        return playerHandler.keySet().stream().filter(this::isVIP).collect(Collectors.toSet());
    }
    public void removeVIP(VIPlayer vp){

        if(!isVIP(vp.getPlayer())) return;

        ClawVIP.getConfigOption().getGroupOption().get(vp.getGroupName()).getExtraPerm().forEach(permS ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ClawLib.replaceIfExists(ClawLib.replaceIfExists(
                        ClawVIP.getConfigOption().getVipCmdDelPerm(),
                        "%player%", vp.getPlayer().getName()),
                        "%perm%", permS)));

        if(!ClawVIP.getConfigOption().getVipDefaultGroup().equals("#nochange")){
            String moveGroup = ClawLib.replaceIfExists(ClawLib.replaceIfExists(
                    ClawVIP.getConfigOption().getVipCmdMoveGroup(),
                    "%player%", vp.getPlayer().getName()),
                    "%group%", ClawVIP.getConfigOption().getVipDefaultGroup());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), moveGroup);
        }else if(!Objects.isNull(vp.getOriginalGroup())){
            String moveGroup = ClawLib.replaceIfExists(ClawLib.replaceIfExists(
                    ClawVIP.getConfigOption().getVipCmdMoveGroup(),
                    "%player%", vp.getPlayer().getName()),
                    "%group%", vp.getOriginalGroup());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), moveGroup);
        }

        ClawVIP.getPAPIConvertedStringList(ClawVIP.getConfigOption().getVipCmdOnExpire(), vp.getPlayer())
                .forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s));

        vp.setDueDate(null);
        vp.setGroupName(null);

    }

    public VIPlayer getVIPlayer(Player player) {
        return playerHandler.get(player);
    }

    public class VIPlayer {

        private Player player;
        private Date dueDate;
        private String groupName;
        private String originalGroup;

        public VIPlayer(Player player) {
            this.player = player;
            DatabasePlayerDataPOJO pojo = (DatabasePlayerDataPOJO) dbService.getDatabase().extractData(
                    DatabasePlayerDataPOJO.class,
                    (ClawVIP.getConfigOption().isUUIDMode()) ? player.getUniqueId().toString() : player.getName());
            if (Objects.isNull(pojo)) {
                originalGroup = (!Objects.isNull(ClawVIP.getConfigOption().getVipGroupPlaceholder()) ?
                        ClawVIP.getPAPIConvertedString(ClawVIP.getConfigOption().getVipGroupPlaceholder(), player):"");
                groupName = null;
                dueDate = null;
            } else {
                dueDate = (pojo.getDueDate() != null) ? new Date(pojo.getDueDate().getTime()) : null;
                groupName = pojo.getVipGroup();
                originalGroup = pojo.getOriginalGroup();
            }
        }

        public void unregister() {
            DatabasePlayerDataPOJO pojo = new DatabasePlayerDataPOJO();
            pojo.setPlayerId((ClawVIP.getConfigOption().isUUIDMode())
                    ? player.getUniqueId().toString() : player.getName());
            pojo.setVipGroup(groupName);
            pojo.setDueDate((dueDate != null) ? new java.sql.Date(dueDate.getTime()) : null);
            pojo.setOriginalGroup(originalGroup);
            dbService.getDatabase().insertData(pojo);
        }

        public void setOriginalGroup(String originalGroup) {
            this.originalGroup = originalGroup;
        }

        public Date getDueDate() {
            return dueDate;
        }

        public String getGroupName() {
            return groupName;
        }

        public Player getPlayer() {
            return player;
        }

        public String getOriginalGroup() {
            return originalGroup;
        }

        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
    }

    private class PlayerEventListener implements Listener {

        @EventHandler
        public void onJoin(PlayerJoinEvent e) {
            playerHandler.put(e.getPlayer(), new VIPlayer(e.getPlayer()));
        }

        @EventHandler
        public void onQuit(PlayerQuitEvent e) {
            if (playerHandler.containsKey(e.getPlayer())) {
                playerHandler.get(e.getPlayer()).unregister();
                playerHandler.remove(e.getPlayer());
            }
        }

    }

}
