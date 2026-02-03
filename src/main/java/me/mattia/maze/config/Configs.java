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
}
