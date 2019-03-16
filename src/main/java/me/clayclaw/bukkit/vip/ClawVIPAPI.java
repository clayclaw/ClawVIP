package me.clayclaw.bukkit.vip;

import me.clayclaw.bukkit.vip.common.PlayerHandlerService;
import me.clayclaw.bukkit.vip.common.RedeemCodeService;
import me.clayclaw.bukkit.vip.database.DatabaseService;
import me.clayclaw.bukkit.vip.database.MySQLDatabase;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Set;
import java.sql.Connection;

public class ClawVIPAPI {

    /**
     * 生成一个VIP序号
     * @param group 尊贵用户组的ID
     */
    public static String generateRedeemCode(String group) {
        return ((RedeemCodeService) ClawVIP.getInstance().getServiceManager()
                .getService(RedeemCodeService.class))
                .generateRedeemCode(group);
    }

    /**
     * 检查玩家是否VIP
     * @param player 玩家
     */
    public static boolean isVIP(Player player) {
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class))
                .isVIP(player);
    }

    /***
     * 获取VIP玩家身处的VIP用户组
     * 如果不是VIP玩家则会传回 null
     */
    public static String getVIPGroup(Player player) {
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class))
                .getVIPlayer(player)
                .getGroupName();
    }

    /***
     * 取得玩家VIP用户组到期日
     * 如果不是VIP玩家则会传回 null
     */
    public static Date getVIPDueDate(Player player){
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class))
                .getVIPlayer(player)
                .getDueDate();
    }

    /***
     * 删除玩家的VIP
     */
    public static void removeVIP(Player player){
        ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class)).removeVIP(
                        ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class)).getVIPlayer(player));
    }

    /***
     * 替玩家使用序号
     * @param key 序号
     */
    public static void tryToRedeem(Player player, String key){
        ((RedeemCodeService) ClawVIP.getInstance().getServiceManager()
                .getService(RedeemCodeService.class)).tryToRedeem(player, key);
    }

    /***
     * 获取玩家Id
     */
    public static String getId(Player player){
        return (ClawVIP.getConfigOption().isUUIDMode()) ? player.getUniqueId().toString() : player.getName();
    }

    /***
     * 给予玩家VIP
     */
    public static boolean giveVIP(Player player, String group, int days){
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class)).updateVIP(player, group, days == -1, days);
    }

    /***
     * 获取所有在线的VIP
     */
    public static Set<Player> getVIPOnline(){
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class)).getVIPOnline();
    }

    /***
     * 获取MySQL Connection
     */
    public static Connection getConnection(){
        return ((MySQLDatabase)((DatabaseService) ClawVIP.getInstance().getServiceManager()
                .getService(DatabaseService.class)).getDatabase()).getConnection();
    }

}
