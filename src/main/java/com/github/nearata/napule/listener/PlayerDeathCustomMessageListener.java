package com.github.nearata.napule.listener;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.config.ModuleConfig;

public final class PlayerDeathCustomMessageListener implements Listener
{
    private final Napule plugin;

    public PlayerDeathCustomMessageListener(Napule pluginIn)
    {
        this.plugin = pluginIn;
    }

    @EventHandler
    public final void playerDeath(final PlayerDeathEvent event)
    {
        if (!(ModuleConfig.isEnabled(this.plugin.getAdminModule("player_death_custom_message"))))
        {
            return;
        }

        if (!(event.getEntity().getKiller() instanceof Player))
        {
            return;
        }

        final Player killer = event.getEntity().getKiller();

        if (this.plugin.getServer().getPluginManager().getPlugin("Parties").isEnabled())
        {
            final PartiesAPI api = Parties.getApi();

            if (!api.getPartyPlayer(killer.getUniqueId()).getPartyName().isEmpty())
            {
                return;
            }
        }

        final String killerHealth = String.format(ChatColor.RED + "(%s \u2764 | %s pot)", new DecimalFormat("0.00").format(killer.getHealth()),
                killer.getInventory().all(Material.SPLASH_POTION).size());
        final String deathMessage = String.format("%s %s", event.getDeathMessage(), killerHealth);

        event.setDeathMessage(deathMessage);
    }
}
