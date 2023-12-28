package me.itzzdevk.powerplugin.menu;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class RevivalMenu extends SimpleMenu {

    private static final int REVIVAL_LEVEL = 2;

    private static final int MAX_SIZE = 54;
    private static final String TITLE = ChatColor.GOLD + "Revival Menu";

    private final JavaPlugin plugin;

    public RevivalMenu(JavaPlugin plugin, MenuManager menuManager, InventoryHolder holder) {
        super(menuManager, holder, MAX_SIZE, TITLE);
        this.plugin = plugin;
        fillMenu();
    }

    private void fillMenu() {
        Bukkit.getBannedPlayers().stream().filter(RevivalMenu::isLevelBanned).forEach(banned -> {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            if (meta == null) return;

            meta.setOwningPlayer(banned);
            meta.setDisplayName(ChatColor.YELLOW + banned.getName());

            head.setItemMeta(meta);
            addNextButton(head, this::revive);
        });
    }

    private static boolean isLevelBanned(OfflinePlayer offlinePlayer) {
        Map<UUID, Integer> levelMap = PowerPlugin.levelMap;
        UUID uuid = offlinePlayer.getUniqueId();
        return levelMap.containsKey(uuid) && levelMap.get(uuid) == 0;
    }

    private void revive(Player player, int slot) {
        Inventory inventory = getInventory();

        ItemStack button = inventory.getItem(slot);
        if (button == null || button.getType() != Material.PLAYER_HEAD) return;

        SkullMeta meta = (SkullMeta) button.getItemMeta();
        if (meta == null) return;

        OfflinePlayer banned = meta.getOwningPlayer();
        if (banned == null) return;

        Bukkit.getBanList(BanList.Type.NAME).pardon(banned.getName());
        PowerPlugin.levelMap.put(banned.getUniqueId(), REVIVAL_LEVEL);

        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_CHIME, SoundCategory.MASTER, 1, 2);
        player.sendMessage(ChatColor.GREEN + "You have successfully revived " + banned.getName() + "!");

//        This is scheduled so that the event is cancelled properly
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this::closeAll, 0L);

        PlayerInventory playerInventory = player.getInventory();

        ItemStack mainHandStack = playerInventory.getItemInMainHand();
        if (isReviveCompass(mainHandStack)) {
            mainHandStack.setAmount(mainHandStack.getAmount() - 1);
            return;
        }

        ItemStack offHandStack = playerInventory.getItemInOffHand();
        if (isReviveCompass(offHandStack))
            offHandStack.setAmount(offHandStack.getAmount() - 1);
    }

    public static boolean isReviveCompass(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != Material.COMPASS) return false;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null || !itemMeta.hasDisplayName()) return false;

        return itemMeta.getDisplayName().equals(ChatColor.AQUA + "Revive Compass");
    }
}
