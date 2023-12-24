package me.itzzdevk.powerplugin.preventers;

import me.itzzdevk.powerplugin.utils.PowerItems;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NoClicking implements Listener {

    @EventHandler
    public void onCrystalClick(InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        List<ItemStack> items = new ArrayList<>();
        items.add(event.getCurrentItem());
        items.add(event.getCursor());
        items.add((event.getClick() == ClickType.NUMBER_KEY) ? event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) : event.getCurrentItem());

        for (ItemStack item : items) {
            if (PowerItems.isfire(item) || PowerItems.ishealing(item) || PowerItems.iswind(item) || PowerItems.isstrength(item) || PowerItems.islightning(item)) {
                if (!entity.isOp())
                    event.setCancelled(true);
            }
        }
    }
}
