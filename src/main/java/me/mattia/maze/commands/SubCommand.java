package me.mattia.maze.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public abstract class SubCommand {
    @Getter final protected String name;
    @Getter final protected String description;
    @Getter final protected String usage;

    protected final AcceptedSender acceptedSender;
    @Getter final protected String permission;

    public abstract void execute(CommandSender sender, String[] args);

    public boolean isSenderValid(CommandSender sender) {
        if (acceptedSender == AcceptedSender.ALL) return true;

        return switch (acceptedSender) {
            case PLAYER -> sender instanceof Player;
            case CONSOLE -> sender instanceof ConsoleCommandSender;
            default -> false;
        };
    }

    public boolean hasPermission(CommandSender sender) {
        if(permission == null) return true;
        return sender.hasPermission(permission);
    }
}
