package me.clayclaw.bukkit.vip.commands.subcommands;

import me.clayclaw.bukkit.vip.BuiltinMessage;
import me.clayclaw.bukkit.vip.ClawVIPAPI;
import me.clayclaw.bukkit.vip.commands.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandRemove implements ICommand {

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getPermissionNode() {
        return "clawvip.remove";
    }

    @Override
    public List<Class<? extends CommandSender>> getCommandSide() {
        return Arrays.asList(Player.class, ConsoleCommandSender.class);
    }

    @Override
    public void trigger(CommandSender sender, String[] args) {
        if(args.length > 1){
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null || !target.isOnline()){
                sender.sendMessage(BuiltinMessage.getMessage("NONEXISTPLAYER"));
            }else{
                if(ClawVIPAPI.isVIP(target)){
                    ClawVIPAPI.removeVIP(target);
                    sender.sendMessage(BuiltinMessage.getMessage("SUCCESSFULLYREMOVE")
                            +target.getName()+BuiltinMessage.getMessage("SVIPGROUP"));
                }else{
                    sender.sendMessage(BuiltinMessage.getMessage("PLAYERNOTVIP"));
                }
            }
        }else{
            sender.sendMessage(BuiltinMessage.getMessage("TYPEPLAYERNAMEREMINDER"));
        }
    }

}
