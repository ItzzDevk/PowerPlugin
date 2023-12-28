package me.itzzdevk.powerplugin.powers;

import me.itzzdevk.powerplugin.PowerPlugin;
import me.itzzdevk.powerplugin.utils.ParticleHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class SecondAbility implements Listener {

    HashMap<UUID, Long> fireballCooldown = new HashMap<>();
    HashMap<UUID, Long> takeheartsCooldown = new HashMap<>();
    HashMap<UUID, Long> pushawayCooldown = new HashMap<>();
    HashMap<UUID, Long> strengthCooldown = new HashMap<>();
    HashMap<UUID, Long> lightningCooldown = new HashMap<>();


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getItemMeta() != null) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.isSneaking()) {
                    if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.RED + "§lFire Power")) {
                        if (!fireballCooldown.containsKey(playerUUID)) {
                            Fireball fireball = player.launchProjectile(LargeFireball.class);
                            fireball.setVelocity(fireball.getVelocity().multiply(3));
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
                            event.setCancelled(true);
                            fireballCooldown.put(playerUUID, System.currentTimeMillis() + (10 * 1000));
                        } else {
                            long cooldown = fireballCooldown.get(playerUUID);
                            if (System.currentTimeMillis() >= cooldown) {
                                Fireball fireball = player.launchProjectile(LargeFireball.class);
                                fireball.setVelocity(fireball.getVelocity().multiply(3));
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
                                event.setCancelled(true);
                                fireballCooldown.put(playerUUID, System.currentTimeMillis() + (10 * 1000));
                            } else {
                                player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                            }
                        }


                    } else if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "§lWind Power")) {
                        if (!pushawayCooldown.containsKey(playerUUID)) {
                            double maxDistanceSquared = 5 * 5;
                            for (Player target : Bukkit.getOnlinePlayers()) {
                                if (target != player && player.getWorld() == target.getWorld() && player.getLocation()
                                        .distanceSquared(target.getLocation()) <= maxDistanceSquared) {
                                    target.damage(10);
                                    target.setVelocity(new Vector(0, 1.4, 0));
                                }
                            }
                            Location playerLocation = player.getLocation();
                            ParticleHelper.spawnTornado(playerLocation);
                            event.setCancelled(true);
                            pushawayCooldown.put(playerUUID, System.currentTimeMillis() + (8 * 1000));
                        } else {
                            long cooldown = pushawayCooldown.get(playerUUID);
                            if (System.currentTimeMillis() >= cooldown) {
                                double maxDistanceSquared = 10 * 10;
                                for (Player target : Bukkit.getOnlinePlayers()) {
                                    if (target == player && player.getWorld() == target.getWorld() && player.getLocation()
                                            .distanceSquared(target.getLocation()) <= maxDistanceSquared) {
                                        target.damage(10);
                                        target.setVelocity(new Vector(0, 1.4, 0));
                                    }
                                }
                                Location playerLocation = player.getLocation();
                                ParticleHelper.spawnTornado(playerLocation);
                                event.setCancelled(true);
                                pushawayCooldown.put(playerUUID, System.currentTimeMillis() + (8 * 1000));
                            } else {
                                player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            UUID playerUUID = player.getUniqueId();
            Player damager = (Player) event.getDamager();
            ItemStack item = damager.getInventory().getItemInMainHand();

            if (item.getItemMeta().getDisplayName() != null) {
                if (item.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "§lHealing Power")) {
                    if (!takeheartsCooldown.containsKey(playerUUID)) {
                        player.setMaxHealth(4);
                        PowerPlugin.plugin.getServer().getScheduler().runTaskLater(PowerPlugin.plugin, () -> {
                            player.resetMaxHealth();
                        }, 200L);
                        takeheartsCooldown.put(playerUUID, System.currentTimeMillis() + (30 * 1000));
                    } else {
                        long cooldown = takeheartsCooldown.get(playerUUID);
                        if (System.currentTimeMillis() >= cooldown) {
                            player.setMaxHealth(4);
                            PowerPlugin.plugin.getServer().getScheduler().runTaskLater(PowerPlugin.plugin, () -> {
                                player.resetMaxHealth();
                            }, 200L);
                            takeheartsCooldown.put(playerUUID, System.currentTimeMillis() + (30 * 1000));
                        } else {
                            player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                        }
                    }



                } else if (item.getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "§lStrength Power")) {
                    if (!strengthCooldown.containsKey(playerUUID)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15 * 20, 0));
                        damager.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15 * 20, 1));
                        strengthCooldown.put(playerUUID, System.currentTimeMillis() + (23 * 1000));
                    } else {
                        long cooldown = strengthCooldown.get(playerUUID);
                        if (System.currentTimeMillis() >= cooldown) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15 * 20, 0));
                            damager.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15 * 20, 1));
                            strengthCooldown.put(playerUUID, System.currentTimeMillis() + (23 * 1000));
                        } else {
                            player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                        }
                    }



                } else if (item.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "§lLightning Power")) {
                    if (!lightningCooldown.containsKey(playerUUID)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 20, 0));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 1));
                        LightningStrike lightningStrike = player.getWorld().strikeLightning(player.getLocation());
                        player.damage(10);
                        lightningCooldown.put(playerUUID, System.currentTimeMillis() + (24 * 1000));
                    } else {
                        long cooldown = lightningCooldown.get(playerUUID);
                        if (System.currentTimeMillis() >= cooldown) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 20, 0));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 1));
                            LightningStrike lightningStrike = player.getWorld().strikeLightning(player.getLocation());
                            player.damage(10);
                            lightningCooldown.put(playerUUID, System.currentTimeMillis() + (24 * 1000));
                        } else {
                            player.sendMessage(ChatColor.RED + "Cooldown: " + ChatColor.GREEN + (cooldown - System.currentTimeMillis()) / 1000 + " seconds " + ChatColor.RED + "left.");
                        }
                    }
                }
            }
        }
    }







    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        if (projectile instanceof Fireball) {
            Fireball fireball = (Fireball) projectile;
            if (!(fireball.getShooter() instanceof Blaze || fireball.getShooter() instanceof Ghast)) {
                for (Entity entity : projectile.getNearbyEntities(3, 3, 3)) {
                    if (entity instanceof Player) {
                        ((Player) entity).damage(24);
                    }
                }
            }
        }
    }
}
