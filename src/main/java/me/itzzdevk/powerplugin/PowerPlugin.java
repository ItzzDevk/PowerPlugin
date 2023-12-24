package me.itzzdevk.powerplugin;

import me.itzzdevk.powerplugin.preventers.NoDropping;
import me.itzzdevk.powerplugin.listeners.Reroll;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PowerPlugin extends JavaPlugin {


    public ItemStack firepower = new ItemStack(Material.NETHER_STAR, 1);
    public ItemStack healingpower = new ItemStack(Material.NETHER_STAR, 1);
    public ItemStack windpower = new ItemStack(Material.NETHER_STAR, 1);
    public ItemStack strengthpower = new ItemStack(Material.NETHER_STAR, 1);
    public ItemStack lightningpower = new ItemStack(Material.NETHER_STAR, 1);

    public static PowerPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        this.getServer().getPluginManager().registerEvents(new NoDropping(), this);
        this.getServer().getPluginManager().registerEvents(new Reroll(), this);

        powerMap = new HashMap<>();
        loadPowerData();



        ItemMeta firemeta = firepower.getItemMeta();
        firemeta.setDisplayName(ChatColor.RED + "§lFire Power");
        firepower.setItemMeta(firemeta);

        ItemMeta healingmeta = healingpower.getItemMeta();
        healingmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "§lHealing Power");
        healingpower.setItemMeta(healingmeta);

        ItemMeta windmeta = windpower.getItemMeta();
        windmeta.setDisplayName(ChatColor.GRAY + "§lWind Power");
        windpower.setItemMeta(windmeta);

        ItemMeta strengthmeta = strengthpower.getItemMeta();
        strengthmeta.setDisplayName(ChatColor.DARK_RED + "§lStrength Power");
        strengthpower.setItemMeta(strengthmeta);

        ItemMeta lightningmeta = lightningpower.getItemMeta();
        lightningmeta.setDisplayName(ChatColor.DARK_GRAY + "§lLightning Power");
        lightningpower.setItemMeta(lightningmeta);

    }

    @Override
    public void onDisable() {
        savePowerData();
    }

    public static Map<UUID, Integer> powerMap;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Delayed task to handle data saving onto the disk
        Bukkit.getScheduler().runTaskLater(this, () -> savePowerData(), 20L);
    }

    private void loadPowerData() {

        for (String playerId : getConfig().getKeys(false)) {
            UUID uuid = UUID.fromString(playerId);
            int lives = getConfig().getInt(playerId);
            powerMap.put(uuid, lives);
        }
    }

    private void savePowerData() {

        for (Map.Entry<UUID, Integer> entry : powerMap.entrySet()) {
            UUID uuid = entry.getKey();
            int lives = entry.getValue();
            getConfig().set(uuid.toString(), lives);
        }

        saveConfig();
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().remove(PowerPlugin.plugin.firepower);
        event.getDrops().remove(PowerPlugin.plugin.healingpower);
        event.getDrops().remove(PowerPlugin.plugin.windpower);
        event.getDrops().remove(PowerPlugin.plugin.strengthpower);
        event.getDrops().remove(PowerPlugin.plugin.lightningpower);
    }

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (powerMap.get(uuid).equals(1)) {
            player.getInventory().addItem(PowerPlugin.plugin.firepower);

        } else if (powerMap.get(uuid).equals(2)) {
            player.getInventory().addItem(PowerPlugin.plugin.healingpower);

        } else if (powerMap.get(uuid).equals(3)) {
            player.getInventory().addItem(PowerPlugin.plugin.windpower);

        } else if (powerMap.get(uuid).equals(4)) {
            player.getInventory().addItem(PowerPlugin.plugin.strengthpower);

        } else if (powerMap.get(uuid).equals(5)) {
            player.getInventory().addItem(PowerPlugin.plugin.lightningpower);

        }
    }
}
