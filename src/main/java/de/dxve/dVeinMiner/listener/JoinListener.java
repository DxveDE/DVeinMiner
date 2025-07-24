package de.dxve.dVeinMiner.listener;

import de.dxve.dVeinMiner.DVeinMiner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * DVeinMiner by DxveDE
 *
 * @author DxveDE
 * @version 1.0.0
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.isOp()) {
            DVeinMiner.getInstance().getLatestVersion(latestVersion -> {
                String currentVersion = DVeinMiner.getInstance().getPluginMeta().getVersion();
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    player.sendMessage("§8◢◤ §7§m                                                  §8 ◥◣");
                    player.sendMessage(" ");
                    player.sendMessage("§8  »  §7An update for §4" + DVeinMiner.getInstance().getPluginMeta().getName() + "§7 is §navailable§8!");
                    player.sendMessage("§8  »  §7Current version: §4" + DVeinMiner.getInstance().getPluginMeta().getVersion());
                    player.sendMessage("§8  »  §7New version: §4" + latestVersion);
                    player.sendMessage(" ");
                    player.sendMessage("§8  »  §7Download: https://github.com/DxveDE/DVeinMiner/releases/latest");
                    player.sendMessage(" ");
                    player.sendMessage("§8◥◣ §7§m                                                  §8 ◢◤");
                }
            });
        }
    }
}
