package com.blissgems.Gems.Strength;

import com.blissgems.GemItems.StrengthGemItem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Powers implements Listener {

    private static final HashMap<UUID, Long> weakencooldown = new HashMap<>();
    private static final long WEAKEN_COOLDOWN_TIME = 90 * 1000; // 90 seconds
    private static final long SHARPEN_COOLDOWN_TIME = 600 * 1000; // 30 seconds
    private static final HashMap<UUID, Long> sharpencooldown = new HashMap<>();

    @EventHandler
    public void onEntityHitByPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();

            ItemStack mainHandItem = damager.getInventory().getItemInMainHand();
            ItemStack offHandItem = damager.getInventory().getItemInOffHand();

            if ((mainHandItem != null && mainHandItem.getItemMeta() != null &&
                    mainHandItem.getItemMeta().equals(StrengthGemItem.StrengthGem.getItemMeta())) ||
                    (offHandItem != null && offHandItem.getItemMeta() != null &&
                            offHandItem.getItemMeta().equals(StrengthGemItem.StrengthGem.getItemMeta()))) {

                UUID playerId = damager.getUniqueId();
                long currentTime = System.currentTimeMillis();

                if (weakencooldown.containsKey(playerId)) {
                    long timeSinceLastUse = currentTime - weakencooldown.get(playerId);
                    if (timeSinceLastUse < WEAKEN_COOLDOWN_TIME) {
                        damager.sendMessage("This ability is on cooldown! Please wait.");
                        return;
                    }
                }

                if (event.getEntity() instanceof Player) {
                    Player hitPlayer = (Player) event.getEntity();

                    // Apply Wither I for 30 seconds
                    hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 30 * 20, 0));

                    // Apply Weakness I for 30 seconds
                    hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 30 * 20, 0));

                    // Activate ability and start cooldown
                    damager.sendMessage("You have activated the Strength Gem §4Weaken ability!");
                    weakencooldown.put(playerId, currentTime);

                    // Start cooldown display
                    startWeakenCooldownDisplay(damager);
                }
            }
        }
    }

    private void startWeakenCooldownDisplay(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (weakencooldown.containsKey(player.getUniqueId())) {
                    long cooldownStart = weakencooldown.get(player.getUniqueId());
                    long timeRemaining = WEAKEN_COOLDOWN_TIME - (System.currentTimeMillis() - cooldownStart);

                    if (timeRemaining <= 0) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
                        cancel();
                    } else {
                        int seconds = (int) (timeRemaining / 1000) % 60;
                        int minutes = (int) (timeRemaining / 1000) / 60;
                        String message = String.format("Weaken: %02d:%02d", minutes, seconds);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("BlissGems"), 0L, 20L);
    }

    public static void startSharpeningTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    checkAndSharpenWeapons(player);
                    updateSharpenCooldownDisplay(player);
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("BlissGems"), 0L, 20L); // Run every 20 ticks
    }

    private static void checkAndSharpenWeapons(Player player) {
        long currentTime = System.currentTimeMillis();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                if (isWeapon(item.getType())) {
                    SharpenWeapon(item, player.getUniqueId());
                }
            }
        }
    }

    private static boolean isWeapon(Material material) {
        return material.toString().endsWith("_SWORD") || material.toString().endsWith("_AXE");
    }

    private static void SharpenWeapon(ItemStack item, UUID playerId) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            // Check if the item already has Sharpness III
            if (meta.hasEnchant(Enchantment.SHARPNESS) && meta.getEnchantLevel(Enchantment.SHARPNESS) == 3) {
                return; // Skip if the weapon already has Sharpness III
            }

            // Check if the weapon is still on cooldown
            if (sharpencooldown.containsKey(playerId)) {
                long lastApplied = sharpencooldown.get(playerId);
                long timeSinceLastUse = System.currentTimeMillis() - lastApplied;
                if (timeSinceLastUse < SHARPEN_COOLDOWN_TIME) {
                    return; // Skip if the cooldown is still active
                }
            }

            // Apply Sharpness III if it doesn't already have it
            meta.addEnchant(Enchantment.SHARPNESS, 3, false); // Sharpness III
            item.setItemMeta(meta);

            sharpencooldown.put(playerId, System.currentTimeMillis()); // Update cooldown
        }
    }


    private static void updateSharpenCooldownDisplay(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                long lastApplied = sharpencooldown.getOrDefault(player.getUniqueId(), 0L);
                long timeRemaining = SHARPEN_COOLDOWN_TIME - (currentTime - lastApplied);

                if (timeRemaining <= 0) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
                    cancel();
                } else {
                    int seconds = (int) (timeRemaining / 1000) % 60;
                    int minutes = (int) (timeRemaining / 1000) / 60;
                    String message = String.format("Sharpening: %02d:%02d", minutes, seconds);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("BlissGems"), 0L, 20L);
    }

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack offhandItem = player.getInventory().getItemInOffHand();

        if (offhandItem != null && offhandItem.getType() != Material.AIR && offhandItem.getItemMeta() != null &&
                offhandItem.getItemMeta().equals(StrengthGemItem.StrengthGem.getItemMeta())) {
            // Apply Strength I if Strength Gem is in offhand
            player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, 0, false, false));
        } else {
            // Remove Strength I if Strength Gem is not in offhand
            player.removePotionEffect(PotionEffectType.STRENGTH);
        }
    }

}
