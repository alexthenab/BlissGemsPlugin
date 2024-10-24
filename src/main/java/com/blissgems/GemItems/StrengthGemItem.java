package com.blissgems.GemItems;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class StrengthGemItem {

    public static ItemStack StrengthGem;

    public static void init() {
        createStrengthGem();

    }

    private static void createStrengthGem() {
        ItemStack item = new ItemStack(Material.BONE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§4§lStrength Gem");
        List<String> lore = new ArrayList<>();
        lore.add("§5Passive Abilities:");
        lore.add("§4Strength:");
        lore.add("§4Grants Permanent Strength I in offhand");
        lore.add("§8Sharpening:");
        lore.add("§8Enchants Weapons with Sharpness V every 30s");
        lore.add("§7Weaken:");
        lore.add("§7Inflict Wither I & Weakness I for 30s, 90s cooldown");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.SHARPNESS, 5, false);
        item.setItemMeta(meta);
        StrengthGem = item;
    }

}