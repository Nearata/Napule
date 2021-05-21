package com.github.nearata.napule.runnable;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.nearata.napule.Napule;

import net.md_5.bungee.api.ChatColor;

public final class CombatLogRunnable extends BukkitRunnable
{
    private final Napule plugin = Napule.getInstance();
    private final Player player;

    public CombatLogRunnable(Player playerIn)
    {
        this.player = playerIn;
    }

    @Override
    public void run()
    {
        final UUID uuid = this.player.getUniqueId();

        if (this.plugin.getPlayerCombatLogCooldown().containsKey(uuid))
        {
            int cd = this.plugin.getPlayerCombatLogCooldown().get(uuid);
            cd--;

            if (cd == 0)
            {
                this.player.sendMessage(ChatColor.GREEN + "Non sei più in combattimento.");
                this.plugin.getPlayerCombatLogCooldown().remove(uuid);
                this.plugin.cancelPlayerCombatLogTask(uuid);
            }
        }
    }
}
