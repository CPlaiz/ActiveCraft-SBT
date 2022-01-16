package de.cplaiz.activecraftsbt.managers;

import de.cplaiz.activecraftsbt.ActiveCraftSBT;
import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class ScoreboardManager {

    public static void updateScoreboard(Player player) {
        FileConfig mainConfig = new FileConfig("ActiveCraft-SBT", "config.yml");
        List<String> scoreBoardList = mainConfig.getStringList("board");
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        if (mainConfig.getString("title") == null) {
            objective.setDisplayName(" ");
        } else {
            objective.setDisplayName(mainConfig.getString("title"));
        }

        for (int i = 0; i < scoreBoardList.size(); i++) {
            int scoreSize = scoreBoardList.size() - i;

            String rawScore = ActiveCraftSBT.usePlaceholderAPI ? me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, scoreBoardList.get(i)) : scoreBoardList.get(i);

            Score score = objective.getScore(rawScore.length() > 40 ? rawScore.substring(0, 39) : rawScore);
            score.setScore(scoreSize);
        }
        player.setScoreboard(board);
    }
}