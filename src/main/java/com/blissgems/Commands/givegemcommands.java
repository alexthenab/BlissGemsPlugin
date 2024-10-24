package com.blissgems.Commands;

import com.blissgems.GemItems.FireGemItem;
import com.blissgems.GemItems.StrengthGemItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class givegemcommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("givegemstrength")) {
            if (StrengthGemItem.StrengthGem != null) {
                player.getInventory().addItem(StrengthGemItem.StrengthGem);
                player.sendMessage("You have received a Strength Gem!");
            } else {
                player.sendMessage("Error: Strength Gem is not available.");
            }
        } else if (cmd.getName().equalsIgnoreCase("givegemfire")) {
            if (FireGemItem.FireGem != null) {
                player.getInventory().addItem(FireGemItem.FireGem);
                player.sendMessage("You have received a Fire Gem!");
            } else {
                player.sendMessage("Error: Fire Gem is not available.");
            }
        }

        return true;
    }
}
