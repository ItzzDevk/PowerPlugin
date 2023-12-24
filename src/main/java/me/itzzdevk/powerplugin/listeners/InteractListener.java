package me.itzzdevk.powerplugin.listeners;

import me.itzzdevk.powerplugin.PowerPlugin;
import me.itzzdevk.powerplugin.utils.RollPower;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Power Reroll")) {
                    int roll = RollPower.rollDice();
                    World world = player.getWorld();

                    ItemStack rewardItem = RollPower.getRewardItem(player, roll);
                    event.getPlayer().getInventory().getItemInMainHand().setAmount(-1);

                    event.getPlayer().getInventory().remove(PowerPlugin.plugin.firepower);
                    event.getPlayer().getInventory().remove(PowerPlugin.plugin.healingpower);
                    event.getPlayer().getInventory().remove(PowerPlugin.plugin.windpower);
                    event.getPlayer().getInventory().remove(PowerPlugin.plugin.strengthpower);
                    event.getPlayer().getInventory().remove(PowerPlugin.plugin.lightningpower);

                    BukkitRunnable giveastrix = new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation().add(0.0D, 0.5D, 0.0D), 30, 0.4D, 1.0D, 0.4D, 0.0D);
                            event.getPlayer().getInventory().addItem(rewardItem);
                            world.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 2F, 2);
                        }
                    };
                    giveastrix.runTaskLater(PowerPlugin.plugin, 20L);

                    BukkitRunnable kick = new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.kickPlayer("Oops! Rejoin the Server.");
                        }
                    };
                    kick.runTaskLater(PowerPlugin.plugin, 300 * 20);
                }
            }
        }
    }
}
