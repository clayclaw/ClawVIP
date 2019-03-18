package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.BuiltinMessage;
import me.clayclaw.bukkit.vip.ClawLib;
import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.ClawVIPAPI;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandGive implements ICommand {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getPermissionNode() {
        return "clawvip.give";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class, ConsoleCommandSender.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        if(args.length > 3){
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null || !target.isOnline()){
                sender.sendMessage(BuiltinMessage.getMessage("NONEXISTPLAYER"));
            }else{
                String group = args[2];
                if(!ClawVIP.getConfigOption().getGroupOption().containsKey(group)){
                    sender.sendMessage(BuiltinMessage.getMessage("VIPGROUPNOTFOUND"));
                    return;
                }
                if(!ClawLib.isNumber(args[3])){
                    sender.sendMessage(BuiltinMessage.getMessage("ENTERCORRECTNUMBER"));
                    return;
                }
                int days = Integer.parseInt(args[3]);
                if(ClawVIPAPI.giveVIP(target, group, days)){
                    sender.sendMessage(BuiltinMessage.getMessage("SUCCESSFULLYGIVE")
                            + ((days == -1) ? BuiltinMessage.getMessage("PERMANENT") : days +
                            BuiltinMessage.getMessage("DAY"))
                            + " (" +
                            ChatColor.translateAlternateColorCodes('&',
                                    ClawVIP.getConfigOption().getGroupOption().get(group).getFriendlyName()) + ")");
                }else{
                    sender.sendMessage(BuiltinMessage.getMessage("CANNOTGIVELOWTIER"));
                }
            }
        }else{
            sender.sendMessage(BuiltinMessage.getMessage("GIVECMDREMINDER"));
        }
    }

}
