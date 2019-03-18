package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.BuiltinMessage;
import me.clayclaw.bukkit.vip.ClawVIPAPI;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandGenerateRedeemCode implements ICommand {

    @Override
    public String getName() {
        return "newkey";
    }

    @Override
    public String getPermissionNode() {
        return "clawvip.newkey";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class, ConsoleCommandSender.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(BuiltinMessage.getMessage("ENTERREDEEMCODENAME"));
            return;
        }
        sender.sendMessage(BuiltinMessage.getMessage("SUCCESSFULLYREDEEM")
                + ClawVIPAPI.generateRedeemCode(args[1]));
    }

}
