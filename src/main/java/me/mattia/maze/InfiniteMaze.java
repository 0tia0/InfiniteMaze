package me.mattia.maze;

import lombok.Getter;
import me.mattia.maze.map.GameMap;
import me.mattia.maze.maze.MazeScheme;
import me.mattia.maze.maze.UsedAlgorithm;
import me.mattia.maze.maze.algorithms.DFSAlgorithm;
import me.mattia.maze.maze.algorithms.KruskalAlgorithm;
import me.mattia.maze.maze.algorithms.PrimAlgorithm;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public final class InfiniteMaze extends JavaPlugin {
    @Override
    public void onEnable() {
        //https://patorjk.com/software/taag/#p=display&f=Star%20Wars&t=INFINITE%20%20%20%20%20%20MAZE&x=none
        this.getLogger().info(" __  .__   __.  _______  __  .__   __.  __  .___________. _______                   .___  ___.      ___      ________   _______ ");
        this.getLogger().info("|  | |  \\ |  | |   ____||  | |  \\ |  | |  | |           ||   ____|                  |   \\/   |     /   \\    |       /  |   ____|");
        this.getLogger().info("|  | |   \\|  | |  |__   |  | |   \\|  | |  | `---|  |----`|  |__                     |  \\  /  |    /  ^  \\   `---/  /   |  |__   ");
        this.getLogger().info("|  | |  . `  | |   __|  |  | |  . `  | |  |     |  |     |   __|                    |  |\\/|  |   /  /_\\  \\     /  /    |   __|  ");
        this.getLogger().info("|  | |  |\\   | |  |     |  | |  |\\   | |  |     |  |     |  |____                   |  |  |  |  /  _____  \\   /  /----.|  |____ ");
        this.getLogger().info("|__| |__| \\__| |__|     |__| |__| \\__| |__|     |__|     |_______|                  |__|  |__| /__/     \\__\\ /________||_______|");

        // Implementazione bStat
        Metrics metrics = new Metrics(this, 29013);

        this.getLogger().info("Plugin started successfully!");
    }

    @Override
    public void onDisable() {

    }

    public Plugin getInstance() {
        return this;
    }
}
