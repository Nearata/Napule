package com.github.nearata.napule.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.MessageUtil;

public final class AdminCommand
{
    private static final Napule plugin = Napule.getInstance();

    public static final void run(final CommandSender sender)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(MessageUtil.ERROR_FORMAT + "Solo i giocatori possono eseguire questo comando.");

            return;
        }

        final Player player = (Player) sender;

        if (!(player.isOp() || player.hasPermission("napule.command.admin")))
        {
            sender.sendMessage(MessageUtil.ERROR_FORMAT + "Non puoi eseguire questo comando.");

            return;
        }

        player.openInventory(plugin.getAdminInventory());
    }
}
