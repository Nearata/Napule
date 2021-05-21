package com.github.nearata.napule.listener;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.packet.KeepAlivePacket;
import com.github.nearata.napule.util.ScoreboardUtil;
import com.github.nearata.napule.util.config.ModuleConfig;
import com.github.nearata.napule.util.config.ScoreboardConfig;

public final class PlayerScoreboardListener implements Listener
{
    private final Napule plugin;

    public PlayerScoreboardListener(Napule pluginIn)
    {
        this.plugin = pluginIn;
    }

    @EventHandler
    public final void playerJoin(final PlayerJoinEvent event)
    {
        final UUID uuid = event.getPlayer().getUniqueId();

        ScoreboardConfig.addUser(uuid);

        if (ModuleConfig.isEnabled(this.plugin.getAdminModule("player_scoreboard")) && ScoreboardConfig.isPlayerScoreboardEnabled(uuid))
        {
            ScoreboardUtil.setScoreboard(event.getPlayer());
        }
    }

    @EventHandler
    public final void playerQuit(final PlayerQuitEvent event)
    {
        final UUID uuid = event.getPlayer().getUniqueId();

        KeepAlivePacket.removePlayer(uuid);
        this.plugin.removeUserInventory(uuid);
    }
}
