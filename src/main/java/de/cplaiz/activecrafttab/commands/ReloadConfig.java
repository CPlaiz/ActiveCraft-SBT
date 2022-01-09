package de.cplaiz.activecrafttab.commands;

import de.cplaiz.activecrafttab.ActiveCraftTab;
import de.silencio.activecraftcore.commands.ActiveCraftCommand;
import de.silencio.activecraftcore.exceptions.ActiveCraftException;
import de.silencio.activecraftcore.utils.ComparisonType;
import de.silencio.activecraftcore.utils.FileConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadConfig extends ActiveCraftCommand {

    public ReloadConfig() {
        super("reloadtab");
    }

    @Override
    public void runCommand(CommandSender sender, Command command, String label, String[] args) throws ActiveCraftException {
        checkPermission(sender, "reload");
        checkArgsLength(args, ComparisonType.EQUAL, 0);
        ActiveCraftTab.setMainConfig(new FileConfig("config.yml", ActiveCraftTab.getPlugin()));
        sendMessage(sender, ChatColor.GOLD + "Reloaded configuration.");
    }

    @Override
    public List<String> onTab(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }

}
