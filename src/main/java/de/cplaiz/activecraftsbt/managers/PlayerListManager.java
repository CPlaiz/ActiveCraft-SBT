package de.cplaiz.activecraftsbt.managers;

import de.cplaiz.activecraftsbt.ActiveCraftSBT;
import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerListManager {

    public static void updatePlayerlist(List<Player> players) {
        for (Player player : players) updatePlayerlist(player);
    }

    public static void updatePlayerlist(Player player) {

        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder footerBuilder = new StringBuilder();

        List<String> headerList = ActiveCraftSBT.getMainConfig().getStringList("tab-header");
        List<String> footerList = ActiveCraftSBT.getMainConfig().getStringList("tab-footer");

        for (int i = 0; i < headerList.size(); i++) {
            if (i != 0) headerBuilder.append("§r\n");
            headerBuilder.append(headerList.get(i));
        }

        for (int i = 0; i < footerList.size(); i++) {
            if (i != 0) footerBuilder.append("§r\n");
            footerBuilder.append(footerList.get(i));
        }

        player.setPlayerListHeader(ActiveCraftSBT.usePlaceholderAPI ?
                me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, headerBuilder.toString()) : headerBuilder.toString());
        player.setPlayerListFooter(ActiveCraftSBT.usePlaceholderAPI ?
                me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, footerBuilder.toString()) : footerBuilder.toString());
    }
}