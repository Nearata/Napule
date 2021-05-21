package com.github.nearata.napule.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.config.ModuleConfig;
import com.github.nearata.napule.util.config.SafeDropsConfig;

public final class PlayerNoDropsListener implements Listener
{
    private final Napule plugin = Napule.getInstance();
    
    @EventHandler
    public final void playerDeath(final PlayerDeathEvent event)
    {
        if (!ModuleConfig.isEnabled(this.plugin.getAdminModule("player_no_drops")))
        {
            return;
        }

        event.getDrops().clear();
    }

    @EventHandler
    public final void playerDropItem(final PlayerDropItemEvent event)
    {
        if (!ModuleConfig.isEnabled(this.plugin.getAdminModule("player_no_drops")))
        {
            return;
        }

        final Material itemDropType = event.getItemDrop().getItemStack().getType();

        if (SafeDropsConfig.isSafe(itemDropType))
        {
            return;
        }

        event.getItemDrop().remove();
    }
}
