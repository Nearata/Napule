package com.github.nearata.napule.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.ConfigUtil;
import com.github.nearata.napule.util.MessageUtil;
import com.github.nearata.napule.util.PlayerUtil;

public final class AdminEnderpearlInventoryListener implements Listener
{
    private final Napule plugin;

    public AdminEnderpearlInventoryListener(Napule pluginIn)
    {
        this.plugin = pluginIn;
    }

    @EventHandler
    public final void inventoryClick(final InventoryClickEvent event)
    {
        if (event.getInventory() != this.plugin.getAdminEnderpearlInventory())
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

        if (currentItem.getItemMeta().getLore().get(0).equalsIgnoreCase("attuale"))
        {
            player.sendMessage(MessageUtil.ERROR_FORMAT + "Questo � il valore attuale.");
            PlayerUtil.playFailedSound(player);
            return;
        }

        ConfigUtil.ENDER_PEARL.setCooldown(Integer.valueOf(currentItem.getItemMeta().getDisplayName()), player);
        this.plugin.updateAdminEnderpearlInventory();

        PlayerUtil.openNewInventory(player, this.plugin.getAdminEnderpearlInventory());
    }

    @EventHandler
    public final void inventoryDrag(final InventoryDragEvent event)
    {
        if (event.getInventory() == this.plugin.getAdminEnderpearlInventory())
        {
            event.setCancelled(true);
        }
    }
}
