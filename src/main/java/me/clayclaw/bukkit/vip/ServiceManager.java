package me.clayclaw.bukkit.vip;

import me.clayclaw.bukkit.vip.commands.CommandService;
import me.clayclaw.bukkit.vip.common.PlayerHandlerService;
import me.clayclaw.bukkit.vip.common.RedeemCodeService;
import me.clayclaw.bukkit.vip.bridge.PlaceholderService;
import me.clayclaw.bukkit.vip.database.DatabaseService;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ServiceManager {

    private ArrayList<Class<? extends IService>> preInjectServices;
    private static HashMap<Class<? extends IService>, IService> serviceMap;

    protected ServiceManager() {}

    private static Class<? extends IService> apply(Services s) {
        return s.targetClass;
    }

    protected void enable() {
        serviceMap = new HashMap<>();

        Arrays.stream(Services.values()).map(ServiceManager::apply).forEach(this::accept);

        Bukkit.getScheduler().runTaskLater(ClawVIP.getInstance(), () -> {
            preInjectServices.forEach(this::accept);
            serviceMap.values().stream().forEachOrdered(IService::enable);
        }, 1);

    }

    protected void disable() {

        new ArrayList<>(serviceMap.keySet()).stream()
                .map(serviceMap::get)
                .forEach(IService::disable);

        serviceMap.clear();

    }

    public void destroyService(Class<? extends IService> key){
        serviceMap.get(key).disable();
        serviceMap.remove(key);
    }

    private void accept(Class<? extends IService> targetClass) {
        try {
            serviceMap.put(targetClass, targetClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public IService getService(Class<? extends IService> t) {
        return serviceMap.get(t);
    }

    private enum Services {

        DATABASE(DatabaseService.class),
        COMMON_PLAYER(PlayerHandlerService.class),
        COMMON_REDEEMCODE(RedeemCodeService.class),
        COMMAND(CommandService.class),
        BRIDGE_PLACEHOLDER(PlaceholderService.class);

        Class<? extends IService> targetClass;

        Services(Class<? extends IService> targetClass) {
            this.targetClass = targetClass;
        }

    }

}
