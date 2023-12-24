package me.itzzdevk.powerplugin.listeners;

import me.itzzdevk.powerplugin.PowerPlugin;
import me.itzzdevk.powerplugin.utils.RollPower;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();


        if (!PowerPlugin.plugin.levelMap.containsKey(player.getUniqueId())) {
            int roll = RollPower.rollDice();
            ItemStack rewardItem = RollPower.getRewardItem(player, roll);
            player.getInventory().addItem(rewardItem);

            PowerPlugin.plugin.levelMap.put(player.getUniqueId(), 1);
        }
    }

}
