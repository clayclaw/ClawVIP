package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.ClawVIPAPI;
import me.clayclaw.bukkit.vip.commands.ICommand;
import me.clayclaw.bukkit.vip.common.PlayerHandlerService;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandList implements ICommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getPermissionNode() {
        return "clawvip.list";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class, ConsoleCommandSender.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "[ClawVIP] 在线VIP列表:");
        ClawVIPAPI.getVIPOnline().stream()
                .map(p -> ChatColor.translateAlternateColorCodes('&',
                        "&6- ["+ ClawVIP.getConfigOption().getGroupOption().get(
                        ((PlayerHandlerService) ClawVIP.getInstance().getServiceManager()
                                .getService(PlayerHandlerService.class)).getVIPlayer(p).getGroupName())
                        .getFriendlyName() +"&6] " + p.getName()))
                .forEach(sender::sendMessage);
    }
}
