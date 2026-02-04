package me.mattia.maze.commands.subcommands;

import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.commands.AcceptedSender;
import me.mattia.maze.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {
    private final InfiniteMaze infiniteMaze;

    public HelpCommand(InfiniteMaze plugin) {
        super("help", "shows this help menu", "/maze help", AcceptedSender.ALL, "");
        this.infiniteMaze = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.infiniteMaze.getConfigs().reloadAllConfigs();
        sender.sendMessage("§8==============( §6§lInfinite Maze §8)==============");
        for(SubCommand cmd : this.infiniteMaze.getMazeCommandManager().getSubCommands()) {
            sender.sendMessage("§8■ §6" + cmd.getUsage() + "§7: " + cmd.getDescription());
        }
        sender.sendMessage("§8============================================");
    }
}