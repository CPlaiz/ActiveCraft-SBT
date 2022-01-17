package de.cplaiz.activecraftsbt;

import de.cplaiz.activecraftsbt.managers.PlayerListManager;
import de.cplaiz.activecraftsbt.managers.PlayerSortManager;
import de.cplaiz.activecraftsbt.managers.ScoreboardManager;
import de.silencio.activecraftcore.ActiveCraftCore;
import de.silencio.activecraftcore.Metrics;
import de.silencio.activecraftcore.playermanagement.Profile;
import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public final class ActiveCraftSBT extends JavaPlugin {

    private static ActiveCraftSBT plugin;
    private static FileConfig mainConfig;
    private static Scoreboard teamScoreboard;
    public static boolean usePlaceholderAPI;
    public static String PREFIX = ChatColor.GOLD + "[ActiveCraft-SBT] ";

    private static Scoreboard scoreboard;

    public ActiveCraftSBT() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        usePlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
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
        for (Profile profile : ActiveCraftCore.getProfiles().values()) profile.setPrefix("");
    }

    private void startUpdateTimer() {
        long refreshInterval = getMainConfig().getLong("refresh-interval-ticks");
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {

                mainConfig = new FileConfig("config.yml", ActiveCraftSBT.getPlugin());

                if (refreshInterval != getMainConfig().getInt("refresh-interval-ticks")) {
                    cancel();
                    startUpdateTimer();
                }

                if (getMainConfig().getBoolean("tab-module-enabled")) {
                    PlayerListManager.updatePlayerlist(new ArrayList<>(Bukkit.getOnlinePlayers()));
                    PlayerSortManager.updatePlayerSort();
                }


                if (getMainConfig().getBoolean("scoreboard-module-enabled")) {
                    for (Objective objective : scoreboard.getObjectives()) objective.unregister();
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

    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static Set<String> getEffectivePerms(Player player) {
        Set<String> effectivePerms = new TreeSet<>();
        for (PermissionAttachmentInfo perm : player.getEffectivePermissions())
            effectivePerms.add(perm.getPermission());
        return effectivePerms;
    }
}