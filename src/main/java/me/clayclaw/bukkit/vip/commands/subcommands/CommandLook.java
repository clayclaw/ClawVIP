package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandLook implements ICommand {

    @Override
    public String getName() {
        return "look";
    }

    @Override
    public String getPermissionNode() {
        return "clawvip.look";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class, ConsoleCommandSender.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        if(args.length >= 2){
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null || !target.isOnline()){
                sender.sendMessage(ChatColor.RED + "该玩家不存在或者没有上线");
            }else{
                ClawVIP.getPAPIConvertedStringList(ClawVIP.getLanguageStringList("CommandLook"), target)
                        .forEach(sender::sendMessage);
            }
        }else{
            sender.sendMessage(ChatColor.RED + "[ClawVIP] 请先输入玩家的名字");
        }
    }

}
