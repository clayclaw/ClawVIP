package me.clayclaw.bukkit.vip.commands.subcommands;


import me.clayclaw.bukkit.vip.ClawVIPAPI;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandRedeem implements ICommand {

    @Override
    public String getName() {
        return "redeem";
    }

    @Override
    public String getPermissionNode() {
        return "clawvip.redeem";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        if(args.length >= 2){
            Player player = (Player) sender;
            ClawVIPAPI.tryToRedeem(player, args[1]);
        }else{
            sender.sendMessage(ChatColor.RED + "[ClawVIP] 请先输入序号");
        }
    }

}
