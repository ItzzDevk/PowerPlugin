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

import java.util.UUID;

public class DeathListener implements Listener {

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        int livesk = PowerPlugin.levelMap.get(killer.getUniqueId());
        int livesp = PowerPlugin.levelMap.get(player.getUniqueId());
        UUID uuid = player.getUniqueId();

        System.out.println("1 works");


        if (event.getDrops().contains(PowerPlugin.plugin.firepower)) {
            PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 1);
            event.getDrops().remove(PowerPlugin.plugin.firepower);

        } else if (event.getDrops().contains(PowerPlugin.plugin.healingpower)) {
            PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 2);
            event.getDrops().remove(PowerPlugin.plugin.healingpower);

        } else if (event.getDrops().contains(PowerPlugin.plugin.windpower)) {
            PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 3);
            event.getDrops().remove(PowerPlugin.plugin.windpower);

        } else if (event.getDrops().contains(PowerPlugin.plugin.strengthpower)) {
            PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 4);
            event.getDrops().remove(PowerPlugin.plugin.strengthpower);

        } else {
            if (event.getDrops().contains(PowerPlugin.plugin.lightningpower)) {
                PowerPlugin.powerMap.put(event.getEntity().getUniqueId(), 5);
                event.getDrops().remove(PowerPlugin.plugin.lightningpower);

            }
        }


        if (PowerPlugin.levelMap.containsKey(player.getUniqueId())) {

            if (livesp <= 3) {
                int lives = PowerPlugin.levelMap.get(player.getUniqueId()) - 1;
                PowerPlugin.levelMap.put(player.getUniqueId(), lives);
                player.sendMessage(ChatColor.RED + "Lost a Level! You are now at Level " + ChatColor.YELLOW + lives + ChatColor.RED + ".");
            }


            if (livesk <= 2) {
                int lives = PowerPlugin.levelMap.get(killer.getUniqueId()) + 1;
                PowerPlugin.levelMap.put(killer.getUniqueId(), lives);
                killer.sendMessage(ChatColor.GREEN + "Gained a Level! You are now at Level " + ChatColor.YELLOW + lives + ChatColor.RED + ".");
            }
        }
    }
}
