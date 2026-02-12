package me.mattia.maze.commands;

import com.fastasyncworldedit.core.extent.filter.LinkedFilter;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import lombok.Getter;
import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.commands.subcommands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    @Getter private List<SubCommand> subCommands = new ArrayList<>();
    private final InfiniteMaze infiniteMaze;

    private final FileConfiguration messagesConfig;

    public CommandManager(InfiniteMaze infiniteMaze) {
        this.infiniteMaze = infiniteMaze;
        this.messagesConfig = infiniteMaze.getConfigs().getMessagesConfig().getConfig();

        this.subCommands.add(new GuiCommand(infiniteMaze));
        this.subCommands.add(new HelpCommand(infiniteMaze));
        this.subCommands.add(new ReloadCommand(infiniteMaze));
        this.subCommands.add(new MazeTpCommand(infiniteMaze));
        this.subCommands.add(new EventCommand(infiniteMaze));
        this.subCommands.add(new JoinCommand(infiniteMaze));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.spigot().sendMessage(this.infiniteMaze.getTextFormatter().formatText(
                    messagesConfig.getString("commands.correct_use", "%prefix% &7Incorrect use of the command. Try %usage%").replace("%usage%", "&6/maze <subcommand>")
            ));
            return true;
        }

        String sub = args[0].toLowerCase();

        for (SubCommand cmd : this.subCommands) {
            if (cmd.getName().equalsIgnoreCase(sub)) {
                if (!cmd.isSenderValid(sender)) {
                    sender.spigot().sendMessage(this.infiniteMaze.getTextFormatter().formatText(
                            messagesConfig.getString("commands.invalid_sender", "%prefix% §7You can't use this command here")
                    ));
                    return true;
                }

                if (!cmd.hasPermission(sender) && !sender.hasPermission(CommandPermissions.COMMAND_ADMIN_PERMISSION)) {
                    sender.spigot().sendMessage(this.infiniteMaze.getTextFormatter().formatText(
                            messagesConfig.getString("commands.missing_permission", "%prefix% §7You don't have the permission to use this command")
                    ));
                    return true;
                }

                if(this.infiniteMaze.isBetaRelease()) {
                    sender.sendMessage("§6InfiniteMaze §8§l» §7You are running a §cbeta version§7. Some features may be §cwork in progress§7. New versions will be released soon");
                    sender.sendMessage("");
                }

                cmd.execute(sender, Arrays.copyOfRange(args, 1, args.length));
                return true;
            }
        }

        sender.spigot().sendMessage(this.infiniteMaze.getTextFormatter().formatText(
                messagesConfig.getString("commands.command_not_found", "%prefix% §7Command not found. Try &6/%label% &7help").replace("%label%", label)
        ));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> subCommandsName = new ArrayList<>();

        for (SubCommand cmd : this.subCommands) {
            subCommandsName.add(cmd.getName());
        }

        if (args.length == 1) {
            return subCommandsName;
        }

        if (args.length > 1) {
            for (SubCommand cmd : subCommands) {
                if (cmd.getName().equalsIgnoreCase(args[0])) {
                    return cmd.onTabComplete(sender,command, alias, Arrays.copyOfRange(args, 1, args.length));
                }
            }
        }

        return Collections.emptyList();
    }
}
