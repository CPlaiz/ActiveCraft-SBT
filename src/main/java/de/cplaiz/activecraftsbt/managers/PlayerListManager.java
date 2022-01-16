package de.cplaiz.activecraftsbt.managers;

import de.silencio.activecraftcore.utils.FileConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerListManager {

    public static void updatePlayerlist(List<Player> players) {
        for (Player player : players) updatePlayerlist(player);
    }

    public static void updatePlayerlist(Player player) {
        FileConfig mainConfig = new FileConfig("ActiveCraft-SBT", "config.yml");

        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder footerBuilder = new StringBuilder();

        List<String> headerList = mainConfig.getStringList("tab-header");
        List<String> footerList = mainConfig.getStringList("tab-footer");

        for (int i = 0; i < headerList.size(); i++) {
            if (i != 0) headerBuilder.append("§r\n");
            headerBuilder.append(headerList.get(i));
        }

        for (int i = 0; i < footerList.size(); i++) {
            if (i != 0) footerBuilder.append("§r\n");
            footerBuilder.append(footerList.get(i));
        }

        player.setPlayerListHeader(PlaceholderAPI.setPlaceholders(player, headerBuilder.toString()));
        player.setPlayerListFooter(PlaceholderAPI.setPlaceholders(player, footerBuilder.toString()));
    }
}