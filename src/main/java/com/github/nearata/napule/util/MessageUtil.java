package com.github.nearata.napule.util;

import org.bukkit.ChatColor;

public final class MessageUtil
{
    private static final String prefix = ChatColor.AQUA + "[Napule] " + ChatColor.RESET;

    public static final String ERROR_FORMAT = prefix + ChatColor.RED;

    public static final String SUCCESS_FORMAT = prefix + ChatColor.GREEN;

    public static final String getPrefix()
    {
        return prefix;
    }
}
