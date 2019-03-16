package me.clayclaw.bukkit.vip.bridge;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.IService;
import me.clayclaw.bukkit.vip.common.PlayerHandlerService;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class PlaceholderService implements IService {

    private PlayerHandlerService playerService;
    private static HashMap<String, IPlaceholderResponse> responseMap;

    @Override
    public void enable() {
        responseMap = new HashMap<>();
        playerService =
                (PlayerHandlerService) ClawVIP.getInstance().getServiceManager().getService(PlayerHandlerService.class);
        addPlaceholder("duedate", p -> (playerService.getVIPlayer(p).getDueDate() != null)
                ? new SimpleDateFormat(ClawVIP.getConfigOption().getDateFormat())
                .format(playerService.getVIPlayer(p).getDueDate()) : "无");
        addPlaceholder("groupname", p -> (playerService.getVIPlayer(p).getGroupName() != null) ?
                ClawVIP.getPAPIConvertedString(ClawVIP.getConfigOption().getGroupOption().get(
                        playerService.getVIPlayer(p).getGroupName()).getFriendlyName(), p) : "无");
        addPlaceholder("group", p -> (playerService.getVIPlayer(p).getGroupName() != null)
                ? playerService.getVIPlayer(p).getGroupName() : "无");
        new PlaceholderServiceExpansion().register();
    }

    @Override
    public void disable() {
        responseMap.clear();
    }

    protected static void addPlaceholder(String s, IPlaceholderResponse r) {
        responseMap.put(s, r);
    }

    private IPlaceholderResponse getResponse(String key) {
        return (responseMap.containsKey(key)) ? responseMap.get(key) : null;
    }

    public class PlaceholderServiceExpansion extends PlaceholderExpansion {

        @Override
        public boolean canRegister(){
            return true;
        }

        @Override
        public String getIdentifier() {
            return "vip";
        }

        @Override
        public String getPlugin() {
            return ClawVIP.getInstance().getDescription().getName();
        }

        @Override
        public String getAuthor() {
            return ClawVIP.getInstance().getDescription().getAuthors().toString();
        }

        @Override
        public String getVersion() {
            return ClawVIP.getInstance().getDescription().getVersion();
        }

        @Override
        public String onPlaceholderRequest(Player player, String s) {
            return (getResponse(s) != null) ? getResponse(s).response(player) : "";
        }

    }

    interface IPlaceholderResponse {
        String response(Player player);
    }

}
