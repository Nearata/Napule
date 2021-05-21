package com.github.nearata.napule.util;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.github.nearata.napule.Napule;

import net.md_5.bungee.api.ChatColor;

public final class ScoreboardUtil
{
    private static final Napule PLUGIN = Napule.getInstance();

    public static final void setScoreboard(final Player player)
    {
        final Scoreboard scoreboard = newScoreboard();
        final Objective objective = scoreboard.registerNewObjective("napule", "napule", ChatColor.AQUA + "\u00A78>> \u00A7bNapule \u00A78<<");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        final Team teamTps = scoreboard.registerNewTeam("tps");
        teamTps.addEntry(ChatColor.BLACK + "");
        teamTps.setPrefix(ChatColor.GRAY + "" + ChatColor.BOLD + "Tps: ");
        teamTps.setSuffix(Util.getTpsColor());
        objective.getScore(ChatColor.BLACK + "").setScore(15);

        final Team teamPing = scoreboard.registerNewTeam("ping");
        teamPing.addEntry(ChatColor.AQUA + "");
        teamPing.setPrefix(ChatColor.GRAY + "" + ChatColor.BOLD + "Ping: ");
        teamPing.setSuffix("...");
        objective.getScore(ChatColor.AQUA + "").setScore(14);

        player.setScoreboard(scoreboard);
    }

    public static final void removeScoreboard(final Player player)
    {
        player.setScoreboard(newScoreboard());
    }

    private static final Scoreboard newScoreboard()
    {
        return PLUGIN.getServer().getScoreboardManager().getNewScoreboard();
    }
}
