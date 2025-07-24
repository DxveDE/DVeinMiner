package de.dxve.dVeinMiner.commands;

import de.dxve.dVeinMiner.DVeinMiner;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * DVeinMiner by DxveDE
 *
 * @author DxveDE
 * @version 1.0.0
 */
public class DVeinMinerCommand implements CommandExecutor, TabCompleter {

    private final List<String> pickaxes = Arrays.asList(
            Material.WOODEN_PICKAXE.toString(),
            Material.STONE_PICKAXE.toString(),
            Material.GOLDEN_PICKAXE.toString(),
            Material.IRON_PICKAXE.toString(),
            Material.DIAMOND_PICKAXE.toString(),
            Material.NETHERITE_PICKAXE.toString()
    );

    FileConfiguration config = DVeinMiner.getInstance().getConfig();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(config.getString("messages.command-only-players"));
            return true;
        }

        if (!player.hasPermission(config.getString("settings.permission"))) {
            player.sendMessage(DVeinMiner.getInstance().getConfigMessage("messages.no-permissions"));
            return true;
        }

        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("give")) {
                String materialType = args[1].toUpperCase();
                try {
                    Material pickaxe = Material.valueOf(materialType);
                    player.getInventory().addItem(DVeinMiner.getInstance().dVeinMinerPickaxe(pickaxe));
                    player.sendMessage(DVeinMiner.getInstance().getConfigMessage("messages.get-item"));
                } catch (IllegalArgumentException error) {
                    player.sendMessage(DVeinMiner.getInstance().getConfigMessage("messages.no-material-found"));
                    return true;
                }
            } else {
                player.sendMessage(DVeinMiner.getInstance().getConfigMessage("messages.syntax"));
            }
        } else {
            player.sendMessage(DVeinMiner.getInstance().getConfigMessage("messages.syntax"));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)) return Collections.emptyList();
        if(!player.hasPermission(config.getString("settings.permission"))) return Collections.emptyList();

        if(args.length == 1) {
            return Collections.singletonList("give");
        } else if(args.length == 2 && args[0].equalsIgnoreCase("give")) {
            List<String> completions = new ArrayList<>();
            for (String pickaxe : pickaxes) {
                if(pickaxe.toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(pickaxe);
                }
            }
            return completions;
        }
        return Collections.emptyList();
    }
}
