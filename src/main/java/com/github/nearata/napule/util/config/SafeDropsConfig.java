package com.github.nearata.napule.util.config;

import java.util.List;

import org.bukkit.Material;

import com.github.nearata.napule.Configuration;
import com.github.nearata.napule.Napule;

public final class SafeDropsConfig
{
    private static final Napule PLUGIN = Napule.getInstance();
    private static final Configuration CONFIG = PLUGIN.getConfiguration("safe_drops");
    
    public static final List<String> getItems()
    {
        return section();
    }
    
    public static final boolean isSafe(final Material material)
    {
        return section().stream().anyMatch(itemName -> material.equals(Material.matchMaterial(itemName)));
    }

    private static final List<String> section()
    {
        return CONFIG.getConfig().getStringList(CONFIG.getSectionName());
    }
}
