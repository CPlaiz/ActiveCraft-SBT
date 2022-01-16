package de.cplaiz.activecraftsbt;

import de.cplaiz.activecraftsbt.managers.PrefixManager;
import de.silencio.activecraftcore.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TabPluginManager {

    private static HashMap<String, CommandExecutor> commands = new HashMap<>();
    private static List<Listener> listeners = new ArrayList<>();

    public static void init() {
        // general listeners
        addListeners(
            new PrefixManager()
        );

        //register ac commands
        registerCommands(
        );

        register();
    }

    private static void registerCommands(ActiveCraftCommand... activeCraftCommands) {
        for (ActiveCraftCommand activeCraftCommand : activeCraftCommands)
            for (String command : activeCraftCommand.getCommands())
                commands.put(command, activeCraftCommand);
    }

    private static void addListeners(Listener... listeners) {
        TabPluginManager.listeners.addAll(Arrays.asList(listeners));
    }

    private static void register() {
        for (Listener listener : listeners)
            Bukkit.getPluginManager().registerEvents(listener, ActiveCraftSBT.getPlugin());
        for (String cmd : commands.keySet()) {
            try {
                Bukkit.getPluginCommand(cmd).setExecutor(commands.get(cmd));
            } catch (NullPointerException e) {
                Bukkit.getLogger().severe("Error loading ActiveCraft-Command \"" + cmd + "\".");
                Bukkit.getLogger().severe("Please contact the developers about this issue.");
            }
        }
    }
}
