package com.github.nearata.napule.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.MessageUtil;
import com.github.nearata.napule.util.PlayerUtil;

public final class ReloadCommand
{
    private static final Napule plugin = Napule.getInstance();

    public static final void run(final CommandSender sender, final boolean isPlayer)
    {
        if (!(sender.isOp() || sender.hasPermission("napule.command.admin")))
        {
            sender.sendMessage(MessageUtil.ERROR_FORMAT + "Non puoi eseguire questo comando.");

            return;
        }

        try
        {
            plugin.reloadPluginConfig();

            if (isPlayer)
            {
                PlayerUtil.playSuccessSound((Player) sender);
            }

            sender.sendMessage(plugin.getPluginPrefix() + ChatColor.GREEN + "File di configurazione ricaricato.");
        }
        catch (final Exception e)
        {
            if (isPlayer)
            {
                PlayerUtil.playFailedSound((Player) sender);
            }

            sender.sendMessage(plugin.getPluginPrefix() + ChatColor.RED + "Impossibile ricaricare il file di configurazione.");
        }
    }
}
