package com.blissgems.GemItems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AquaGemItem {

    public static ItemStack AquaGem;

    public static void init() {

    }

    private static void createAquaGem() {
        ItemStack item = new ItemStack(Material.BONE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bAqua Gem");
    }

 }