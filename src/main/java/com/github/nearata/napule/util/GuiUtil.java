package com.github.nearata.napule.util;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class GuiUtil
{
    public static final ItemStack createItem(final Material materialIn, final String displayNameIn, final String... loreIn)
    {
        final ItemStack itemStack = new ItemStack(materialIn, 1);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(displayNameIn);
        itemMeta.setLore(Arrays.asList(loreIn));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
