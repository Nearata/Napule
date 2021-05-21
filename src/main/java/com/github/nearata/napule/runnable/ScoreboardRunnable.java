package com.github.nearata.napule.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.Util;

public final class ScoreboardRunnable extends BukkitRunnable
{
    private final Napule plugin = Napule.getInstance();

    @Override
    public void run()
    {
        this.plugin.getServer().getOnlinePlayers().forEach(p -> {
            final Scoreboard scoreboard = p.getScoreboard();
            final Team teamTps = scoreboard.getTeam("tps");

            if (teamTps != null)
            {
                teamTps.setSuffix(Util.getTpsColor());
            }
        });
    }
}
