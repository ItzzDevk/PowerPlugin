package me.itzzdevk.powerplugin.systems;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public class LevelsSystem implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskLater(PowerPlugin.plugin, () -> saveLevelData(), 20L);
    }

    public static void loadLevelData() {

        for (String playerId : PowerPlugin.plugin.getConfig().getKeys(false)) {
            UUID uuid = UUID.fromString(playerId);
            int lives = PowerPlugin.plugin.getConfig().getInt(playerId);
            PowerPlugin.plugin.levelMap.put(uuid, lives);
        }
    }

    public static void saveLevelData() {

        for (Map.Entry<UUID, Integer> entry : PowerPlugin.plugin.levelMap.entrySet()) {
            UUID uuid = entry.getKey();
            int lives = entry.getValue();
            PowerPlugin.plugin.getConfig().set(uuid.toString(), lives);
        }

        PowerPlugin.plugin.saveConfig();
    }
}
