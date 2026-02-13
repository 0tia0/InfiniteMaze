package me.mattia.maze.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mattia.maze.InfiniteMaze;

public class Configs {
    private final InfiniteMaze infiniteMaze;

    @Getter private final CustomConfig messagesConfig;

    public Configs (InfiniteMaze infiniteMaze) {
        this.infiniteMaze = infiniteMaze;

        this.messagesConfig = new CustomConfig(infiniteMaze, "messages.yml");

        this.infiniteMaze.saveDefaultConfig();
    }

    public void reloadAllConfigs() {
        this.infiniteMaze.reloadConfig();
        this.messagesConfig.reloadConfig();
    }

    public boolean checkCompatibility(int configVersion) {
        String pluginVersion = this.infiniteMaze.getDescription().getVersion();

        char type = pluginVersion.charAt(0);
        String numericPart = pluginVersion.substring(1);
        String[] parts = numericPart.split("\\.");

        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = Integer.parseInt(parts[2]);

        switch (type) {
            case 'B': {
                // Beta versions
                return major == 1 && minor == 0 && patch == 0 && configVersion == 1;
            }
            case 'R': {
                // Release version
                break;
            }
        }

        return false;
    }
}

