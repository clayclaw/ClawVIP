package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.BuiltinMessage;
import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.Bukkit;
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
                sender.sendMessage(BuiltinMessage.getMessage("NONEXISTPLAYER"));
            }else{
                ClawVIP.getPAPIConvertedStringList(ClawVIP.getLanguageStringList("CommandLook"), target)
                        .forEach(sender::sendMessage);
            }
        }else{
            sender.sendMessage(BuiltinMessage.getMessage("TYPEPLAYERNAMEREMINDER"));
        }
    }

}
