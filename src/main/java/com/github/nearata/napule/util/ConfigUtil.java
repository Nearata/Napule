package com.github.nearata.napule.util;

import org.bukkit.entity.Player;

import com.github.nearata.napule.Napule;

public final class ConfigUtil
{
    private static Napule plugin = Napule.getInstance();

    public static final class ENDER_PEARL
    {
        public static final int getCooldown()
        {
            return plugin.getPluginConfig().getInt("enderpearl_cooldown");
        }

        public static final void setCooldown(final int seconds, final Player player)
        {
            try
            {
                plugin.getPluginConfig().set("enderpearl_cooldown", seconds);
                plugin.savePluginConfig();
                PlayerUtil.playSuccessSound(player);
            }
            catch (final Exception e)
            {
                player.sendMessage(MessageUtil.ERROR_FORMAT + "Impossibile effettuare questa operazione");
                PlayerUtil.playFailedSound(player);
            }
        }
    }
}
