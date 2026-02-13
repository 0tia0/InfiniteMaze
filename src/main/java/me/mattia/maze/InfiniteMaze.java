package me.mattia.maze;

import lombok.Getter;
import me.mattia.maze.commands.CommandManager;
import me.mattia.maze.config.Configs;
import me.mattia.maze.map.MazeWorldTemplate;
import me.mattia.maze.messages.TextFormatter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class InfiniteMaze extends JavaPlugin {
    @Getter private final String templateWorldName = "do_not_touch_maze_template_world";

    @Getter private Configs configs;
    @Getter private TextFormatter textFormatter;

    @Getter private CommandManager mazeCommandManager;

    @Getter private final boolean betaRelease = true;

    @Override
    public void onEnable() {
        //https://patorjk.com/software/taag/#p=display&f=Star%20Wars&t=INFINITE%20%20%20%20%20%20MAZE&x=none
        this.getLogger().info(" __  .__   __.  _______  __  .__   __.  __  .___________. _______                   .___  ___.      ___      ________   _______ ");
        this.getLogger().info("|  | |  \\ |  | |   ____||  | |  \\ |  | |  | |           ||   ____|                  |   \\/   |     /   \\    |       /  |   ____|");
        this.getLogger().info("|  | |   \\|  | |  |__   |  | |   \\|  | |  | `---|  |----`|  |__                     |  \\  /  |    /  ^  \\   `---/  /   |  |__   ");
        this.getLogger().info("|  | |  . `  | |   __|  |  | |  . `  | |  |     |  |     |   __|                    |  |\\/|  |   /  /_\\  \\     /  /    |   __|  ");
        this.getLogger().info("|  | |  |\\   | |  |     |  | |  |\\   | |  |     |  |     |  |____                   |  |  |  |  /  _____  \\   /  /----.|  |____ ");
        this.getLogger().info("|__| |__| \\__| |__|     |__| |__| \\__| |__|     |__|     |_______|                  |__|  |__| /__/     \\__\\ /________||_______|");

        configs = new Configs(this);
        textFormatter = new TextFormatter(this);

        int configsVersion = this.getConfig().getInt("plugin_configurations_version");
        if (!this.configs.checkCompatibility(configsVersion)) {
            getLogger().info("");
            getLogger().severe("=========================================================================");
            getLogger().severe("InfiniteMaze - Outdated configuration detected!");
            getLogger().severe("You have installed a new version without updating the configuration files.");
            getLogger().severe("Please delete the plugin folder and restart the server.");
            getLogger().severe("You may create a backup of the old configuration to migrate your settings.");
            getLogger().severe("=========================================================================");
            getServer().getPluginManager().disablePlugin(this);
        }

        // Implementazione bStat
        Metrics metrics = new Metrics(this, 29013);

        // Load PAPI API
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            this.getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Bukkit.getScheduler().runTask(this, () -> {
            MazeWorldTemplate.generateTemplateWorld(new File(Bukkit.getWorldContainer(), templateWorldName), this);

            mazeCommandManager = new CommandManager(this);
            this.getCommand("maze").setExecutor(mazeCommandManager);
        });

        this.getLogger().info("Plugin started successfully!");
    }

    @Override
    public void onDisable() {

    }

    public Plugin getInstance() {
        return this;
    }
}
