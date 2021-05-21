package com.github.nearata.napule.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.CommandUtil;

public final class CommandTabCompleter implements TabCompleter
{
    private final Napule plugin = Napule.getInstance();

    @Override
    public final List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args)
    {
        final List<String> completions = new ArrayList<>();

        switch (args.length)
        {
        case 1:
            StringUtil.copyPartialMatches(args[0], CommandUtil.COMMANDS, completions);
            break;
        case 2:
            if (args[0].equalsIgnoreCase("ping"))
            {
                final List<String> players = this.plugin.getServer().getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList());
                StringUtil.copyPartialMatches(args[1], players, completions);
            }

            break;
        }

        Collections.sort(completions);

        return completions;
    }
}
