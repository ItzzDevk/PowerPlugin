package me.itzzdevk.powerplugin.listeners;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        for (ItemStack item : event.getDrops()) {
            if (item.isSimilar(PowerPlugin.plugin.firepower)) {

                PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 1);
                event.getDrops().remove(item);
                break;
            } if (item.isSimilar(PowerPlugin.plugin.healingpower)) {

                PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 2);
                event.getDrops().remove(item);
                break;
            } if (item.isSimilar(PowerPlugin.plugin.windpower)) {

                PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 3);
                event.getDrops().remove(item);
                break;
            } if (item.isSimilar(PowerPlugin.plugin.strengthpower)) {

                PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 4);
                event.getDrops().remove(item);
                break;
            } if (item.isSimilar(PowerPlugin.plugin.lightningpower)) {

                PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 5);
                event.getDrops().remove(item);
                break;
            }
        }



        if (PowerPlugin.levelMap.containsKey(event.getEntity().getUniqueId())) {

            if (PowerPlugin.levelMap.get(event.getEntity().getUniqueId()) <= 3) {

                int lives = PowerPlugin.levelMap.get(event.getEntity().getUniqueId()) - 1;
                PowerPlugin.levelMap.put(event.getEntity().getUniqueId(), lives);
                event.getEntity().sendMessage(ChatColor.RED + "Lost a Level! You are now at Level " + ChatColor.YELLOW + lives + ChatColor.RED + ".");
            }


            if (PowerPlugin.levelMap.get(event.getEntity().getKiller().getUniqueId()) <= 2) {

                int lives = PowerPlugin.levelMap.get(event.getEntity().getKiller().getUniqueId()) + 1;
                PowerPlugin.levelMap.put(event.getEntity().getKiller().getUniqueId(), lives);
                event.getEntity().getKiller().sendMessage(ChatColor.GREEN + "Gained a Level! You are now at Level " + ChatColor.YELLOW + lives + ChatColor.RED + ".");
            }
        }
    }
}
