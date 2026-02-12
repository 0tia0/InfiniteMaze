package me.mattia.maze.commands.subcommands;

import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.commands.AcceptedSender;
import me.mattia.maze.commands.CommandPermissions;
import me.mattia.maze.commands.SubCommand;
import me.mattia.maze.gui.MazeConfigGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GuiCommand extends SubCommand {
    private final InfiniteMaze infiniteMaze;

    public GuiCommand(InfiniteMaze plugin) {
        super("gui", "opens the configuration gui", "&6/maze gui", AcceptedSender.PLAYER, CommandPermissions.COMMAND_GUI_PERMISSION);
        this.infiniteMaze = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MazeConfigGUI mazeConfigGUI = new MazeConfigGUI(this.infiniteMaze);
        mazeConfigGUI.getGui().show((Player) sender);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
