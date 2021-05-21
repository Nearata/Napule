package com.github.nearata.napule.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.alessiodp.parties.api.Parties;
import com.alessiodp.parties.api.interfaces.PartiesAPI;
import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.PlayerUtil;
import com.github.nearata.napule.util.config.ModuleConfig;

public final class FixPartyOpListener implements Listener
{
    private final Napule plugin;

    public FixPartyOpListener(Napule pluginIn)
    {
        this.plugin = pluginIn;
    }

    @EventHandler
    public final void entityDamageByEntity(final EntityDamageByEntityEvent event)
    {
        if (!ModuleConfig.isEnabled(this.plugin.getAdminModule("fix_party_op")))
        {
            return;
        }

        if (!this.plugin.getServer().getPluginManager().getPlugin("Parties").isEnabled())
        {
            return;
        }

        if (!(event.getEntity() instanceof Player))
        {
            return;
        }

        final Entity entity = event.getDamager();
        final Player victim = (Player) event.getEntity();
        final Player killer = PlayerUtil.getPlayerFromEntity(entity);

        if (killer == null || !(victim.isOp() || killer.isOp()))
        {
            return;
        }

        final PartiesAPI api = Parties.getApi();

        final String victimParty = api.getPartyPlayer(victim.getUniqueId()).getPartyName();
        final String killerParty = api.getPartyPlayer(killer.getUniqueId()).getPartyName();

        if (victimParty.isEmpty() || killerParty.isEmpty())
        {
            return;
        }

        if (victimParty.equalsIgnoreCase(killerParty))
        {
            event.setCancelled(true);
        }
    }
}
