package me.clayclaw.bukkit.vip;

import com.google.common.collect.Lists;
import me.clayclaw.bukkit.vip.commands.CommandService;
import me.clayclaw.bukkit.vip.common.PlayerHandlerService;
import me.clayclaw.bukkit.vip.common.RedeemCodeService;
import me.clayclaw.bukkit.vip.bridge.PlaceholderService;
import me.clayclaw.bukkit.vip.database.DatabaseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ServiceManager {

    private static HashMap<Class<? extends IService>, IService> serviceMap;

    protected ServiceManager() {}

    protected void enable() {
        serviceMap = new HashMap<>();
        Arrays.stream(Services.values()).forEach(this::accept);
        serviceMap.values().stream().forEachOrdered(IService::enable);
    }

    protected void disable() {

        new ArrayList<>(serviceMap.keySet()).stream()
                .map(serviceMap::get)
                .forEach(IService::disable);

        serviceMap.clear();
    }

    private void accept(Services services) {
        try {
            serviceMap.put(services.targetClass, services.targetClass.newInstance());
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
