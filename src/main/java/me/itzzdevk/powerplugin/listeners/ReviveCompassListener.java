package me.itzzdevk.powerplugin.listeners;

import me.itzzdevk.powerplugin.menu.MenuManager;
import me.itzzdevk.powerplugin.menu.RevivalMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ReviveCompassListener implements Listener {

    private final JavaPlugin plugin;
    private final MenuManager menuManager;

    public ReviveCompassListener(JavaPlugin plugin, MenuManager menuManager) {
        this.plugin = plugin;
        this.menuManager = menuManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse (PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY) return;

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack itemStack = event.getItem();
        if (!RevivalMenu.isReviveCompass(itemStack)) return;

        Player player = event.getPlayer();
        RevivalMenu revivalMenu = new RevivalMenu(plugin, menuManager, player);
        revivalMenu.open(player);
    }
}
