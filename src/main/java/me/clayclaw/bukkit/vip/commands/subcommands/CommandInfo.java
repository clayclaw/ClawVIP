package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.BuiltinMessage;
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
                BuiltinMessage.getMessage("PLUGINNAME") + ClawVIP.getInstance().getDescription().getName(),
                BuiltinMessage.getMessage("PLUGINVERSION") + ClawVIP.getInstance().getDescription().getVersion(),
                BuiltinMessage.getMessage("DEVELOPER") + " ClayClaw",
                ChatColor.BLUE + "--------------------------------------"
        });
    }

}
