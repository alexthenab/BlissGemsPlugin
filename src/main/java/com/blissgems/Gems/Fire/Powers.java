package com.blissgems.Gems.Fire;

import com.blissgems.GemItems.FireGemItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Powers implements Listener {

    // Fire Resistance effect when holding Fire Gem
    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack offhandItem = player.getInventory().getItemInOffHand();

        if (isFireGem(offhandItem)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
            //player.sendMessage("Fire Resistance applied");
        } else {
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            //player.sendMessage("Fire Resistance removed");
        }
    }

    private boolean isFireGem(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getType() == Material.BONE) {
            ItemMeta meta = item.getItemMeta();
            // Simplified check for display name
            return meta != null && meta.getDisplayName().contains("Fire Gem");
        }
        return false;
    }

    // Automatically cook ores and food when picked up by player with Fire Gem
    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack offhandItem = player.getInventory().getItemInOffHand();

            if (isFireGem(offhandItem)) {
                ItemStack pickedItem = event.getItem().getItemStack();
                Material cookedMaterial = getSmeltedMaterial(pickedItem.getType());

                if (cookedMaterial != null) {
                    // Create a new ItemStack with the cooked item
                    ItemStack smeltedItem = new ItemStack(cookedMaterial, pickedItem.getAmount());
                    event.getItem().setItemStack(smeltedItem);
                }
            }
        }
    }

    // Smeltable versions of items
    private Material getSmeltedMaterial(Material material) {
        switch (material) {
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
            case RAW_IRON:
                return Material.IRON_INGOT;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
            case RAW_GOLD:
            case NETHER_GOLD_ORE:
                return Material.GOLD_INGOT;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
            case RAW_COPPER:
                return Material.COPPER_INGOT;
            case ANCIENT_DEBRIS:
                return Material.NETHERITE_SCRAP;
            case BEEF:
                return Material.COOKED_BEEF;
            case PORKCHOP:
                return Material.COOKED_PORKCHOP;
            case CHICKEN:
                return Material.COOKED_CHICKEN;
            case SALMON:
                return Material.COOKED_SALMON;
            case MUTTON:
                return Material.COOKED_MUTTON;
            case COD:
                return Material.COOKED_COD;
            case RABBIT:
                return Material.COOKED_RABBIT;
            case POTATO:
                return Material.BAKED_POTATO;
            default:
                return null;
        }
    }
}
