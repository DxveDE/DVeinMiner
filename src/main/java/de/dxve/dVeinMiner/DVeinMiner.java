package de.dxve.dVeinMiner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.dxve.dVeinMiner.commands.DVeinMinerCommand;
import de.dxve.dVeinMiner.listener.BreakListener;
import de.dxve.dVeinMiner.listener.JoinListener;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * DVeinMiner by DxveDE
 *
 * @author DxveDE
 * @version 1.0.0
 */
public final class DVeinMiner extends JavaPlugin {

    @Getter private static DVeinMiner instance;
    @Getter private final List<Material> ores = List.of(
            Material.COAL_ORE,
            Material.COPPER_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,

            Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE,

            Material.ANCIENT_DEBRIS,
            Material.NETHER_QUARTZ_ORE,
            Material.NETHER_GOLD_ORE
    );
    @Getter private List<String> whiteWorlds;
    @Getter private List<String> blackWorlds;
    @Getter private int maxBlocks;
    @Getter private boolean updateCheck;
    @Getter private boolean deepslateConnection;

    @Override
    public void onLoad() {
        instance = this;

        getLogger().info("◢◤ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ◥◣");
        getLogger().info(" ");
        getLogger().info("  »  " + getPluginMeta().getName() + " v" + this.getPluginMeta().getVersion() + " by " + this.getPluginMeta().getAuthors());
        getLogger().info(" ");
        getLogger().info("  »  Plugin was loaded.");
        getLogger().info(" ");
        getLogger().info("◥◣ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ◢◤");
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Init variables
        whiteWorlds = (getConfig().getBoolean("settings.world-whitelist.enabled") ? getConfig().getStringList("settings.world-whitelist.worlds") : null);
        blackWorlds = (getConfig().getBoolean("settings.world-blacklist.enabled") ? getConfig().getStringList("settings.world-blacklist.worlds") : null);
        if(getConfig().getInt("settings.max-blocks") < 0) getConfig().set("settings.max-blocks", 64);
        maxBlocks = getConfig().getInt("settings.max-blockamount");
        updateCheck = getConfig().getBoolean("settings.update-checker");
        deepslateConnection = getConfig().getBoolean("settings.deepslate-connection");

        // Init commands & events
        getCommand("dveinminer").setExecutor(new DVeinMinerCommand());
        getCommand("dveinminer").setTabCompleter(new DVeinMinerCommand());
        Bukkit.getPluginManager().registerEvents(new BreakListener(), this);
        if(updateCheck) Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

        // finished
        getLogger().info("◢◤ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ◥◣");
        getLogger().info(" ");
        getLogger().info("  »  " + getPluginMeta().getName() + " v" + this.getPluginMeta().getVersion() + " by " + this.getPluginMeta().getAuthors());
        getLogger().info(" ");
        getLogger().info("  »  Plugin was enabled.");
        getLogger().info(" ");
        getLogger().info("◥◣ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ◢◤");
        if(updateCheck) {
            getLatestVersion(latestVersion -> {
                String currentVersion = getPluginMeta().getVersion();
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    getLogger().info(" ");
                    getLogger().info("  »  An update with version " + latestVersion + " is available!");
                    getLogger().info("  »  Download: https://github.com/DxveDE/" + getPluginMeta().getName() + "/releases/latest");
                    getLogger().info(" ");
                    getLogger().info("◥◣ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ◢◤");
                }
            });
        }
    }

    @Override
    public void onDisable() {
        //saveConfig();

        getLogger().info("◢◤ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ◥◣");
        getLogger().info(" ");
        getLogger().info("  »  " + getPluginMeta().getName() + " v" + this.getPluginMeta().getVersion() + " by " + this.getPluginMeta().getAuthors());
        getLogger().info(" ");
        getLogger().info("  »  Plugin was disabled.");
        getLogger().info(" ");
        getLogger().info("◥◣ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ◢◤");
    }

    public ItemStack dVeinMinerPickaxe(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text("§4DVeinMiner §8»§f Pickaxe"));
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(DVeinMiner.getInstance(), "dveinminer"), PersistentDataType.BOOLEAN, true);
        item.setItemMeta(itemMeta);
        return item;
    }

    public String getConfigMessage(String path) {
        return (getConfig().getString(path).replace("%prefix%", getConfig().getString("prefix"))).replace("&", "§");
    }

    public void getLatestVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                URL url = new URL("https://api.github.com/repos/DxveDE/" + getPluginMeta().getName() + "/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                    JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                    String latestVersion = json.get("tag_name").getAsString();
                    consumer.accept(latestVersion);
                }
            } catch (Exception exception) {
                getLogger().warning("  »  Update-Check failed:");
                getLogger().warning(exception.getMessage());
            }
        });
    }
}