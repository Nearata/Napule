package com.github.nearata.napule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Configuration
{
    private final File dataFolder;
    private final String configName;
    private final String sectionName;

    private File file;
    private FileConfiguration config;

    public Configuration(File dataFolder, String fileName)
    {
        this.dataFolder = dataFolder;
        this.configName = fileName;
        this.sectionName = fileName.replace(".yml", "");
    }

    public final void create()
    {
        this.file = new File(this.dataFolder.toString(), this.configName);

        if (!this.file.exists())
        {
            try
            {
                this.file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        this.config = new YamlConfiguration();
        
        this.reload();
        
        if (this.config.getConfigurationSection(this.sectionName) == null)
        {
            this.config.createSection(this.sectionName);
            this.save();
        }
    }

    public final void save()
    {
        try
        {
            this.config.save(this.file);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        this.reload();
    }

    public final void reload()
    {
        try
        {
            this.config.load(this.file);
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        catch (final InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig()
    {
        return this.config;
    }
    
    public String getSectionName()
    {
        return this.sectionName;
    }
}
