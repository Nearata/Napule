package com.github.nearata.napule.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import com.github.nearata.napule.AdminModule;
import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.PlayerUtil;
import com.github.nearata.napule.util.ScoreboardUtil;
import com.github.nearata.napule.util.config.ModuleConfig;
import com.github.nearata.napule.util.config.ScoreboardConfig;

public final class AdminModulesInventoryListener implements Listener
{
    private final Napule plugin;

    public AdminModulesInventoryListener(Napule pluginIn)
    {
        this.plugin = pluginIn;
    }

    @EventHandler
    public final void inventoryClick(final InventoryClickEvent event)
    {
        if (event.getInventory() != this.plugin.getAdminModulesInventory())
        {
            return;
        }

        event.setCancelled(true);

        final ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null || currentItem.getType() == Material.AIR)
        {
            return;
        }

        final Player player = (Player) event.getWhoClicked();

        final String moduleName = currentItem.getItemMeta().getDisplayName();
        final AdminModule module = this.plugin.getModuleByLabel(moduleName);

        ModuleConfig.switchState(module, player);
        this.plugin.updateAdminModulesInventory();

        PlayerUtil.openNewInventory(player, this.plugin.getAdminModulesInventory());

        if (module.getKey().equalsIgnoreCase(this.plugin.getAdminModule(moduleName).getKey()))
        {
            if (ModuleConfig.isEnabled(module))
            {
                this.plugin.startScoreboardTpsRunnable();
            }
            else
            {
                this.plugin.stopScoreboardTpsRunnable();
            }

            this.plugin.getServer().getOnlinePlayers().forEach(p -> {
                if (ModuleConfig.isEnabled(module) && ScoreboardConfig.isPlayerScoreboardEnabled(p.getUniqueId()))
                {
                    ScoreboardUtil.setScoreboard(p);
                }
                else
                {
                    ScoreboardUtil.removeScoreboard(p);
                }
            });
        }
    }

    @EventHandler
    public final void inventoryDrag(final InventoryDragEvent event)
    {
        if (event.getInventory() == this.plugin.getAdminModulesInventory())
        {
            event.setCancelled(true);
        }
    }
}
