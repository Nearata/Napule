package com.github.nearata.napule.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.nearata.napule.util.CommandUtil;
import com.github.nearata.napule.util.MessageUtil;

public final class CommandsManager implements CommandExecutor
{
    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
    {
        if (args.length == 0)
        {
            CommandUtil.COMMANDS.forEach(c -> {
                sender.sendMessage(ChatColor.AQUA + "/napule " + c);
            });

            return true;
        }

        final String base = args[0];

        if (CommandUtil.COMMANDS.stream().noneMatch(c -> c.equalsIgnoreCase(base)))
        {
            sender.sendMessage(MessageUtil.ERROR_FORMAT + "Questo comando non esiste.");

            return true;
        }

        switch (base.toLowerCase())
        {
        case "admin":
            AdminCommand.run(sender);
            break;
        case "reload":
            ReloadCommand.run(sender, false);
            break;
        case "user":
            UserCommand.run(sender);
            break;
        case "ping":
            PingCommand.run(sender, args);
            break;
        }

        return true;
    }
}
