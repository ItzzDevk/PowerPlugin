package me.itzzdevk.powerplugin.commands;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PowerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("power")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.getInventory().contains(PowerPlugin.plugin.firepower)) {
                    player.sendMessage(ChatColor.GREEN + "You have the Fire Power!");

                } else if (player.getInventory().contains(PowerPlugin.plugin.healingpower)) {
                    player.sendMessage(ChatColor.GREEN + "You have the Healing Power!");

                } else if (player.getInventory().contains(PowerPlugin.plugin.windpower)) {
                    player.sendMessage(ChatColor.GREEN + "You have the Wind Power!");

                } else if (player.getInventory().contains(PowerPlugin.plugin.strengthpower)) {
                    player.sendMessage(ChatColor.GREEN + "You have the Strength Power!");

                } else if (player.getInventory().contains(PowerPlugin.plugin.lightningpower)) {
                    player.sendMessage(ChatColor.GREEN + "You have the Lightning Power!");

                } else {
                    player.sendMessage(ChatColor.RED + "Something is wrong, You don't have a Power.");
                }
            }
            return true;
        }
        return false;
    }
}
