package me.clayclaw.bukkit.vip.common;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.ConfigOption;
import me.clayclaw.bukkit.vip.IService;
import me.clayclaw.bukkit.vip.database.DatabaseService;
import me.clayclaw.bukkit.vip.database.pojo.DatabaseKeyDataPOJO;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RedeemCodeService implements IService {

    private DatabaseService dbService;
    private PlayerHandlerService playerService;

    @Override
    public void enable() {
        dbService = (DatabaseService) ClawVIP.getInstance().getServiceManager().getService(DatabaseService.class);
        playerService = ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class));
    }

    @Override
    public void disable() {}

    public String generateRedeemCode(String group) {
        String key = RandomStringUtils.random(
                ClawVIP.getConfigOption().getRedeemCodeLength(),
                ClawVIP.getConfigOption().getRedeemCodeInvolved());
        DatabaseKeyDataPOJO pojo = new DatabaseKeyDataPOJO();
        pojo.setKey(key);
        pojo.setKeyGroup(group);
        dbService.getDatabase().insertData(pojo);
        return key;
    }

    public void tryToRedeem(Player player, String key) {
        DatabaseKeyDataPOJO pojo =
                (DatabaseKeyDataPOJO) dbService.getDatabase().extractData(DatabaseKeyDataPOJO.class, key);

        if(Objects.isNull(pojo)) {
            player.sendMessage(ChatColor.RED + "[ClawVIP] 使用序号失败，没有找到相关序号");
            return;
        }

        if(!ClawVIP.getConfigOption().getKeyOption().containsKey(pojo.getKeyGroup())){
            player.sendMessage(ChatColor.RED + "[ClawVIP] 使用序号失败，此序号没有找到相关序号设定");
            return;
        }

        ConfigOption.KeyOption ko = ClawVIP.getConfigOption().getKeyOption().get(pojo.getKeyGroup());
        String targetGroup = ko.getVipGroup();
        if(!ClawVIP.getConfigOption().getGroupOption().containsKey(targetGroup)){
            player.sendMessage(ChatColor.RED + "[ClawVIP] 使用序号失败，此序号没有找到相关VIP组");
            return;
        }
        boolean isPermanent = ko.getDays() == -1;

        if(playerService.updateVIP(player, targetGroup, isPermanent, ko.getDays())){
            player.sendMessage(ClawVIP.getLanguageString("RedeemSuccess"));
            dbService.getDatabase().removeData(DatabaseKeyDataPOJO.class, key);
        }else{
            return;
        }

    }

}
