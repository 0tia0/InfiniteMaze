package me.mattia.maze.map;

import lombok.RequiredArgsConstructor;
import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.maze.MazeScheme;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MazeBuilder {
    private final MazeScheme mazeScheme;
    private final InfiniteMaze infiniteMaze;
    private final World mapWorld;

    private final int wallHeight = 4;
    private final int wallWidth = 2;

    private record BlockData(int x, int y, int z, Material material) {}

    public void buildMapAsync(Material walls, Material floor, Consumer<World> callback) {
        int mazeWidth = mazeScheme.getWidth();
        int mazeHeight = mazeScheme.getHeight();

        int xOffset = -mazeWidth * wallWidth;
        int zOffset = 10;

        final int batchSize = 500;

        Bukkit.getScheduler().runTaskAsynchronously(infiniteMaze, () -> {
            List<BlockData> blocksToPlace = new ArrayList<>();

            for (int x = 0; x < mazeWidth; x++) {
                for (int z = 0; z < mazeHeight; z++) {
                    boolean isWall = mazeScheme.isWall(x, z);

                    for (int dx = 0; dx < wallWidth; dx++) {
                        for (int dz = 0; dz < wallWidth; dz++) {
                            int bx = x * wallWidth + dx + xOffset;
                            int bz = z * wallWidth + dz + zOffset;

                            blocksToPlace.add(new BlockData(bx, 99, bz, floor));

                            if (isWall) {
                                for (int y = 0; y < wallHeight; y++) {
                                    blocksToPlace.add(new BlockData(bx, 100 + y, bz, walls));
                                }
                            }
                        }
                    }
                }
            }

            new BukkitRunnable() {
                int index = 0;

                @Override
                public void run() {
                    int placed = 0;
                    while (index < blocksToPlace.size() && placed < batchSize) {
                        BlockData block = blocksToPlace.get(index++);
                        mapWorld.getBlockAt(block.x, block.y, block.z).setType(block.material);
                        placed++;

                        if (index >= blocksToPlace.size()) {
                            createSpawnPlatform();
                            mapWorld.setSpawnLocation(new Location(mapWorld, 0, 100, 0));
                            callback.accept(mapWorld);
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(infiniteMaze, 0L, 1L);
        });
    }

    public void createSpawnPlatform() {
        mapWorld.getBlockAt(0, 99, 0).setType(Material.STONE);
        mapWorld.getBlockAt(-1, 99, 0).setType(Material.STONE);
        mapWorld.getBlockAt(0, 99, -1).setType(Material.STONE);
        mapWorld.getBlockAt(-1, 99, -1).setType(Material.STONE);
    }
}