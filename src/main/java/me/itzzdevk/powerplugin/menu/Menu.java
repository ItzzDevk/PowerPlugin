package me.itzzdevk.powerplugin.menu;

import org.bukkit.entity.Player;

public interface Menu {
    void click(Player player, int slot);
    void open(Player player);
    void close(Player player);
    void closeAll();
}
