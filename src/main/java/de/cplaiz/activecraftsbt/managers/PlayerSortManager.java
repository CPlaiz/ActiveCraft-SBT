package de.cplaiz.activecraftsbt.managers;

import de.cplaiz.activecraftsbt.ActiveCraftSBT;
import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerSortManager {

    public static void updatePlayerSort() {
        List<String> list = new ArrayList<>(ActiveCraftSBT.getMainConfig().getStringList("group-list-sort"));
        Scoreboard board = ActiveCraftSBT.getScoreboard();

        for (int i = 0; i < list.size(); i++) {

            if (board.getTeam(String.valueOf(i + 1)) == null)
                board.registerNewTeam(String.valueOf(i + 1));

            if (ActiveCraftSBT.getMainConfig().getBoolean("show-ping")) {
                if (board.getObjective("ping") == null) {
                    Objective ping = board.registerNewObjective("ping", "dummy");
                    ping.setDisplaySlot(DisplaySlot.PLAYER_LIST);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Score score = ping.getScore(player.getName());
                        score.setScore(player.getPing());
                        player.setScoreboard(board);
                    }
                } else {
                    Objective ping = board.getObjective("ping");
                    ping.setDisplaySlot(DisplaySlot.PLAYER_LIST);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Score score = ping.getScore(player.getName());
                        score.setScore(player.getPing());
                        player.setScoreboard(board);
                    }
                }
            }
        }


        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < list.size(); i++) {
                if (player.hasPermission("group." + list.get(i).toLowerCase()) && list.get(i).equalsIgnoreCase(getHighestGroup(player))) {
                    Team team = board.getTeam(String.valueOf(i + 1));
                    team.addEntry(player.getName());
                    player.setScoreboard(board);
                    break;
                }
            }
        }

    }

    private static String getHighestGroup(Player player) {
        for (String s : ActiveCraftSBT.getMainConfig().getStringList("group-list-sort"))
            if (player.hasPermission("group." + s.toLowerCase()))
                return s;
        return "default";
    }
}