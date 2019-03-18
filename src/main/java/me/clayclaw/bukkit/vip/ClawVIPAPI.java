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
     * Generate a redeem code
     * @param group Key Group Id
     */
    public static String generateRedeemCode(String group) {
        return ((RedeemCodeService) ClawVIP.getInstance().getServiceManager()
                .getService(RedeemCodeService.class))
                .generateRedeemCode(group);
    }

    /**
     * @return weather the player is vip or not
     */
    public static boolean isVIP(Player player) {
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class))
                .isVIP(player);
    }

    /***
     * @return player VIP GroupID,
     * @return null if the player is not VIP
     */
    public static String getVIPGroup(Player player) {
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class))
                .getVIPlayer(player)
                .getGroupName();
    }

    /***
     * @return due date or null if the player is not VIP or the player is permanent VIP
     */
    public static Date getVIPDueDate(Player player){
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class))
                .getVIPlayer(player)
                .getDueDate();
    }

    /***
     * remove VIP
     */
    public static void removeVIP(Player player){
        ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class)).removeVIP(
                        ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class)).getVIPlayer(player));
    }

    /***
     * redeem for player
     */
    public static void tryToRedeem(Player player, String key){
        ((RedeemCodeService) ClawVIP.getInstance().getServiceManager()
                .getService(RedeemCodeService.class)).tryToRedeem(player, key);
    }

    /***
     * @return player Id
     */
    public static String getId(Player player){
        return (ClawVIP.getConfigOption().isUUIDMode()) ? player.getUniqueId().toString() : player.getName();
    }

    /***
     * @return success or not
     */
    public static boolean giveVIP(Player player, String group, int days){
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class)).updateVIP(player, group, days == -1, days);
    }

    /***
     * @return online VIP players
     */
    public static Set<Player> getVIPOnline(){
        return ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                .getService(PlayerHandlerService.class)).getVIPOnline();
    }

    /***
     * @return MySQL Connection
     */
    public static Connection getConnection(){
        return ((MySQLDatabase)((DatabaseService) ClawVIP.getInstance().getServiceManager()
                .getService(DatabaseService.class)).getDatabase()).getConnection();
    }

}
