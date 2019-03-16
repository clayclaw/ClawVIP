package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandReload implements ICommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getPermissionNode() {
        return "cvip.reload";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class, ConsoleCommandSender.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        ClawVIP.getInstance().initConfig(true);
        sender.sendMessage(ChatColor.GREEN + "[ClawVIP] 已重新读取设定档");
    }
}
