package de.cplaiz.activecraftsbt;

import de.cplaiz.activecraftsbt.managers.PlayerListManager;
import de.cplaiz.activecraftsbt.managers.PlayerSortManager;
import de.cplaiz.activecraftsbt.managers.ScoreboardManager;
import de.silencio.activecraftcore.Metrics;
import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public final class ActiveCraftSBT extends JavaPlugin {

    private static ActiveCraftSBT plugin;
    private static FileConfig mainConfig;
    private static Scoreboard teamScoreboard;
    public static boolean usePlaceholderAPI;
    public static String PREFIX = ChatColor.GOLD + "[ActiveCraft-SBT] ";

    public ActiveCraftSBT() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        usePlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        TabPluginManager.init();
        saveDefaultConfig();
        log("Plugin loaded.");
        mainConfig = new FileConfig("config.yml", this);
        startUpdateTimer();
        Metrics metrics = new Metrics(this, 13973);
    }

    @Override
    public void onDisable() {
        log("Plugin Unloaded.");
    }

    private void startUpdateTimer() {
        long refreshInterval = getMainConfig().getLong("refresh-interval-ticks");
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (refreshInterval != getMainConfig().getInt("refresh-interval-ticks")) {
                    cancel();
                    startUpdateTimer();
                }

                if (getMainConfig().getBoolean("tab-module-enabled")) {
                    PlayerListManager.updatePlayerlist(new ArrayList<>(Bukkit.getOnlinePlayers()));
                    PlayerSortManager.updatePlayerSort();
                }

                if (getMainConfig().getBoolean("scoreboard-module-enabled")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        ScoreboardManager.updateScoreboard(player);
                    }
                }
            }
        };
        runnable.runTaskTimer(ActiveCraftSBT.plugin, 0, refreshInterval);
    }


    public static ActiveCraftSBT getPlugin() {
        return plugin;
    }

    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }

    public static FileConfig getMainConfig() {
        return ActiveCraftSBT.mainConfig;
    }

    public static void setMainConfig(FileConfig mainConfig) {
        ActiveCraftSBT.mainConfig = mainConfig;
    }
}