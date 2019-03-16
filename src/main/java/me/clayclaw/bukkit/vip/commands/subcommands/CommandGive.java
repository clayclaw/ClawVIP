package me.clayclaw.bukkit.vip.commands.subcommands;

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
                sender.sendMessage(ChatColor.RED + "该玩家不存在或者没有上线");
            }else{
                String group = args[2];
                if(!ClawVIP.getConfigOption().getGroupOption().containsKey(group)){
                    sender.sendMessage(ChatColor.RED + "[ClawVIP] 没有找到相关VIP组");
                    return;
                }
                if(!ClawLib.isNumber(args[3])){
                    sender.sendMessage(ChatColor.RED + "[ClawVIP] 请输入正确的数字！");
                    return;
                }
                int days = Integer.parseInt(args[3]);
                if(ClawVIPAPI.giveVIP(target, group, days)){
                    sender.sendMessage(ChatColor.GOLD + "[ClawVIP] 成功给予玩家 " + ((days == -1) ? "永久" : days+"天")
                            + " 的VIP （" +
                            ChatColor.translateAlternateColorCodes('&',
                                    ClawVIP.getConfigOption().getGroupOption().get(group).getFriendlyName()) + ")");
                }else{
                    sender.sendMessage(ChatColor.RED + "给予玩家VIP失败，你不能给予玩家更低等级的VIP！");
                }
            }
        }else{
            sender.sendMessage(ChatColor.RED + "[ClawVIP] 请先输入玩家名字、VIP用户组、给予时间(按日计算)");
        }
    }

}
