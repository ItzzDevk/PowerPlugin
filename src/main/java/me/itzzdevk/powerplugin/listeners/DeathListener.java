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
        int livesk = PowerPlugin.levelMap.get(killer.getUniqueId()) - 0;
        int livesp = PowerPlugin.levelMap.get(player.getUniqueId()) - 0;
        UUID uuid = player.getUniqueId();


        if (event.getDrops().contains(PowerPlugin.plugin.firepower)) {
            PowerPlugin.plugin.powerMap.put(event.getEntity().getUniqueId(), 1);
            event.getDrops().remove(PowerPlugin.plugin.firepower);

        } else if (event.getDrops().contains(PowerPlugin.plugin.healingpower)) {
            PowerPlugin.plugin.powerMap.put(event.getEntity().getUniqueId(), 2);
            event.getDrops().remove(PowerPlugin.plugin.healingpower);

        } else if (event.getDrops().contains(PowerPlugin.plugin.windpower)) {
            PowerPlugin.plugin.powerMap.put(event.getEntity().getUniqueId(), 3);
            event.getDrops().remove(PowerPlugin.plugin.windpower);

        } else if (event.getDrops().contains(PowerPlugin.plugin.strengthpower)) {
            PowerPlugin.plugin.powerMap.put(event.getEntity().getUniqueId(), 4);
            event.getDrops().remove(PowerPlugin.plugin.strengthpower);

        } else if (event.getDrops().contains(PowerPlugin.plugin.lightningpower)) {
            PowerPlugin.plugin.powerMap.put(event.getEntity().getUniqueId(), 5);
            event.getDrops().remove(PowerPlugin.plugin.lightningpower);

        }



        if (PowerPlugin.plugin.levelMap.containsKey(player.getUniqueId())
                && PowerPlugin.plugin.levelMap.containsKey(killer.getUniqueId())) {
            if (killer instanceof Player) {
                if (!killer.getDisplayName().equals(player.getDisplayName())) {

                    if (livesk <= 2) {
                        int lives = PowerPlugin.levelMap.get(player.getUniqueId()) - 1;
                        PowerPlugin.levelMap.put(player.getUniqueId(), lives);
                        killer.sendMessage(ChatColor.GREEN + "Gained an Energy! You are now at Level " + ChatColor.YELLOW + lives + ChatColor.RED + ".");
                    }


                    if (livesp <= 3) {
                        int lives = PowerPlugin.levelMap.get(player.getUniqueId()) + 1;
                        PowerPlugin.levelMap.put(killer.getUniqueId(), lives);
                        player.sendMessage(ChatColor.RED + "Lost an Energy! You are now at Level " + ChatColor.YELLOW + lives + ChatColor.RED + ".");
                    }
                }
            }
        }
    }
}
