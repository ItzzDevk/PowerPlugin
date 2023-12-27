package me.itzzdevk.powerplugin.utils;

import me.itzzdevk.powerplugin.PowerPlugin;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ParticleHelper {

    public static void spawnTornado(Location location) {
        new BukkitRunnable() {
            int ticks = 0;
            double radius = 1.0;
            double heightIncrease = 0.25;
            List<Location> particleLocations = new ArrayList<>();

            @Override
            public void run() {
                ticks++;
                if (ticks > 60) {
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
}
