package com.github.nearata.napule.util.config;

import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.github.nearata.napule.Configuration;
import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.MessageUtil;
import com.github.nearata.napule.util.PlayerUtil;

public final class ScoreboardConfig
{
    private static final Napule PLUGIN = Napule.getInstance();
    private static final String SECTION_LABEL = "scoreboards";

    public static final void addUser(final UUID uuid)
    {
        if (!userExists(uuid))
        {
            final ConfigurationSection user = section().createSection(uuid.toString());

            user.set("scoreboard", true);

            config().save();
        }
    }

    public static final void removeUser(final UUID uuid)
    {
        if (userExists(uuid))
        {
            section().set(uuid.toString(), null);
            config().save();
        }
    }

    public static final boolean userExists(final UUID uuid)
    {
        return section().getConfigurationSection(uuid.toString()) != null;
    }

    public static final boolean isPlayerScoreboardEnabled(final UUID uuid)
    {
        return section().getConfigurationSection(uuid.toString()).getBoolean("scoreboard");
    }

    public static final void switchPlayerScoreboardState(final Player player)
    {
        final UUID uuid = player.getUniqueId();

        final boolean bool = isPlayerScoreboardEnabled(uuid) ? false : true;

        if (ModuleConfig.isEnabled(PLUGIN.getAdminModules().get("player_scoreboard")))
        {
            setPlayerScoreboardState(uuid.toString(), bool);
            config().save();
            PlayerUtil.playSuccessSound(player);
        }
        else
        {
            PlayerUtil.playFailedSound(player);
            player.sendMessage(MessageUtil.ERROR_FORMAT + "Le scoreboard sono state disattivate globalmente da un admin.");
        }
    }

    public static final void setPlayerScoreboardState(final String uuid, final boolean bool)
    {
        section().getConfigurationSection(uuid).set("scoreboard", bool);
    }

    private static final Configuration config()
    {
        return PLUGIN.getConfiguration(SECTION_LABEL);
    }

    private static final ConfigurationSection section()
    {
        return config().getConfig().getConfigurationSection(SECTION_LABEL);
    }
}
