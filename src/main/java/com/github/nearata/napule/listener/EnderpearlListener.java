package com.github.nearata.napule.listener;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.runnable.PlayerSetCooldownRunnable;
import com.github.nearata.napule.runnable.ScoreboardEnderpearlRunnable;
import com.github.nearata.napule.util.ConfigUtil;
import com.github.nearata.napule.util.config.ModuleConfig;
import com.github.nearata.napule.util.config.ScoreboardConfig;

import net.md_5.bungee.api.ChatColor;

public final class EnderpearlListener implements Listener
{
    private final Napule plugin;

    public EnderpearlListener(Napule pluginIn)
    {
        this.plugin = pluginIn;
    }

    @EventHandler
    public final void projectileLaunch(final ProjectileLaunchEvent event)
    {
        if (!ModuleConfig.isEnabled(this.plugin.getAdminModule("ender_pearl")))
        {
            return;
        }

        if (!(event.getEntity().getShooter() instanceof Player))
        {
            return;
        }

        if (!(event.getEntity().getType() == EntityType.ENDER_PEARL))
        {
            return;
        }

        final Player player = (Player) event.getEntity().getShooter();
        final UUID uuid = player.getUniqueId();
        final int cooldown = ConfigUtil.ENDER_PEARL.getCooldown();

        new PlayerSetCooldownRunnable(player, Material.ENDER_PEARL, cooldown).runTaskLater(plugin, 0);

        if (ModuleConfig.isEnabled(this.plugin.getAdminModule("player_scoreboard")) && ScoreboardConfig.isPlayerScoreboardEnabled(uuid))
        {
            this.plugin.getScoreboardEnderpearlTasks().put(uuid, new ScoreboardEnderpearlRunnable(player).runTaskTimerAsynchronously(plugin, 0, 2L));
        }
        else
        {
            final double pearlCd = player.getCooldown(Material.ENDER_PEARL);
            player.sendMessage(ChatColor.AQUA + "Enderpearl in cooldown: " + ChatColor.GOLD + new DecimalFormat("0.0").format(pearlCd / 20));
        }
    }

    @EventHandler
    public final void playerQuit(final PlayerQuitEvent event)
    {
        final UUID uuid = event.getPlayer().getUniqueId();
        this.plugin.cancelScoreboardEnderpearlTask(uuid);
    }
}
