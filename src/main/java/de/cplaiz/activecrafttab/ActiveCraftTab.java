package de.cplaiz.activecrafttab;

import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public final class ActiveCraftTab extends JavaPlugin {

    private static ActiveCraftTab plugin;
    private static FileConfig mainConfig;

    public ActiveCraftTab() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        TabPluginManager.init();
        saveDefaultConfig();

        mainConfig = new FileConfig("config.yml", this);

        startUpdateTimer();
    }

    @Override
    public void onDisable() {

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
                if (mainConfig.getBoolean("show-ping")) {
                    Scoreboard pingBoard = Bukkit.getScoreboardManager().getNewScoreboard();
                    Objective ping = pingBoard.registerNewObjective("ping", "dummy");
                    ping.setDisplaySlot(DisplaySlot.PLAYER_LIST);
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.setScoreboard(pingBoard);
                        Score score = ping.getScore(players);
                        score.setScore(players.getPing());
                    }
                }
            }
        };
        runnable.runTaskTimer(ActiveCraftTab.plugin, 0, refreshInterval);
    }


    public static ActiveCraftTab getPlugin() {
        return plugin;
    }

    public static FileConfig getMainConfig() {
        return ActiveCraftTab.mainConfig;
    }

    public static void setMainConfig(FileConfig mainConfig) {
        ActiveCraftTab.mainConfig = mainConfig;
    }
}
