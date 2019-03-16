package me.clayclaw.bukkit.vip.commands;

import me.clayclaw.bukkit.vip.ClawVIP;
import me.clayclaw.bukkit.vip.IService;
import me.clayclaw.bukkit.vip.commands.subcommands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;

public class CommandService implements IService {

    private ClawVIPCommandExecutor executor;
    private static final String COMMAND_NAME = "cvip";

    private HashMap<String, ICommand> commandMap;

    @Override
    public void enable() {
        commandMap = new HashMap<>();
        Arrays.stream(SubCommands.values())
                .map(sc -> sc.targetClass)
                .forEach(t -> {
                    try {
                        ICommand ic = t.newInstance();
                        commandMap.put(ic.getName(), ic);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        Bukkit.getPluginCommand(COMMAND_NAME).setExecutor(executor = new ClawVIPCommandExecutor());
    }

    @Override
    public void disable() {
        commandMap.clear();
    }

    enum SubCommands {

        HELP(CommandHelp.class),
        INFO(CommandInfo.class),
        GEN_REDEEMCODE(CommandGenerateRedeemCode.class),
        GIVE(CommandGive.class),
        LIST(CommandList.class),
        LOOK(CommandLook.class),
        REDEEM(CommandRedeem.class),
        REMOVE(CommandRemove.class),
        SELF(CommandSelf.class),
        RELOAD(CommandReload.class);

        Class<? extends ICommand> targetClass;

        SubCommands(Class<? extends ICommand> targetClass) {
            this.targetClass = targetClass;
        }

    }

    public class ClawVIPCommandExecutor implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (label.equalsIgnoreCase(COMMAND_NAME)) {
                if (args.length > 0) {
                    if (commandMap.keySet().contains(args[0])) {
                        ICommand subcmd = commandMap.get(args[0]);
                        if (subcmd.getCommandSide().stream().filter(c -> c.isAssignableFrom(sender.getClass()))
                                .findFirst().isPresent()) {
                            if (subcmd.getPermissionNode() == null ||
                                    sender.hasPermission(subcmd.getPermissionNode())) {
                                subcmd.trigger(sender, args);
                            } else {
                                sender.sendMessage(ClawVIP.getLanguageString("NoPermission"));
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "[ClawVIP] 部分命令只有玩家能够使用");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ClawVIP.getLanguageString("HelpReminder"));
                    }
                } else {
                    sender.sendMessage(ClawVIP.getLanguageString("HelpReminder"));
                }
            }
            return false;
        }

    }

}
