package me.mattia.maze.commands.subcommands;

import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.commands.AcceptedSender;
import me.mattia.maze.commands.CommandPermissions;
import me.mattia.maze.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HelpCommand extends SubCommand {
    private final InfiniteMaze infiniteMaze;

    public HelpCommand(InfiniteMaze plugin) {
        super("help", "shows this help menu", "&6/maze help", AcceptedSender.ALL, CommandPermissions.COMMAND_HELP_PERMISSION);
        this.infiniteMaze = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.infiniteMaze.getConfigs().reloadAllConfigs();
        sender.sendMessage("§8==============( §6§lInfinite Maze §8)==============");
        for(SubCommand cmd : this.infiniteMaze.getMazeCommandManager().getSubCommands()) {
            sender.sendMessage("§8■ §6" + infiniteMaze.getTextFormatter().basicFormat(cmd.getUsage()) + "§7: " + cmd.getDescription());
        }
        sender.sendMessage("§8============================================");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}