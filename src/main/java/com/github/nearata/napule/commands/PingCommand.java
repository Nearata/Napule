package com.github.nearata.napule.commands;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.packet.KeepAlivePacket;
import com.github.nearata.napule.util.MessageUtil;
import com.github.nearata.napule.util.Util;

public final class PingCommand
{
    private static final Napule plugin = Napule.getInstance();

    public static final void run(final CommandSender sender, final String[] args)
    {
        if (!sender.hasPermission("napule.command.user"))
        {
            sender.sendMessage(MessageUtil.ERROR_FORMAT + "Non puoi eseguire questo comando.");

            return;
        }

        if (args.length == 1)
        {
            final long latency = KeepAlivePacket.getPlayerLatency(((Player) sender).getUniqueId());

            if (latency == -1)
            {
                sender.sendMessage(MessageUtil.ERROR_FORMAT + "Ping non ancora calcolato.");
            }
            else
            {
                sender.sendMessage(
                        MessageUtil.getPrefix() + "Il tuo ping è: " + Util.getPingColor(latency));
            }

            return;
        }
        else if (args.length != 2)
        {
            sender.sendMessage(MessageUtil.getPrefix() + "/napule ping <nome_giocatore>");

            return;
        }

        final Optional<? extends Player> optionalPlayer = plugin.getServer().getOnlinePlayers().stream().filter(p -> p.getName().equalsIgnoreCase(args[1]))
                .findFirst();

        if (!optionalPlayer.isPresent())
        {
            sender.sendMessage(MessageUtil.ERROR_FORMAT + "Giocatore non trovato!");

            return;
        }

        final Player player = optionalPlayer.get();
        final UUID uuid = player.getUniqueId();

        final long latency = KeepAlivePacket.getPlayerLatency(uuid);

        if (latency == -1)
        {
            sender.sendMessage(MessageUtil.ERROR_FORMAT + "Ping non ancora calcolato.");
        }
        else
        {
            sender.sendMessage(
                    MessageUtil.getPrefix() + "Il ping di " + ChatColor.GRAY + player.getName() + ChatColor.RESET + " è: " + Util.getPingColor(latency));
        }

        return;
    }
}
