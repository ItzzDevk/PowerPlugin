package me.itzzdevk.powerplugin.preventers;

import me.itzzdevk.powerplugin.PowerPlugin;
import me.itzzdevk.powerplugin.utils.PowerItems;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NoDropping implements Listener {

    @EventHandler
    public void onCrystalDrop(PlayerDropItemEvent event) {
        Player entity = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();

        if (PowerItems.isfire(item) || PowerItems.ishealing(item) || PowerItems.iswind(item) || PowerItems.isstrength(item) || PowerItems.islightning(item)) {
            if (!entity.isOp()) {
                event.setCancelled(true);
            }
        }
    }
}
