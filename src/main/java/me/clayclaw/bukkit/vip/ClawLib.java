package me.clayclaw.bukkit.vip;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClawLib {

    public static String replaceIfExists(String s, String a, String b){
        if(s.contains(a)) return s.replace(a, b);
        return s;
    }

    public static boolean isNumber(String s){
        return new Scanner(s.trim()).hasNextInt();
    }

    public static List<String> convertColorCodeList(List<String> strings) {
        return strings.stream()
                .map(s -> ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', s))
                .collect(Collectors.toList());
    }

}
