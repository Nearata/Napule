package com.github.nearata.napule.inventories;

import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import com.github.nearata.napule.Napule;
import com.github.nearata.napule.util.ConfigUtil;
import com.github.nearata.napule.util.GuiUtil;
import com.github.nearata.napule.util.Util;
import com.github.nearata.napule.util.config.ModuleConfig;
import com.github.nearata.napule.util.config.ScoreboardConfig;

public final class CustomInventories
{
    private static final Napule PLUGIN = Napule.getInstance();
    private static final Map<String, String> userModules = Map.of("scoreboard", "Attiva / Disattiva scoreboard.");

    public static final Inventory createAdminInventory()
    {
        final Inventory inventory = Bukkit.createInventory(null, 9, "Napule - Admin");

        inventory.addItem(GuiUtil.createItem(Material.GLASS_PANE, "Reload", "Ricarica il file di configurazione del plugin."));
        inventory.addItem(GuiUtil.createItem(Material.GLASS_PANE, "Moduli", "Attiva / Disattiva un modulo."));
        inventory.addItem(GuiUtil.createItem(Material.ENDER_PEARL, "Ender Pearl CD", "Cambia il cooldown delle ender pearl."));

        return inventory;
    }

    public static final Inventory createAdminModulesInventory()
    {
        final Inventory inventory = Bukkit.createInventory(null, 9, "Napule - Moduli");

        PLUGIN.getAdminModules().forEach((moduleName, module) -> {
            final Material material = ModuleConfig.isEnabled(module) ? Material.GREEN_WOOL : Material.RED_WOOL;

            inventory.addItem(GuiUtil.createItem(material, module.getLabel(), module.getDescription()));
        });

        return inventory;
    }

    public static final Inventory createAdminEnderpearlInventory()
    {
        final Inventory inventory = Bukkit.createInventory(null, 18, "Napule - Enderpearl Cooldown");

        IntStream.rangeClosed(2, 19).forEach(n -> {
            final boolean bool = ConfigUtil.ENDER_PEARL.getCooldown() == n;
            final Material material = bool ? Material.RED_WOOL : Material.GREEN_WOOL;
            final String lore = bool ? "Attuale" : "Imposta il cooldown a " + String.valueOf(n);

            inventory.addItem(GuiUtil.createItem(material, String.valueOf(n), lore));
        });

        return inventory;
    }

    public static final Inventory createUserInventory(final UUID uuid)
    {
        final Inventory inventory = Bukkit.createInventory(null, 9, "Napule - User");

        userModules.forEach((k, v) -> {
            final boolean bool = ModuleConfig.isEnabled(PLUGIN.getAdminModule("player_scoreboard")) && ScoreboardConfig.isPlayerScoreboardEnabled(uuid);
            final Material material = bool ? Material.GREEN_WOOL : Material.RED_WOOL;

            inventory.addItem(GuiUtil.createItem(material, Util.titleCase(k), v));
        });

        return inventory;
    }
}
