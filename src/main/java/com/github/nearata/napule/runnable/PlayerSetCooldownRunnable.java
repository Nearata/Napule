package com.github.nearata.napule.runnable;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class PlayerSetCooldownRunnable extends BukkitRunnable
{
    private final Player player;
    private final Material material;
    private final int cooldown;

    public PlayerSetCooldownRunnable(final Player playerIn, final Material materialIn, final int cooldownIn)
    {
        this.player = playerIn;
        this.material = materialIn;
        this.cooldown = cooldownIn;
    }

    @Override
    public void run()
    {
        this.player.setCooldown(this.material, 20 * this.cooldown);
    }
}
