package com.github.nearata.napule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.nearata.napule.commands.CommandTabCompleter;
import com.github.nearata.napule.commands.CommandsManager;
import com.github.nearata.napule.inventories.CustomInventories;
import com.github.nearata.napule.listener.AdminEnderpearlInventoryListener;
import com.github.nearata.napule.listener.AdminModulesInventoryListener;
import com.github.nearata.napule.listener.CommandsListener;
import com.github.nearata.napule.listener.EnderpearlListener;
import com.github.nearata.napule.listener.FixPartyOpListener;
import com.github.nearata.napule.listener.PlayerDeathCustomMessageListener;
import com.github.nearata.napule.listener.PlayerListener;
import com.github.nearata.napule.listener.PlayerNoDropsListener;
import com.github.nearata.napule.listener.PlayerScoreboardListener;
import com.github.nearata.napule.listener.PlayerUserInventoryListener;
import com.github.nearata.napule.packet.KeepAlivePacket;
import com.github.nearata.napule.runnable.ScoreboardRunnable;
import com.github.nearata.napule.util.MessageUtil;
import com.github.nearata.napule.util.config.ModuleConfig;

public final class Napule extends JavaPlugin
{
    private static Napule INSTANCE;
    private Inventory adminInventory;
    private Inventory adminModulesInventory;
    private Inventory adminEnderpearlInventory;
    private Map<UUID, Inventory> userInventories = new HashMap<>();
    private FileConfiguration pluginConfig;
    private ProtocolManager protocolManager;
    private BukkitTask scoreboardTpsTask;
    private Map<UUID, BukkitTask> scoreboardEnderpearlTasks = new HashMap<>();
    private Map<String, Configuration> configurations = new HashMap<>();

    private Map<String, AdminModule> adminModules = new HashMap<>();
    
    private Map<UUID, Integer> playerCombatLogCooldown = new HashMap<>();
    private Map<UUID, BukkitTask> playerCombatLogTasks = new HashMap<>();

    @Override
    public void onEnable()
    {
        INSTANCE = this;
        
        this.adminModules.put("player_no_drops", new AdminModule("ender_pearl", "Ender Pearl", "Attiva la personalizzazione del cooldown sulle ender pearl."));
        this.adminModules.put("fix_party_op", new AdminModule("fix_party_op", "Fix Party OP", "Fixa il danno tra OP mentre si è nello stesso party."));
        this.adminModules.put("player_no_drops", new AdminModule("player_no_drops", "Player No Drops", "Rimuove i drop dei player quando muoiono o quando vengono buttati a terra."));
        this.adminModules.put("player_death_custom_message", new AdminModule("player_death_custom_message", "Player Death Custom Message", "Alla morte di un player, se l'uccisore non è in un party, mostra cuori e pozioni rimanenti."));
        this.adminModules.put("player_scoreboard", new AdminModule("player_scoreboard", "Player Scoreboard", "Aggiunge ai player una scoreboard con diverse informazioni."));
        this.adminModules.put("combat_log", new AdminModule("combat_log", "Combat Log", "Previene la fuga in PvP uccidendo chi slogga."));

        final Configuration c1 = new Configuration(this.getDataFolder(), "scoreboards.yml");
        c1.create();
        this.configurations.put("scoreboards", c1);
        
        final Configuration c2 = new Configuration(this.getDataFolder(), "modules.yml");
        c2.create();
        
        final FileConfiguration c2Config = c2.getConfig();
        
        this.adminModules.forEach((moduleName, module) -> {
            final ConfigurationSection section = c2Config.getConfigurationSection(c2.getSectionName());
            final String key = module.getKey();

            if (section.get(key) == null)
            {
                section.set(key, true);
                c2.save();
            }
        });
        
        this.configurations.put("modules", c2);
        
        final Configuration c3 = new Configuration(this.getDataFolder(), "safe_drops.yml");
        c3.create();
        
        final FileConfiguration c3Config = c3.getConfig();
        
        final List<String> dropsList = c3Config.getStringList(c3.getSectionName());
        Arrays.asList("cooked_beef", "potion", "splash_potion", "diamond_sword", "netherite_sword").forEach(item -> {
            if (!dropsList.contains(item))
            {
                dropsList.add(item);
            }
        });
        
        if (!dropsList.isEmpty())
        {
            c3Config.set(c3.getSectionName(), dropsList);
            c3.save();
        }
        
        this.configurations.put("safe_drops", c3);

        this.getServer().getPluginManager().registerEvents(new AdminEnderpearlInventoryListener(this), this);
        this.getServer().getPluginManager().registerEvents(new CommandsListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EnderpearlListener(this), this);
        this.getServer().getPluginManager().registerEvents(new AdminModulesInventoryListener(this), this);
        this.getServer().getPluginManager().registerEvents(new FixPartyOpListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeathCustomMessageListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerNoDropsListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerScoreboardListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerUserInventoryListener(this), this);

        this.getCommand("napule").setExecutor(new CommandsManager());
        this.getCommand("napule").setTabCompleter(new CommandTabCompleter());

        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        this.pluginConfig = this.getConfig();

        this.adminInventory = CustomInventories.createAdminInventory();
        this.updateAdminModulesInventory();
        this.updateAdminEnderpearlInventory();

        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.protocolManager.addPacketListener(KeepAlivePacket.SERVER);
        this.protocolManager.addPacketListener(KeepAlivePacket.CLIENT);

        if (this.adminModules.containsKey("player_no_drops") && ModuleConfig.isEnabled(this.adminModules.get("player_no_drops")))
        {
            this.startScoreboardTpsRunnable();
        }
    }

