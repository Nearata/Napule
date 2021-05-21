package com.github.nearata.napule.commands;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.inventories.CustomInventories;
import com.github.nearata.napule.util.MessageUtil;

public final class UserCommand
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

        if (!player.hasPermission("napule.command.user"))
        {
            sender.sendMessage(MessageUtil.ERROR_FORMAT + "Non puoi eseguire questo comando.");

            return;
        }

        final UUID uuid = player.getUniqueId();

        plugin.updateUserInventory(uuid, CustomInventories.createUserInventory(uuid));
        player.openInventory(plugin.getUserInventory(uuid));
    }
}
