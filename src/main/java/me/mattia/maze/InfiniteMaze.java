package me.mattia.maze;

import com.sk89q.worldedit.math.interpolation.LinearInterpolation;
import lombok.Getter;
import me.mattia.maze.gui.MazeConfigGUI;
import me.mattia.maze.map.MazeWorldTemplate;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class InfiniteMaze extends JavaPlugin {
    @Getter private final String templateWorldName = "do_not_touch_maze_template_world";

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

        Bukkit.getScheduler().runTask(this, () -> {
            MazeWorldTemplate.generateTemplateWorld(new File(Bukkit.getWorldContainer(), templateWorldName), this);
        });

        this.getLogger().info("Plugin started successfully!");
    }

    @Override
    public void onDisable() {

    }

    // TESTING ONLY
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("maze") && sender instanceof Player) {
            MazeConfigGUI mazeConfigGUI = new MazeConfigGUI(this);
            mazeConfigGUI.getGui().show((Player) sender);
        }
        return false;
    }

    public Plugin getInstance() {
        return this;
    }
}