    @Override
    public void onDisable()
    {
    }
    
    public static Napule getInstance()
    {
        return INSTANCE;
    }
    
    public final Map<String, AdminModule> getAdminModules()
    {
        return this.adminModules;
    }
    
    public final AdminModule getAdminModule(final String moduleName)
    {
        return this.adminModules.get(moduleName);
    }
    
    public final AdminModule getModuleByLabel(final String moduleLabel)
    {
        return this.adminModules.entrySet().stream().filter(module -> module.getValue().getLabel().equalsIgnoreCase(moduleLabel)).map(e -> e.getValue()).findFirst().get();
    }
    
    public final void registerAdminModule(final AdminModule adminModule)
    {
        final String key = adminModule.getKey();
        this.adminModules.put(key, adminModule);
    }

    public Inventory getAdminInventory()
    {
        return this.adminInventory;
    }

    public Inventory getAdminModulesInventory()
    {
        return this.adminModulesInventory;
    }

    public void updateAdminModulesInventory()
    {
        this.adminModulesInventory = CustomInventories.createAdminModulesInventory();
    }

    public Inventory getAdminEnderpearlInventory()
    {
        return this.adminEnderpearlInventory;
    }

    public void updateAdminEnderpearlInventory()
    {
        this.adminEnderpearlInventory = CustomInventories.createAdminEnderpearlInventory();
    }

    public Inventory getUserInventory(final UUID uuid)
    {
        return this.userInventories.get(uuid);
    }

    public void updateUserInventory(final UUID uuid, final Inventory inventory)
    {
        this.userInventories.put(uuid, inventory);
    }

    public void removeUserInventory(final UUID uuid)
    {
        if (this.userInventories.containsKey(uuid))
        {
            this.userInventories.remove(uuid);
        }
    }

    public final String getPluginPrefix()
    {
        return MessageUtil.getPrefix();
    }

    public FileConfiguration getPluginConfig()
    {
        return this.pluginConfig;
    }

    public ProtocolManager getProtocolManager()
    {
        return this.protocolManager;
    }

    public void stopScoreboardTpsRunnable()
    {
        this.scoreboardTpsTask.cancel();
    }

    public void startScoreboardTpsRunnable()
    {
        this.scoreboardTpsTask = new ScoreboardRunnable().runTaskTimer(this, 0, 20 * 60);
    }

    public Map<UUID, BukkitTask> getScoreboardEnderpearlTasks()
    {
        return this.scoreboardEnderpearlTasks;
    }

    public void cancelScoreboardEnderpearlTask(final UUID uuid)
    {
        if (this.scoreboardEnderpearlTasks.containsKey(uuid))
        {
            this.scoreboardEnderpearlTasks.get(uuid).cancel();
            this.scoreboardEnderpearlTasks.remove(uuid);
        }
    }

    public Configuration getConfiguration(final String configName)
    {
        return this.configurations.get(configName);
    }

    public Map<UUID, Integer> getPlayerCombatLogCooldown()
    {
        return this.playerCombatLogCooldown;
    }

    public Map<UUID, BukkitTask> getPlayerCombatLogTasks()
    {
        return this.playerCombatLogTasks;
    }

    public final void cancelPlayerCombatLogTask(final UUID uuid)
    {
        if (this.playerCombatLogTasks.containsKey(uuid))
        {
            this.playerCombatLogTasks.get(uuid).cancel();
            this.playerCombatLogTasks.remove(uuid);
        }
    }

    public void savePluginConfig()
    {
        this.saveConfig();
        this.reloadPluginConfig();
    }

    public void reloadPluginConfig()
    {
        this.reloadConfig();
        this.pluginConfig = this.getConfig();
    }
}
