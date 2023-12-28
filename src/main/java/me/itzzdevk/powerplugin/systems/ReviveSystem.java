package me.itzzdevk.powerplugin.systems;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.UUID;

public class ReviveSystem implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item != null && item.getType() == Material.COMPASS) {
                Player player = event.getPlayer();
                UUID playerId = player.getUniqueId();

                if (PowerPlugin.levelMap.containsKey(playerId) && PowerPlugin.levelMap.get(playerId) == 0) {
                    Inventory reviveMenu = Bukkit.createInventory(player, 27, ChatColor.GOLD + "Revive Menu");

                    for (OfflinePlayer onlinePlayer : Bukkit.getBannedPlayers()) {
                        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
                        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
                        meta.setOwningPlayer(Bukkit.getOfflinePlayer(playerId));
                        meta.setDisplayName(Bukkit.getOfflinePlayer(playerId).getName());
                        playerHead.setItemMeta(meta);

                        reviveMenu.addItem(playerHead);
                    }

                    player.openInventory(reviveMenu);
                }
            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory != null && clickedInventory.getHolder() instanceof Player) {
            Player player = (Player) clickedInventory.getHolder();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem != null && clickedItem.getType() == Material.PLAYER_HEAD) {
                SkullMeta meta = (SkullMeta) clickedItem.getItemMeta();
                UUID revivedPlayerId = meta.getOwningPlayer().getUniqueId();

                if (PowerPlugin.levelMap.containsKey(revivedPlayerId) && PowerPlugin.levelMap.get(revivedPlayerId) == 0) {
                    Player revivedPlayer = Bukkit.getPlayer(revivedPlayerId);
                    if (revivedPlayer != null) {
                        PowerPlugin.plugin.levelMap.put(revivedPlayer.getUniqueId(), 1);
                        player.sendMessage(ChatColor.YELLOW + revivedPlayer.getName() + ChatColor.GREEN + " has been Revived.");
                        Bukkit.getBanList(BanList.Type.NAME).pardon(revivedPlayer.getName());
                    }
                    player.closeInventory();
                }

            }
        }
    }
}
