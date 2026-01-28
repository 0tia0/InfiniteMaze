package me.mattia.maze.map;

import me.mattia.maze.InfiniteMaze;
import org.bukkit.*;

import java.io.File;

public class MazeWorldTemplate {
    public static void generateTemplateWorld(File worldFile, InfiniteMaze infiniteMaze) {
        if (!worldFile.exists()) {
            infiniteMaze.getLogger().info("Template world not found, generating it!");

            WorldCreator wc = new WorldCreator(worldFile.getName());
            wc.generator(new MazeWorldChunkGenerator());
            wc.type(WorldType.FLAT);

            World world = wc.createWorld();
            if(world == null) return;

            world.setAutoSave(false);

            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);

            world.setDifficulty(Difficulty.PEACEFUL);
            world.setPVP(false);

            world.setTime(12000);
            world.setStorm(false);

            Bukkit.unloadWorld(world, true);
        }
    }
}
