package me.itzzdevk.powerplugin.utils;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleHelper {

    public static void activateLightningUlt(Player player) {
        for (int i = 0; i < 3; i++) {
            Bukkit.getScheduler().runTaskLater(PowerPlugin.plugin, () -> {
                for (Entity entity : player.getNearbyEntities(4, 4, 4)) {
                    if (entity instanceof Player) {
                        LightningStrike lightningStrike = player.getWorld().strikeLightning(entity.getLocation());
                        ((Player) entity).damage(20);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 2F, 1F);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1 * 20, 2));
                    }
                }
            }, i * 20L);
        }

        for (int in = 0; in < 30; in++) {
            Bukkit.getScheduler().runTaskLater(PowerPlugin.plugin, () -> {
                createLightningCircle(player);
            }, in * 2L);
        }
    }

    public static BukkitTask campfireTask;
    public static void activateFireUlt(Player player) {
        Block block = player.getWorld().getBlockAt(player.getLocation());
        block.setType(Material.CAMPFIRE);

        campfireTask = new BukkitRunnable() {
            int timer = 0;

            @Override
            public void run() {
                if (timer >= 10) {
                    block.setType(Material.AIR);
                    cancel();
                } else {
                    for (Entity nearbyPlayer : player.getLocation().getWorld().getNearbyEntities(block.getLocation(), 4, 4, 4)) {
                        ((Player) nearbyPlayer).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1 * 20, 0));
                    }
                }
                createFireCircle(block);
                timer++;
            }
        }.runTaskTimer(PowerPlugin.plugin, 0, 20);
    }



    public static void spawnLine(Player player, double particleSpacing, int particleCount, double damageAmount, int time) {
        Location startLocation = player.getEyeLocation();
        Vector direction = player.getEyeLocation().getDirection().normalize().multiply(particleSpacing);
        for (int i = 0; i < particleCount; i++) {
            Location particleLocation = startLocation.clone().add(direction.clone().multiply(i));
            particleLocation.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 1, new Particle.DustOptions(Color.GRAY, 1.0F));
            for (Entity entity : particleLocation.getWorld().getNearbyEntities(particleLocation, 0.5D, 0.5D, 0.5D)) {
            }
        }
    }

    public static void createLightningCircle(Player players) {
        final int radius = 4;
        final int particleCount = 150;
        (new BukkitRunnable() {
            int ticksPassed = 0;

            int particlesDuration = 5;

            Location center = players.getLocation();

            double pullRadius = 6.0D;

            double speed = 0.5D;

            public void run() {
                int i;
                for (i = 0; i < particleCount; i++) {
                    double angle = 6.283185307179586D * i / particleCount;
                    double x = this.center.getX() + radius * Math.cos(angle);
                    double z = this.center.getZ() + radius * Math.sin(angle);
                    Location particleLoc = new Location(this.center.getWorld(), x, this.center.getY(), z);
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GRAY, 1.0F);
                    particleLoc.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, dustOptions);
                }
                this.ticksPassed += 5;
                if (this.ticksPassed >= this.particlesDuration) {
                    cancel();
                    for (i = 0; i < particleCount; i++) {
                        double angle = 6.283185307179586D * i / particleCount;
                        double x = this.center.getX() + radius * Math.cos(angle);
                        double z = this.center.getZ() + radius * Math.sin(angle);
                        Location particleLoc = new Location(this.center.getWorld(), x, this.center.getY(), z);
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE, 1.0F);
                        particleLoc.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 0, dustOptions);
                    }
                }
            }
        }).runTaskTimer(PowerPlugin.plugin, 0L, 5L);
    }

    public static void spawnTornado(Location location) {
        new BukkitRunnable() {
            int ticks = 0;
            double radius = 1.0;
            double heightIncrease = 0.25;
            List<Location> particleLocations = new ArrayList<>();

            @Override
            public void run() {
                ticks++;
                if (ticks > 40) {
                    cancel();
                    return;
                }

                for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 8) {
                    double x = radius * Math.cos(theta);
                    double z = radius * Math.sin(theta);
                    double y = heightIncrease * ticks;

                    Location particleLoc = location.clone().add(x, y, z);
                    particleLocations.add(particleLoc);
                    particleLoc.getWorld().spawnParticle(Particle.CLOUD, particleLoc, 0);
                }

                radius += 0.20;
                removeOldParticles();
            }

            private void removeOldParticles() {
                if (!particleLocations.isEmpty()) {
                    for (Location loc : particleLocations) {
                        loc.getWorld().spawnParticle(Particle.CLOUD, loc, 0, 0, 0, 0, 0);
                    }
                    particleLocations.clear();
                }
            }
        }.runTaskTimer(PowerPlugin.plugin, 0L, 1L);
    }

    public static void createFireCircle(Block block) {
        final int radius = 4;
        final int particleCount = 150;
        (new BukkitRunnable() {
            int ticksPassed = 0;

            int particlesDuration = 140;

            Location center = block.getLocation();

            double pullRadius = 6.0D;

            double speed = 0.5D;

            public void run() {
                int i;
                for (i = 0; i < particleCount; i++) {
                    double angle = 6.283185307179586D * i / particleCount;
                    double x = this.center.getX() + radius * Math.cos(angle);
                    double z = this.center.getZ() + radius * Math.sin(angle);
                    Location particleLoc = new Location(this.center.getWorld(), x, this.center.getY(), z);
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1.0F);
                    particleLoc.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 1, dustOptions);
                }
                this.ticksPassed += 5;
                if (this.ticksPassed >= this.particlesDuration) {
                    cancel();
                    for (i = 0; i < particleCount; i++) {
                        double angle = 6.283185307179586D * i / particleCount;
                        double x = this.center.getX() + radius * Math.cos(angle);
                        double z = this.center.getZ() + radius * Math.sin(angle);
                        Location particleLoc = new Location(this.center.getWorld(), x, this.center.getY(), z);
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.ORANGE, 1.0F);
                        particleLoc.getWorld().spawnParticle(Particle.REDSTONE, particleLoc, 0, dustOptions);
                    }
                }
            }
        }).runTaskTimer(PowerPlugin.plugin, 0L, 5L);
    }
}
