package me.mattia.maze.commands.subcommands;

import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.commands.AcceptedSender;
import me.mattia.maze.commands.SubCommand;
import me.mattia.maze.gui.MazeConfigGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand extends SubCommand {
    private final InfiniteMaze infiniteMaze;

    public GuiCommand(InfiniteMaze plugin) {
        super("gui", "opens the configuration gui", "/maze gui", AcceptedSender.PLAYER, "");
        this.infiniteMaze = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MazeConfigGUI mazeConfigGUI = new MazeConfigGUI(this.infiniteMaze);
        mazeConfigGUI.getGui().show((Player) sender);
    }
}
