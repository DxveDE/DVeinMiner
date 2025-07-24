package de.dxve.dVeinMiner.listener;

import de.dxve.dVeinMiner.DVeinMiner;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * DVeinMiner by DxveDE
 *
 * @author DxveDE
 * @version 1.0.0
 */
public class BreakListener implements Listener {

    FileConfiguration config = DVeinMiner.getInstance().getConfig();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block startBlock = event.getBlock();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if(!tool.getType().toString().endsWith("_PICKAXE")) return;
        if(!tool.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(DVeinMiner.getInstance(), "dveinminer"))) return;
        if(DVeinMiner.getInstance().getWhiteWorlds() != null) {
            if(!DVeinMiner.getInstance().getWhiteWorlds().isEmpty() && !DVeinMiner.getInstance().getWhiteWorlds().contains(player.getWorld().getName())) {
                if(!config.getBoolean("settings.bypass.enabled")) return;
                if(!player.hasPermission(config.getString("settings.bypass.permission"))) return;
            }
        }
        if(DVeinMiner.getInstance().getBlackWorlds() != null) {
            if(!DVeinMiner.getInstance().getBlackWorlds().isEmpty() && DVeinMiner.getInstance().getBlackWorlds().contains(player.getWorld().getName())) {
                if(!config.getBoolean("settings.bypass.enabled")) return;
                if(!player.hasPermission(config.getString("settings.bypass.permission"))) return;
            }
        }
        if(config.getBoolean("settings.permission-worlds.enabled")) {
            if(!player.hasPermission(config.getString("settings.permission-worlds.permission").replace("%worldname%", player.getWorld().getName().toLowerCase()))) return;
        }
        if(config.getBoolean("settings.sneak-needed") && !player.isSneaking()) return;
        if(!DVeinMiner.getInstance().getOres().contains(startBlock.getType())) return;

        int unbreakingLevel = tool.getEnchantmentLevel(Enchantment.UNBREAKING);
        Set<Block> vein = findConnectedOres(startBlock, startBlock.getType(), DVeinMiner.getInstance().getMaxBlocks());
        int durabilityLosses = 0;
        for (Block b : vein) {
            if (b.equals(startBlock)) continue;

            b.breakNaturally(tool);
            player.incrementStatistic(org.bukkit.Statistic.MINE_BLOCK, b.getType());

            if (!tool.getItemMeta().isUnbreakable() && shouldDamageTool(unbreakingLevel)) {
                durabilityLosses++;
            }
        }
        if (durabilityLosses > 0) {
            short durability = tool.getDurability();
            short maxDurability = tool.getType().getMaxDurability();
            durability += durabilityLosses;
            if (durability >= maxDurability) {
                player.getInventory().setItemInMainHand(null);
                player.playSound(player.getEyeLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            } else {
                tool.setDurability(durability);
                player.getInventory().setItemInMainHand(tool);
            }
        }
    }

    private boolean shouldDamageTool(int unbreakingLevel) {
        if (unbreakingLevel <= 0) return true;
        return (new Random().nextInt(unbreakingLevel + 1) == 0);
    }

    private Set<Block> findConnectedOres(Block start, Material type, int max) {
        Set<Block> found = new HashSet<>();
        Queue<Block> toCheck = new LinkedList<>();
        toCheck.add(start);

        while (!toCheck.isEmpty() && found.size() < max) {
            Block b = toCheck.poll();
            String deepOre = (type.toString().startsWith("DEEPSLATE_") ? type.toString().replace("DEEPSLATE_", "") : "DEEPSLATE_" + type.toString());
            if ((DVeinMiner.getInstance().isDeepslateConnection() ? (b.getType() != type && b.getType() != Material.valueOf(deepOre)) : b.getType() != type) || found.contains(b)) continue;

            found.add(b);

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        Block neighbor = b.getRelative(dx, dy, dz);
                        if (!found.contains(neighbor) && (DVeinMiner.getInstance().isDeepslateConnection() ? (neighbor.getType() == type || neighbor.getType() == Material.valueOf(deepOre)) : neighbor.getType() == type)) {
                            toCheck.add(neighbor);
                        }
                    }
                }
            }
        }

        return found;
    }
}
