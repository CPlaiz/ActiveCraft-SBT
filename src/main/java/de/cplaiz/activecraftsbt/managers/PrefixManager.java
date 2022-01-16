package de.cplaiz.activecraftsbt.managers;

import de.cplaiz.activecraftsbt.ActiveCraftSBT;
import de.silencio.activecraftcore.ActiveCraftCore;
import de.silencio.activecraftcore.commands.ColorNickCommand;
import de.silencio.activecraftcore.events.*;
import de.silencio.activecraftcore.playermanagement.Profile;
import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class PrefixManager implements Listener {

    public static void updatePrefix(Player player) {
        if (player == null) return;
        ConfigurationSection configSection = ActiveCraftSBT.getMainConfig().getConfigurationSection("permission-groups");
        List<String> list = new ArrayList<>(configSection.getKeys(false));
        Profile profile = ActiveCraftCore.getProfile(player);
        for (String s : list) {
            String prefix = configSection.getString(s + ".prefix");
            if (player.hasPermission("group." + s.toLowerCase())) {
                profile.setPrefix(prefix);
                break;
            }
        }
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        PrefixManager.updatePrefix(event.getPlayer());
    }
}