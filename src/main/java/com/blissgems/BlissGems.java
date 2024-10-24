package com.blissgems;
import com.blissgems.Commands.givegemcommands;
import com.blissgems.GemItems.FireGemItem;
import com.blissgems.GemItems.StrengthGemItem;
import com.blissgems.Gems.Strength.Powers;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlissGems extends JavaPlugin {


    @Override
    public void onEnable() {
        System.out.println("BlissGems has been enabled");
        // Plugin startup logic

        StrengthGemItem.init();
        FireGemItem.init();

        getCommand("givegemstrength").setExecutor(new givegemcommands()); // givegemfire cmd
        getCommand("givegemfire").setExecutor(new givegemcommands()); // givegemfire cmd

        getServer().getPluginManager().registerEvents(new com.blissgems.Gems.Strength.Powers(), this);
        getServer().getPluginManager().registerEvents(new com.blissgems.Gems.Fire.Powers(), this);

        Powers.startSharpeningTask(); // StrengthGem Sharpening Task

        // Need to add tasks for other gems n shi







    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("BlissGems has been disabled");

    }
}
