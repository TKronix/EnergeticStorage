package net.seanomik.energeticstorage;

import net.minecraft.server.v1_14_R1.Recipes;
import net.seanomik.energeticstorage.commands.ESGiveCommand;
import net.seanomik.energeticstorage.commands.ESReloadCommand;
import net.seanomik.energeticstorage.files.ConfigFile;
import net.seanomik.energeticstorage.files.PlayersFile;
import net.seanomik.energeticstorage.listeners.BlockBreakListener;
import net.seanomik.energeticstorage.listeners.BlockPlaceListener;
import net.seanomik.energeticstorage.listeners.PlayerInteractListener;
import net.seanomik.energeticstorage.objects.ESSystem;
import net.seanomik.energeticstorage.tasks.HopperTask;
import net.seanomik.energeticstorage.utils.ItemRecipes;
import net.seanomik.energeticstorage.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.stream.events.Namespace;
import java.util.*;

public final class EnergeticStorage extends JavaPlugin implements Listener {
    private static EnergeticStorage plugin;
    private static HopperTask hopperTask;

    @Override
    public void onEnable() {
        plugin = this;

        registerCommands();
        registerListener();
        ItemRecipes.registerRecipes();

        ConfigFile.getConfig().saveDefaultConfig();
        PlayersFile.getConfig().saveDefaultConfig();

        Reference.ES_SYSTEMS = PlayersFile.getAllSystems();

        if (ConfigFile.isHopperInputEnabled()) {
            hopperTask = new HopperTask();
            hopperTask.runTaskTimerAsynchronously(this, 0L, 8L);
        }
    }

    private void registerCommands() {
        getCommand("esgive").setExecutor(new ESGiveCommand());
        getCommand("esreload").setExecutor(new ESReloadCommand());
    }

    private void registerListener() {
        getServer().getPluginManager().registerEvents(Reference.ES_TERMINAL_GUI, this);
        getServer().getPluginManager().registerEvents(Reference.ES_DRIVE_GUI, this);
        getServer().getPluginManager().registerEvents(Reference.ES_SYSTEM_SECURITY_GUI, this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    /*public void cachePlayersSystems(Player player) {
        if (PlayersFile.doesPlayerHaveSystem(player.getUniqueId())) {
            Reference.ES_SYSTEMS.put(player.getUniqueId(), PlayersFile.getPlayersSystems(player.getUniqueId()));
        }
    }*/

    /**
     * Saves all player systems.
     */
    private void savePlayerSystems() {
        for (Map.Entry<UUID, List<ESSystem>> systemEntry : Reference.ES_SYSTEMS.entrySet()) {
            PlayersFile.savePlayerSystems(systemEntry.getValue());
        }
    }

    @EventHandler
    public void onWorldSaveEvent(WorldSaveEvent event) {
        // Save the player systems when the world is saved
        savePlayerSystems();
    }

    // Adds all the custom recipes to the players crafting guide on join
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent join)
    {
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "silicon"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "basic_processor"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "improved_processor"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "advanced_processor"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "quartz_enriched_iron"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "processor_binding"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "raw_basic_processor"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "raw_improved_processor"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "raw_advanced_processor"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "machine_casing"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "1k_storage_part"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "4k_storage_part"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "16k_storage_part"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "64k_storage_part"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "256k_storage_part"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "1024k_storage_part"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "4096k_storage_part"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "16384k_storage_part"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_system"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_1k"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_4k"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_16k"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_64k"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_256k"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_1024k"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_4096k"));
        join.getPlayer().discoverRecipe(new NamespacedKey(EnergeticStorage.getPlugin(), "es_drive_16384k"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        // Save the player systems on shutdown to prevent item loss
        savePlayerSystems();
    }

    public static EnergeticStorage getPlugin() {
        return plugin;
    }

    public static HopperTask getHopperTask() {
        return hopperTask;
    }

    public static void setHopperTask(HopperTask hopperTask) {
        EnergeticStorage.hopperTask = hopperTask;
    }
}
