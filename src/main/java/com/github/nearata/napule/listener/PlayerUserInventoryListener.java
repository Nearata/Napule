package com.github.nearata.napule.listener;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.inventories.CustomInventories;
import com.github.nearata.napule.util.PlayerUtil;
import com.github.nearata.napule.util.ScoreboardUtil;
import com.github.nearata.napule.util.config.ScoreboardConfig;

public final class PlayerUserInventoryListener implements Listener
{
    private final Napule plugin;

    public PlayerUserInventoryListener(Napule pluginIn)
    {
        this.plugin = pluginIn;
    }

    @EventHandler
    public final void inventoryClick(final InventoryClickEvent event)
    {
        final Player player = (Player) event.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        if (event.getInventory() != this.plugin.getUserInventory(uuid))
        {
            return;
        }

        event.setCancelled(true);

        final ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null || currentItem.getType() == Material.AIR)
        {
            return;
        }

        if (currentItem.getItemMeta().getDisplayName().equalsIgnoreCase("scoreboard"))
        {
            if (!ScoreboardConfig.isPlayerScoreboardEnabled(uuid))
            {
                ScoreboardUtil.setScoreboard(player);
            }
            else
            {
                ScoreboardUtil.removeScoreboard(player);
            }

            ScoreboardConfig.switchPlayerScoreboardState(player);
        }

        this.plugin.updateUserInventory(uuid, CustomInventories.createUserInventory(uuid));
        PlayerUtil.openNewInventory(player, this.plugin.getUserInventory(uuid));
    }

    @EventHandler
    public final void inventoryDrag(final InventoryDragEvent event)
    {
        if (event.getInventory() == this.plugin.getUserInventory(((Player) event.getWhoClicked()).getUniqueId()))
        {
            event.setCancelled(true);
        }
    }
}
