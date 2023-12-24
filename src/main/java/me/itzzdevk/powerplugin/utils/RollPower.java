package me.itzzdevk.powerplugin.utils;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RollPower {

    public static int rollDice() {
        Random random = new Random();
        return random.nextInt(5) + 1;
    }

    public static ItemStack getRewardItem(Player player, int roll) {
        switch (roll) {
            case 1:
                return PowerPlugin.plugin.firepower;
            case 2:
                return PowerPlugin.plugin.healingpower;
            case 3:
                return PowerPlugin.plugin.windpower;
            case 4:
                return PowerPlugin.plugin.strengthpower;
            case 5:
                return PowerPlugin.plugin.lightningpower;
            default:
                return null;
        }
    }
}
