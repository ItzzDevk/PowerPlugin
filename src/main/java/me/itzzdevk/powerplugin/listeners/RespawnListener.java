package me.itzzdevk.powerplugin.listeners;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();


        if (PowerPlugin.plugin.powerMap.get(uuid).equals(1)) {
            player.getInventory().addItem(PowerPlugin.plugin.firepower);
            PowerPlugin.plugin.powerMap.remove(uuid);

        } else if (PowerPlugin.plugin.powerMap.get(uuid).equals(2)) {
            player.getInventory().addItem(PowerPlugin.plugin.healingpower);
            PowerPlugin.plugin.powerMap.remove(uuid);

        } else if (PowerPlugin.plugin.powerMap.get(uuid).equals(3)) {
            player.getInventory().addItem(PowerPlugin.plugin.windpower);
            PowerPlugin.plugin.powerMap.remove(uuid);

        } else if (PowerPlugin.plugin.powerMap.get(uuid).equals(4)) {
            player.getInventory().addItem(PowerPlugin.plugin.strengthpower);
            PowerPlugin.plugin.powerMap.remove(uuid);

        } else if (PowerPlugin.plugin.powerMap.get(uuid).equals(5)) {
            player.getInventory().addItem(PowerPlugin.plugin.lightningpower);
            PowerPlugin.plugin.powerMap.remove(uuid);

        }



        if (PowerPlugin.levelMap.containsKey(player.getUniqueId())) {
            int lives = PowerPlugin.levelMap.get(player.getUniqueId());
            if (lives == 0) {

                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), ChatColor.RED + "You have run out of Levels!", null, null);

                Bukkit.getScheduler().runTask(PowerPlugin.plugin, () -> {
                    player.kickPlayer(ChatColor.RED + "You are out of Levels!");
                });
            }
        }
    }

}
