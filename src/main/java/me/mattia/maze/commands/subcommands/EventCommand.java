package me.mattia.maze.commands.subcommands;

import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.commands.AcceptedSender;
import me.mattia.maze.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EventCommand extends SubCommand {
    private final InfiniteMaze infiniteMaze;

    public EventCommand(InfiniteMaze plugin) {
        // Permission non aggiunta perchè i controlli vengono effettuati sui sub create e start
        super("event", "work in progress", "&6/maze event &7<create | start>", AcceptedSender.PLAYER, "");
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