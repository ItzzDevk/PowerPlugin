package me.itzzdevk.powerplugin.commands;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("levels")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (PowerPlugin.levelMap.containsKey(player.getUniqueId())) {
                    int lives = PowerPlugin.levelMap.get(player.getUniqueId());
                    player.sendMessage(ChatColor.RED + "You are at Level " + ChatColor.YELLOW + lives + "" + ChatColor.RED + ".");

                } else {
                    player.sendMessage(ChatColor.RED + "Something is wrong, you don't have any Levels.");
                }
            }
            return true;
        }
        return false;
    }
}
