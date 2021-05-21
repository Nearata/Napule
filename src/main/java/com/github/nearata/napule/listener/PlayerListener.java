package com.github.nearata.napule.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class PlayerListener implements Listener
{
    private final Server server = Bukkit.getServer();

    @EventHandler
    public final void playerInteract(final PlayerInteractEvent event)
    {
        if (event.getClickedBlock() == null)
        {
            return;
        }

        if (event.getClickedBlock().getType() == Material.WARPED_SIGN || event.getClickedBlock().getType() == Material.WARPED_WALL_SIGN)
        {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                Sign sign = (Sign) event.getClickedBlock().getState();

                if (sign.getLine(0).startsWith("kit"))
                {
                    String kitName = sign.getLine(0).replace("kit ", "");
                    Player player = event.getPlayer();

                    player.getInventory().clear();
                    this.server.dispatchCommand(Bukkit.getConsoleSender(), String.format("kit give %s %s", kitName, player.getName()));
                }
            }
        }
    }

    @EventHandler
    public final void blockBreak(final BlockBreakEvent event)
    {
        if (!event.getPlayer().hasPermission("napule.break"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public final void blockPlace(final BlockPlaceEvent event)
    {
        if (!event.getPlayer().hasPermission("napule.place"))
        {
            event.setCancelled(true);
        }
    }
}
