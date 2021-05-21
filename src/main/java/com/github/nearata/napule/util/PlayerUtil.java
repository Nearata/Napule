package com.github.nearata.napule.util;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.Inventory;
import org.bukkit.projectiles.ProjectileSource;

public final class PlayerUtil
{
    private static final float VOLUME = 1F;
    private static final float PITCH = 1F;

    public static final void playSuccessSound(final Player player)
    {
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, VOLUME, PITCH);
    }

    public static final void playFailedSound(final Player player)
    {
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, VOLUME, PITCH);
    }

    public static final void openNewInventory(final Player player, final Inventory inventory)
    {
        player.closeInventory();
        player.openInventory(inventory);
    }

    public static final Player getPlayerFromEntity(final Entity entity)
    {
        if (entity instanceof Player)
        {
            return (Player) entity;
        }
        else if (entity instanceof Projectile)
        {
            final Projectile projectile = (Projectile) entity;
            final ProjectileSource shooter = projectile.getShooter();

            if (shooter instanceof Player)
            {
                return (Player) shooter;
            }
        }

        return null;
    }
}
