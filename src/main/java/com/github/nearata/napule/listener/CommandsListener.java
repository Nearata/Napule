package com.github.nearata.napule.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.commands.ReloadCommand;
import com.github.nearata.napule.util.PlayerUtil;

public final class CommandsListener implements Listener
{
    private final Napule plugin;

    public CommandsListener(Napule pluginIn)
    {
        this.plugin = pluginIn;
    }

    @EventHandler
    public final void inventoryClick(final InventoryClickEvent event)
    {
        if (event.getInventory() != this.plugin.getAdminInventory())
        {
            return;
        }

        event.setCancelled(true);

        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null || currentItem.getType() == Material.AIR)
        {
            return;
        }

        switch (currentItem.getItemMeta().getDisplayName().toLowerCase())
        {
        case "reload":
            ReloadCommand.run(player, true);
            break;
        case "moduli":
            PlayerUtil.openNewInventory(player, this.plugin.getAdminModulesInventory());
            break;
        case "ender pearl cd":
            PlayerUtil.openNewInventory(player, this.plugin.getAdminEnderpearlInventory());
            break;
        }
    }

    @EventHandler
    public final void inventoryDrag(final InventoryDragEvent event)
    {
        if (event.getInventory() == this.plugin.getAdminInventory())
        {
            event.setCancelled(true);
        }
    }
}
