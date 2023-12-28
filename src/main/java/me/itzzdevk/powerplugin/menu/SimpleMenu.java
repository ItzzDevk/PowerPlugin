package me.itzzdevk.powerplugin.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class SimpleMenu implements Menu {

    private final Map<Integer, BiConsumer<Player, Integer>> buttons = new HashMap<>();

    private final MenuManager menuManager;
    private final Inventory inventory;

    public SimpleMenu(MenuManager menuManager, InventoryHolder holder, int size, String title) {
        this.inventory = Bukkit.createInventory(holder, size, title);
        this.menuManager = menuManager;
    }

    public void addButton(int slot, ItemStack icon, BiConsumer<Player, Integer> clickHandler) {
        if (slot < 0 || slot >= inventory.getSize()) return;
        if (icon.getType() == Material.AIR) return;
        inventory.setItem(slot, icon);
        buttons.put(slot, clickHandler);
    }

    public void addNextButton(ItemStack icon, BiConsumer<Player, Integer> clickHandler) {
        addButton(inventory.firstEmpty(), icon, clickHandler);
    }

    @Override
    public void click(Player player, int slot) {
        BiConsumer<Player, Integer> clickHandler = buttons.get(slot);
        if (clickHandler != null) clickHandler.accept(player, slot);
    }

    @Override
    public void open(Player player) {
        player.openInventory(inventory);
        menuManager.registerMenu(inventory, this);
    }

    @Override
    public void close(Player player) {
        List<HumanEntity> viewers = inventory.getViewers();

        if (!viewers.contains(player)) return;
        player.closeInventory();

        if (viewers.isEmpty()) menuManager.unregisterMenu(inventory);
    }

    @Override
    public void closeAll() {
        Iterator<HumanEntity> viewerIterator = inventory.getViewers().listIterator();
        while (viewerIterator.hasNext()) {
            HumanEntity viewer = viewerIterator.next();
            viewerIterator.remove();
            viewer.closeInventory();
        }
        menuManager.unregisterMenu(inventory);
    }

    protected Inventory getInventory() {
        return inventory;
    }
}
