package me.itzzdevk.powerplugin.commands;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLevelsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("level") && args.length >= 3 && args[0].equalsIgnoreCase("set")) {
            if (!sender.isOp()) {
                sender.sendMessage("You don't have permission to use this command!");
                return true;
            }
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage("Player not found!");
                return true;
            }
            try {
                int amount = Integer.parseInt(args[2]);
                givePaper(player, amount);
                sender.sendMessage(ChatColor.GREEN + "Set " + player.getName() + " to Level " + ChatColor.YELLOW + amount + ".");
                player.sendMessage(ChatColor.GREEN + "You have been set to Level " + ChatColor.YELLOW + amount + ".");
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid amount specified!");
            }
            return true;
        }
        return false;
    }

    private void givePaper(Player player, int amount) {
        PowerPlugin.levelMap.put(player.getUniqueId(), amount);
    }
}
