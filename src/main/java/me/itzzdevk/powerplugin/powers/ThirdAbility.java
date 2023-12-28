package me.itzzdevk.powerplugin.powers;

import me.itzzdevk.powerplugin.PowerPlugin;
import me.itzzdevk.powerplugin.utils.ParticleHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class ThirdAbility implements Listener {

    HashMap<UUID, Long> campCooldown = new HashMap<>();
    HashMap<UUID, Long> regenCooldown = new HashMap<>();
    HashMap<UUID, Long> dashCooldown = new HashMap<>();
    HashMap<UUID, Long> strengthCooldown = new HashMap<>();
    HashMap<UUID, Long> lightningultCooldown = new HashMap<>();



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getItemMeta() != null) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.isSneaking()) {
                    if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.RED + "§lFire Power")) {
                        if (!campCooldown.containsKey(playerUUID)) {
                            ParticleHelper.activateFireUlt(player);
                            event.setCancelled(true);
                            campCooldown.put(playerUUID, System.currentTimeMillis() + (26 * 1000));
                        } else {
                            long cooldown = campCooldown.get(playerUUID);
                            if (System.currentTimeMillis() >= cooldown) {
                                ParticleHelper.activateFireUlt(player);
                                event.setCancelled(true);
                                campCooldown.put(playerUUID, System.currentTimeMillis() + (26 * 1000));
                            } else {
                                player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                            }
                        }


                    } else if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "§lHealing Power")) {
                        if (!regenCooldown.containsKey(playerUUID)) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 1));
                            event.setCancelled(true);
                            regenCooldown.put(playerUUID, System.currentTimeMillis() + (21 * 1000));
                        } else {
                            long cooldown = regenCooldown.get(playerUUID);
                            if (System.currentTimeMillis() >= cooldown) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 1));
                                event.setCancelled(true);
                                regenCooldown.put(playerUUID, System.currentTimeMillis() + (21 * 1000));
                            } else {
                                player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                            }
                        }


                    } else if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "§lWind Power")) {
                        if (!dashCooldown.containsKey(playerUUID)) {
                            org.bukkit.util.Vector direction = player.getLocation().getDirection().normalize().multiply(1.7);
                            player.setVelocity(direction);
                            double particleSpacing = 0.1D;
                            int particleCount = 45;
                            double damageAmount = 8.0D;
                            ParticleHelper.spawnLine(event.getPlayer(), particleSpacing, particleCount, damageAmount, 120);
                            event.setCancelled(true);
                            dashCooldown.put(playerUUID, System.currentTimeMillis() + (7 * 1000));
                        } else {
                            long cooldown = dashCooldown.get(playerUUID);
                            if (System.currentTimeMillis() >= cooldown) {
                                org.bukkit.util.Vector direction = player.getLocation().getDirection().normalize().multiply(1.7);
                                player.setVelocity(direction);
                                double particleSpacing = 0.1D;
                                int particleCount = 45;
                                double damageAmount = 8.0D;
                                ParticleHelper.spawnLine(event.getPlayer(), particleSpacing, particleCount, damageAmount, 120);
                                event.setCancelled(true);
                                dashCooldown.put(playerUUID, System.currentTimeMillis() + (7 * 1000));
                            } else {
                                player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                            }
                        }


                    } else if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "§lStrength Power")) {
                        if (!strengthCooldown.containsKey(playerUUID)) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 7));
                            event.setCancelled(true);
                            strengthCooldown.put(playerUUID, System.currentTimeMillis() + (20 * 1000));
                        } else {
                            long cooldown = strengthCooldown.get(playerUUID);
                            if (System.currentTimeMillis() >= cooldown) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 7));
                                event.setCancelled(true);
                                strengthCooldown.put(playerUUID, System.currentTimeMillis() + (20 * 1000));
                            } else {
                                player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                            }
                        }


                    } else if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "§lLightning Power")) {
                        if (!lightningultCooldown.containsKey(playerUUID)) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 25 * 20, 1));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 25 * 20, 1));
                            ParticleHelper.activateLightningUlt(player);
                            event.setCancelled(true);
                            lightningultCooldown.put(playerUUID, System.currentTimeMillis() + (35 * 1000));
                        } else {
                            long cooldown = lightningultCooldown.get(playerUUID);
                            if (System.currentTimeMillis() >= cooldown) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 25 * 20, 1));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 25 * 20, 1));
                                ParticleHelper.activateLightningUlt(player);
                                event.setCancelled(true);
                                lightningultCooldown.put(playerUUID, System.currentTimeMillis() + (8 * 1000));
                            } else {
                                player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                            }
                        }
                    }
                }
            }
        }
    }

}
