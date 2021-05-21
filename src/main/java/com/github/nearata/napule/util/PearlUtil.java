package com.github.nearata.napule.util;

import java.time.Duration;
import java.time.Instant;

public final class PearlUtil
{
    public static final boolean canUsePearl(Instant time)
    {
        return getSeconds(time) >= ConfigUtil.ENDER_PEARL.getCooldown();
    }

    public static final long remainingTime(Instant time)
    {
        long current = ConfigUtil.ENDER_PEARL.getCooldown() - getSeconds(time);

        if (current >= 0)
        {
            return current;
        }

        return 0;
    }

    public static final double getRemoveTime(double barProgress)
    {
        double time = (double) 1.0 / ConfigUtil.ENDER_PEARL.getCooldown();
        double progress = barProgress - time;

        if (progress < 0)
        {
            return 0;
        }

        return progress;
    }

    private static final long getSeconds(Instant playerTime)
    {
        return Duration.between(playerTime, Instant.now()).getSeconds();
    }
}
