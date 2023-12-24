package me.itzzdevk.powerplugin.commands;

import me.itzzdevk.powerplugin.PowerPlugin;
import me.itzzdevk.powerplugin.utils.RollPower;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RerollCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reroll")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.isOp()) {
                    int roll = RollPower.rollDice();
                    World world = player.getWorld();

                    ItemStack rewardItem = RollPower.getRewardItem(player, roll);

                    player.getInventory().remove(PowerPlugin.plugin.firepower);
                    player.getInventory().remove(PowerPlugin.plugin.healingpower);
                    player.getInventory().remove(PowerPlugin.plugin.windpower);
                    player.getInventory().remove(PowerPlugin.plugin.strengthpower);
                    player.getInventory().remove(PowerPlugin.plugin.lightningpower);

                    BukkitRunnable giveastrix = new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation().add(0.0D, 0.5D, 0.0D), 30, 0.4D, 1.0D, 0.4D, 0.0D);
                            player.getInventory().addItem(rewardItem);
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
            return true;
        }
        return false;
    }
}
