package me.itzzdevk.powerplugin.utils;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.inventory.ItemStack;

public class PowerItems {

    public static boolean isfire(ItemStack item) {
        return item != null && item.equals(PowerPlugin.plugin.firepower);
    }

    public static boolean ishealing(ItemStack item) {
        return item != null && item.equals(PowerPlugin.plugin.healingpower);
    }

    public static boolean iswind(ItemStack item) {
        return item != null && item.equals(PowerPlugin.plugin.windpower);
    }

    public static boolean isstrength(ItemStack item) {
        return item != null && item.equals(PowerPlugin.plugin.strengthpower);
    }

    public static boolean islightning(ItemStack item) {
        return item != null && item.equals(PowerPlugin.plugin.lightningpower);
    }
}
