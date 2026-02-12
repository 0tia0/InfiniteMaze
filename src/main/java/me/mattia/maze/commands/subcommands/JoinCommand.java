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

public class JoinCommand extends SubCommand {
    private final InfiniteMaze infiniteMaze;

    public JoinCommand(InfiniteMaze plugin) {
        super("join", "work in progress", "&6/maze join &7<event>", AcceptedSender.PLAYER, CommandPermissions.COMMAND_EVENT_JOIN_PERMISSION);
        this.infiniteMaze = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.spigot().sendMessage(infiniteMaze.getTextFormatter().formatText("%prefix% &7This feature is under development! It'll be available soon"));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}