package me.mattia.maze.commands.subcommands;

import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.commands.AcceptedSender;
import me.mattia.maze.commands.SubCommand;
import me.mattia.maze.gui.MazeConfigGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    private final InfiniteMaze infiniteMaze;

    public ReloadCommand(InfiniteMaze plugin) {
        super("reload", "reload configuration files", "/maze reload", AcceptedSender.ALL, "");
        this.infiniteMaze = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.infiniteMaze.getConfigs().reloadAllConfigs();
        sender.spigot().sendMessage(this.infiniteMaze.getTextFormatter().formatText(
                infiniteMaze.getConfigs().getMessagesConfig().getConfig().getString(
                        "commands.reload_configurations", "%prefix% &7Configurations have been reloaded")
        ));
    }
}