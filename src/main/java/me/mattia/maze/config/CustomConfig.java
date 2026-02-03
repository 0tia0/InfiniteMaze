package me.mattia.maze.config;

import me.mattia.maze.InfiniteMaze;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CustomConfig {
    private final InfiniteMaze infiniteMaze;
    private final String customConfigName;
    
    private FileConfiguration customConfig = null;
    private File customConfigFile = null;

    public CustomConfig(InfiniteMaze infiniteMaze, String customConfigName) {
        this.infiniteMaze = infiniteMaze;
        this.customConfigName = customConfigName;
        
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(infiniteMaze.getDataFolder(), customConfigName);
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

        InputStream defConfigStream = infiniteMaze.getResource(customConfigName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            customConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (customConfig == null) reloadConfig();
        return customConfig;
    }

    public void saveConfig() {
        if (customConfig == null || customConfigFile == null) return;
        try {
            getConfig().save(customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(infiniteMaze.getDataFolder(), customConfigName);
        }
        if (!customConfigFile.exists()) {
            infiniteMaze.saveResource(customConfigName, false);
        }
    }
}
