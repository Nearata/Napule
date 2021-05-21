package com.github.nearata.napule.util;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;

import org.bukkit.ChatColor;

import com.github.nearata.napule.Napule;

public final class Util
{
    private static final Napule plugin = Napule.getInstance();

    public static final String titleCase(final String text)
    {
        if (text == null || text.isEmpty())
        {
            return text;
        }

        StringBuilder sb = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray())
        {
            if (Character.getType(ch) == Character.CONNECTOR_PUNCTUATION)
            {
                ch = " ".charAt(0);
            }

            if (Character.isSpaceChar(ch))
            {
                convertNext = true;
            }
            else if (convertNext)
            {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            }
            else
            {
                ch = Character.toLowerCase(ch);
            }

            sb.append(ch);
        }

        return sb.toString();
    }

    public static final long getPing(final Instant start, final Instant finish)
    {
        return Duration.between(start, finish).toMillis();
    }

    public static final String getPingColor(final long ms)
    {
        final String ping = String.valueOf(ms) + "ms";

        if (ms <= 59)
        {
            return ChatColor.GREEN + ping;
        }
        else if (ms <= 129)
        {
            return ChatColor.GOLD + ping;
        }
        else if (ms <= 199)
        {
            return ChatColor.RED + ping;
        }
        else
        {
            return ChatColor.BLACK + ping;
        }
    }

    public static final double getTps()
    {
        return plugin.getServer().getTPS()[0];
    }

    public static final String getTpsColor()
    {
        final double tps = getTps();
        final String tpsString = new DecimalFormat("0.0").format(tps);

        if (tps >= 18.0)
        {
            return ChatColor.GREEN + tpsString;
        }
        else if (tps >= 15.0)
        {
            return ChatColor.YELLOW + tpsString;
        }
        else
        {
            return ChatColor.RED + tpsString;
        }
    }
}
