package com.github.nearata.napule.util.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.github.nearata.napule.AdminModule;
import com.github.nearata.napule.Configuration;
import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.MessageUtil;
import com.github.nearata.napule.util.PlayerUtil;

public final class ModuleConfig
{
    private static final Napule PLUGIN = Napule.getInstance();
    private static final String SECTION_LABEL = "modules";
    
    public static final boolean isEnabled(final AdminModule module)
    {
        return section().getBoolean(module.getKey());
    }
    
    public static final void switchState(final AdminModule module, final Player player)
    {
        try
        {
            section().set(module.getKey(), switchBoolean(module));
            config().save();
            PlayerUtil.playSuccessSound(player);
        }
        catch (final Exception e)
        {
            player.sendMessage(MessageUtil.ERROR_FORMAT + "Impossibile effettuare questa operazione");
            PlayerUtil.playFailedSound(player);
        }
    }
    
    private static final boolean switchBoolean(final AdminModule module)
    {
        return isEnabled(module) ? false : true;
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
