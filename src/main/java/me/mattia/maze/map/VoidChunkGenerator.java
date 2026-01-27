package me.mattia.maze.map;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

@SuppressWarnings("all")
public class VoidChunkGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(
            World world,
            Random random,
            int chunkX,
            int chunkZ,
            BiomeGrid biomeGrid
    ) {
        for (int x = -16; x < 16; x++) {
            for (int z = -16; z < 16; z++) {
                biomeGrid.setBiome(x, z, Biome.JUNGLE);
            }
        }

        return createChunkData(world);
    }
}