package de.cplaiz.activecrafttab.commands;

import de.cplaiz.activecrafttab.ActiveCraftTab;
import de.silencio.activecraftcore.commands.ActiveCraftCommand;
import de.silencio.activecraftcore.exceptions.ActiveCraftException;
import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class TestCommand extends ActiveCraftCommand  {

    public TestCommand() {
        super("testcommand");
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args) throws ActiveCraftException {

        FileConfig mainConfig = new FileConfig("ActiveCraft-Tab", "config.yml");
        Player player = getPlayer(sender);

        StringBuilder headerBuilder = new StringBuilder();
        StringBuilder footerBuilder = new StringBuilder();

        //ActiveCraftTab.getPlugin().reloadConfig();
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

        player.setPlayerListHeader(headerBuilder.toString());
        player.setPlayerListFooter(footerBuilder.toString());


    }

    @Override
    public List<String> onTab(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}