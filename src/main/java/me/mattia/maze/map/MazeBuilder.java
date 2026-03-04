package me.mattia.maze.map;

import lombok.RequiredArgsConstructor;
import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.maze.MazeScheme;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MazeBuilder {
    private final MazeScheme mazeScheme;
    private final InfiniteMaze infiniteMaze;
    private final World mapWorld;

    private final int wallHeight;
    private final int wallWidth;

    Material usedFloor = Material.STONE;

    public MazeBuilder(MazeScheme mazeScheme, InfiniteMaze infiniteMaze, World mapWorld) {
        this.mazeScheme = mazeScheme;
        this.infiniteMaze = infiniteMaze;
        this.mapWorld = mapWorld;

        this.wallWidth = infiniteMaze.getConfig().getInt("maze_generation.wall_width", 2);
        this.wallHeight = infiniteMaze.getConfig().getInt("maze_generation.wall_height", 4);
    }

    private record BlockData(int x, int y, int z, Material material) {}

    public void buildMapAsync(Material walls, Material floor, Consumer<World> callback) {
        this.usedFloor = floor;

        int mazeWidth = mazeScheme.getWidth();
        int mazeHeight = mazeScheme.getHeight();

        int xOffset = -mazeWidth * wallWidth;
        int zOffset = 10;

        final int batchSize = infiniteMaze.getConfig().getInt("anti_lag_limitation.block_per_tick", 500);

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
                            callback.accept(mapWorld);
                            this.cancel();
                        }
                    }
                }
            }.runTaskTimer(infiniteMaze, 0L, 1L);
        });
    }

    public void createSpawnPlatform() {
        int mazeWidth = mazeScheme.getWidth();
        int platformDepth = 10;
        int yLevel = 99;

        // Correzione per centrare la piattaforma
        int startX = -mazeWidth + ((mazeWidth/2 % 2 == 0) ? 0 : -2);
        System.err.println((mazeWidth % 2 == 0) + " " + mazeWidth);

        for (int z = 0; z < platformDepth; z++) {
            // ==== LIVELLO BASE ====
            // Bordo sinistro
            setTopSlab(mapWorld.getBlockAt(startX, yLevel, z), Material.POLISHED_BLACKSTONE_SLAB);

            // Corpo centrale
            for (int dx = 1; dx <= wallWidth; dx++) {
                mapWorld.getBlockAt(startX + dx, yLevel, z).setType(Material.POLISHED_BLACKSTONE);
            }

            // Bordo destro
            setTopSlab(mapWorld.getBlockAt(startX + wallWidth + 1, yLevel, z), Material.POLISHED_BLACKSTONE_SLAB);
            // ==== LIVELLO SUPERIORE ====
            for (int dx = 0; dx <= wallWidth + 1; dx++) {
                Block block = mapWorld.getBlockAt(startX + dx, yLevel + 1, z);

                // Bordo sempre top slab
                if (dx == 0 || dx == wallWidth + 1) {
                    // Pattern alternato nel corpo centrale
                    if (z % 2 == 0) {
                        block.setType(Material.POLISHED_BLACKSTONE);
                    } else {
                        setTopSlab(block, Material.POLISHED_BLACKSTONE_SLAB);
                    }
                }
            }
        }

        mapWorld.setSpawnLocation(new Location(mapWorld, startX + ((double) wallWidth /2) + 1, 100, 2));
        setTopSlab(mapWorld.getBlockAt(startX + 1, 100, 0), Material.POLISHED_BLACKSTONE_SLAB);
        setTopSlab(mapWorld.getBlockAt(startX + 2, 100, 0), Material.POLISHED_BLACKSTONE_SLAB);
    }

    private void setTopSlab(Block block, Material slabMaterial) {
        block.setType(slabMaterial);

        Slab slab = (Slab) block.getBlockData();
        slab.setType(Slab.Type.TOP);
        block.setBlockData(slab);
    }
}