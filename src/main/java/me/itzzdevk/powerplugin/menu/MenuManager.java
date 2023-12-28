package me.itzzdevk.powerplugin.menu;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MenuManager {

    private final Map<Inventory, Menu> menus = new HashMap<>();

    public MenuManager(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    void registerMenu(Inventory inventory, Menu menu) {
        menus.putIfAbsent(inventory, menu);
    }

    void unregisterMenu(Inventory inventory) {
        menus.remove(inventory);
    }

    private class MenuListener implements Listener {

        @EventHandler(ignoreCancelled = true)
        public void onClick(InventoryClickEvent event) {
            Inventory clickedInventory = event.getClickedInventory();
            Menu menu = menus.get(clickedInventory);
            if (menu == null) return;

            ClickType clickType = event.getClick();
            if (!clickType.isLeftClick() && !clickType.isRightClick()) {
                event.setCancelled(true);
                return;
            }

            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            menu.click(player, slot);
            event.setCancelled(true);
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onClose(InventoryCloseEvent event) {
            HumanEntity player = event.getPlayer();
            if (event.getViewers().stream().anyMatch(viewer -> viewer != player)) return;
            unregisterMenu(event.getInventory());
        }
    }
}
