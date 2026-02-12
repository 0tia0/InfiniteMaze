package me.mattia.maze.commands.subcommands;

import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.commands.AcceptedSender;
import me.mattia.maze.commands.CommandPermissions;
import me.mattia.maze.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeTpCommand extends SubCommand {
    private final InfiniteMaze infiniteMaze;
    private final World defaultWorld;

    public MazeTpCommand(InfiniteMaze plugin) {
        super("tp", "teleport into the maze world", "&6/maze tp &7<maze_world_name> <player>", AcceptedSender.PLAYER, CommandPermissions.COMMAND_TP_PERMISSION);
        this.infiniteMaze = plugin;

        defaultWorld = Bukkit.getWorlds().getFirst();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.spigot().sendMessage(this.infiniteMaze.getTextFormatter().formatText(
                    infiniteMaze.getConfigs().getMessagesConfig().getConfig().getString(
                            "commands.correct_use",
                            "%prefix% &7Incorrect use of the command. Try %usage%").replace("%usage%", super.usage)
            ));
            return;
        }

        String worldName = args[0];

        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        if (!worldFolder.exists() || !worldFolder.isDirectory()) {
            sender.spigot().sendMessage(infiniteMaze.getTextFormatter().formatText("%prefix% &7Maze world " + worldName + "&c not found"));
            return;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            WorldCreator creator = new WorldCreator(worldName);
            world = creator.createWorld();

            if(world == null){
                sender.spigot().sendMessage(infiniteMaze.getTextFormatter().formatText("%prefix% &7Maze world " + worldName + "&c not loaded"));
                return;
            }
        }

        Player target;

        if (args.length == 2) {
            if (!sender.hasPermission(CommandPermissions.COMMAND_TP_OTHERS_PERMISSION)) {
                sender.spigot().sendMessage(this.infiniteMaze.getTextFormatter().formatText(
                        infiniteMaze.getConfigs().getMessagesConfig().getConfig().getString(
                                "commands.missing_permission", "%prefix% §7You don't have the permission to use this command"
                        )
                ));
                return;
            }

            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.spigot().sendMessage(infiniteMaze.getTextFormatter().formatText("%prefix% &cPlayer " + args[1] + " not found or offline"));
                return;
            }
        } else target = (Player) sender;

        target.teleport(world.getSpawnLocation());
        target.spigot().sendMessage(infiniteMaze.getTextFormatter().formatText("%prefix% &7Teleported to &6" + worldName));

        if (!target.equals(sender)) {
            sender.spigot().sendMessage(infiniteMaze.getTextFormatter().formatText("%prefix% &7Teleported &6" + target.getName() + " &7to &6" + worldName));
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1 || args.length > 2) return Collections.emptyList();

        if (args.length == 1) {
            File serverFolder = Bukkit.getWorldContainer();
            File[] files = serverFolder.listFiles();
            if (files == null) return List.of();

            List<String> worlds = new ArrayList<>();
            worlds.add(defaultWorld.getName());

            for (File file : files) {
                if (!file.isDirectory()) continue;

                String name = file.getName();
                if (name.startsWith("maze_") && name.toLowerCase().startsWith(args[0].toLowerCase())) {
                    worlds.add(name);
                }
            }
            return worlds;
        } else {
            List<String> players = new ArrayList<>();
            infiniteMaze.getServer().getOnlinePlayers().forEach(player -> {
                if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) players.add(player.getName());
            });
            return players;
        }
    }
}