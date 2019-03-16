package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandSelf implements ICommand {

    @Override
    public String getName() {
        return "self";
    }

    @Override
    public String getPermissionNode() {
        return "clawvip.self";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        ClawVIP.getPAPIConvertedStringList(ClawVIP.getLanguageStringList("CommandSelf"), (Player) sender)
                .forEach(sender::sendMessage);
    }

}
