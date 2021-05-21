package com.github.nearata.napule.runnable;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.github.nearata.napule.Napule;

public final class ScoreboardEnderpearlRunnable extends BukkitRunnable
{
    private final Napule plugin = Napule.getInstance();
    private final Player player;
    private final String teamName = "enderpearl";

    public ScoreboardEnderpearlRunnable(Player playerIn)
    {
        this.player = playerIn;
    }

    @Override
    public void run()
    {
        final double cooldown = player.getCooldown(Material.ENDER_PEARL);
        final Scoreboard scoreboard = this.player.getScoreboard();
        final Objective objective = scoreboard.getObjective("napule");

        if (objective != null)
        {
            Team teamEnderpearl = scoreboard.getTeam(this.teamName);

            if (!scoreboard.getTeams().contains(teamEnderpearl))
            {
                final Score score = objective.getScore(ChatColor.BOLD + "");
                score.setScore(13);

                teamEnderpearl = scoreboard.registerNewTeam(this.teamName);
                teamEnderpearl.addEntry(ChatColor.BLUE + "");
                teamEnderpearl.setPrefix(ChatColor.GOLD + "" + ChatColor.BOLD + "Pearl: ");
                objective.getScore(ChatColor.BLUE + "").setScore(12);
            }

            if (cooldown > 0)
            {
                teamEnderpearl.setSuffix(new DecimalFormat("0.0").format(cooldown / 20));
            }
            else
            {
                teamEnderpearl.unregister();
                scoreboard.resetScores(ChatColor.BOLD + "");
                scoreboard.resetScores(ChatColor.BLUE + "");
            }
        }

        if (cooldown == 0)
        {
            this.plugin.cancelScoreboardEnderpearlTask(player.getUniqueId());
            this.cancel();
        }
    }
}
