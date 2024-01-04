package me.itzzdevk.powerplugin.powers;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FirstAbility implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();
        int lives = PowerPlugin.levelMap.get(player.getUniqueId());

        if (lives >= 1) {
            if (item != null) {
                if (PowerPlugin.levelMap.get(player.getUniqueId()) >= 1) {
                    if (item.isSimilar(PowerPlugin.plugin.firepower)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 20, 0));

                    }

                    if (item.isSimilar(PowerPlugin.plugin.healingpower)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 90 * 20, 1));

                    }

                    if (item.isSimilar(PowerPlugin.plugin.strengthpower)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 20, 0));

                    }

                    if (item.isSimilar(PowerPlugin.plugin.lightningpower)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 0));

                    }
                }
            }
        }
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity player = event.getEntity();
        int lives = PowerPlugin.levelMap.get(player.getUniqueId());

        if (lives >= 1) {
            if (player instanceof Player) {
                ItemStack item = ((Player) player).getInventory().getItemInOffHand();

                if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    if (item != null && item.getItemMeta() != null) {
                        if (item.isSimilar(PowerPlugin.plugin.windpower)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
