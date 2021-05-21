package com.github.nearata.napule.packet;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.Util;
import com.github.nearata.napule.util.config.ModuleConfig;
import com.github.nearata.napule.util.config.ScoreboardConfig;

public final class KeepAlivePacket
{
    private static final Napule PLUGIN = Napule.getInstance();

    private static Map<UUID, Instant> PLAYERS = new HashMap<>();
    private static Map<UUID, Long> LATENCIES = new HashMap<>();

    public static final PacketAdapter SERVER = new PacketAdapter(PLUGIN, ListenerPriority.NORMAL, PacketType.Play.Server.KEEP_ALIVE) {
        @Override
        public void onPacketSending(final PacketEvent event)
        {
            final UUID uuid = event.getPlayer().getUniqueId();

            PLAYERS.put(uuid, Instant.now());
        }
    };

    public static final PacketAdapter CLIENT = new PacketAdapter(PLUGIN, ListenerPriority.NORMAL, PacketType.Play.Client.KEEP_ALIVE) {
        @Override
        public void onPacketReceiving(final PacketEvent event)
        {
            final Player player = event.getPlayer();
            final UUID uuid = player.getUniqueId();

            final Instant start = PLAYERS.get(uuid);
            final Instant finish = Instant.now();
            final long ms = Util.getPing(start, finish);

            LATENCIES.put(uuid, ms);

            if (ModuleConfig.isEnabled(PLUGIN.getAdminModule("player_scoreboard")) && ScoreboardConfig.isPlayerScoreboardEnabled(uuid))
            {
                final Team teamPing = player.getScoreboard().getTeam("ping");
                teamPing.setSuffix(Util.getPingColor(ms));
            }

            PLAYERS.remove(uuid);
        }
    };

    public static void removePlayer(final UUID uuid)
    {
        if (PLAYERS.containsKey(uuid))
        {
            PLAYERS.remove(uuid);
        }

        if (LATENCIES.containsKey(uuid))
        {
            LATENCIES.remove(uuid);
        }
    }

    public static long getPlayerLatency(final UUID uuid)
    {
        return LATENCIES.containsKey(uuid) ? LATENCIES.get(uuid) : -1;
    }
}
