package me.mattia.maze.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.maze.MazeScheme;
import me.mattia.maze.maze.algorithms.KruskalAlgorithm;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class GameMap {
    private final MazeScheme mazeScheme;
    private final String mapName;
    private World mapWorld;
    private boolean worldCreated;

    @Getter private Location spawnLocation;

    public void createWorld() {
        WorldCreator worldCreator = new WorldCreator(mapName);
        worldCreator.generator(new VoidChunkGenerator());
        worldCreator.generateStructures(false);

        mapWorld = worldCreator.createWorld();

        if (mapWorld == null) {
            worldCreated = false;
            return;
        }

        mapWorld.setAutoSave(true);

        // Game rules e impostazioni
        mapWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        mapWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        mapWorld.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        mapWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        mapWorld.setDifficulty(Difficulty.PEACEFUL);
        mapWorld.setPVP(false);

        mapWorld.setTime(12000);
        mapWorld.setStorm(false);

        spawnLocation = new Location(mapWorld, 0, 100, 0);
        worldCreated = true;
    }


    public void buildMap(Material walls, Material floor) {
        int mazeWidth = mazeScheme.getWidth();
        int mazeHeight = mazeScheme.getHeight();

        int wallHeight = 4;
        int zOffset = 10;
        int xOffset = -mazeWidth;

        for (int x = 0; x < mazeWidth; x++) {
            for (int z = 0; z < mazeHeight; z++) {
                Material materialToUse = mazeScheme.isWall(x, z) ? walls : Material.AIR;

                for (int dx = 0; dx < 2; dx++) {
                    for (int dz = 0; dz < 2; dz++) {
                        mapWorld.getBlockAt(x * 2 + dx + xOffset, 99, z * 2 + dz + zOffset).setType(floor);

                        for (int y = 0; y < wallHeight; y++) {
                            mapWorld.getBlockAt(x * 2 + dx + xOffset, 100 + y, z * 2 + dz + zOffset).setType(materialToUse);
                        }
                    }
                }
            }
        }

        createSpawnPlatform();
        mapWorld.setSpawnLocation(spawnLocation);
    }

    public void createSpawnPlatform() {
        mapWorld.getBlockAt(0, 99, 0).setType(Material.STONE);
        mapWorld.getBlockAt(-1, 99, 0).setType(Material.STONE);
    }

}
