package me.clayclaw.bukkit.vip.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ICommand {

    String getName();

    String getPermissionNode();

    List<Class<? extends CommandSender>> getCommandSide();

    void trigger(CommandSender sender, String[] args);

}
