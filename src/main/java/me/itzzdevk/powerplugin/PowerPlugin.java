package me.itzzdevk.powerplugin;

import me.itzzdevk.powerplugin.commands.RerollCommand;
import me.itzzdevk.powerplugin.listeners.DeathListener;
import me.itzzdevk.powerplugin.listeners.JoinListener;
import me.itzzdevk.powerplugin.listeners.RespawnListener;
import me.itzzdevk.powerplugin.preventers.NoClicking;
import me.itzzdevk.powerplugin.preventers.NoDropping;
import me.itzzdevk.powerplugin.listeners.InteractListener;
import me.itzzdevk.powerplugin.systems.LevelsSystem;
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
import sun.jvm.hotspot.debugger.linux.amd64.DwarfParser;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PowerPlugin extends JavaPlugin {


    public ItemStack firepower = new ItemStack(Material.NETHER_STAR, 1);
    public ItemStack healingpower = new ItemStack(Material.NETHER_STAR, 1);
    public ItemStack windpower = new ItemStack(Material.NETHER_STAR, 1);
    public ItemStack strengthpower = new ItemStack(Material.NETHER_STAR, 1);
    public ItemStack lightningpower = new ItemStack(Material.NETHER_STAR, 1);

    public static Map<UUID, Integer> levelMap;
    public static Map<UUID, Integer> powerMap;
    public static PowerPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        registerCommands();
        registerEvents();
        ItemLore();


        levelMap = new HashMap<>();
        powerMap = new HashMap<>();

        LevelsSystem.loadLevelData();
    }

    @Override
    public void onDisable() {
        LevelsSystem.saveLevelData();
    }

    public void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new NoDropping(), this);
        this.getServer().getPluginManager().registerEvents(new NoClicking(), this);
        this.getServer().getPluginManager().registerEvents(new LevelsSystem(), this);

        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new InteractListener(), this);
        this.getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        this.getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }

    public void registerCommands() {
        getCommand("reroll").setExecutor(new RerollCommand());
    }

    public void ItemLore() {
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
}
