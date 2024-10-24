package com.blissgems.GemItems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class FireGemItem {

    public static ItemStack FireGem;

    public static void init() {
        createFireGem();

    }

    private static void createFireGem() {
        ItemStack item = new ItemStack(Material.BONE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Fire Gem"); // add color and bold
        List<String> lore = new ArrayList<>();
        lore.add("§5Passive Abilities:");
        lore.add("Fire Resistance"); // need to add color
        lore.add("Grants Permanent Fire Resistance in Offhand"); // need to add color
        lore.add("Auto Smelting"); // add color
        lore.add("Auto Smelts Mined Ores"); // add color
        lore.add("Smelting Refinement"); // add color
        lore.add("Enchants Sword/bow with Fire Aspect II/Flame I"); //add color
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, false);
        item.setItemMeta(meta);
        FireGem = item;
    }

}
