package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandInfo implements ICommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getPermissionNode() {
        return "clawvip.info";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class, ConsoleCommandSender.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        getMessageDescription().stream().forEach(sender::sendMessage);
    }

    public static List<String> getMessageDescription() {
        return Arrays.asList(new String[]{
                ChatColor.BLUE + "--------------------------------------",
                ChatColor.GOLD + "插件名称: " + ClawVIP.getInstance().getDescription().getName(),
                ChatColor.GOLD + "插件版本: " + ClawVIP.getInstance().getDescription().getVersion(),
                ChatColor.GOLD + "插件开发人员: ClayClaw",
                ChatColor.RED + "本插件目前仅於MCBBS免费公开下载。",
                ChatColor.BLUE + "--------------------------------------"
        });
    }

}
