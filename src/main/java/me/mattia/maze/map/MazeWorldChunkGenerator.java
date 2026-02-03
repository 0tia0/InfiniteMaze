package me.mattia.maze.map;

import com.sk89q.worldedit.world.biome.Biomes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class MazeWorldChunkGenerator extends ChunkGenerator {

    @Override
    public void generateNoise(
            @NotNull WorldInfo worldInfo,
            @NotNull Random random,
            int chunkX,
            int chunkZ,
            @NotNull ChunkData chunkData
    ) {
        // NIENTE = mondo vuoto
    }

    @Override
    public void generateSurface(
            @NotNull WorldInfo worldInfo,
            @NotNull Random random,
            int chunkX,
            int chunkZ,
            @NotNull ChunkData chunkData
    ) {
        // NIENTE = mondo vuoto
    }

    @Override
    public void generateBedrock(
            @NotNull WorldInfo worldInfo,
            @NotNull Random random,
            int chunkX,
            int chunkZ,
            @NotNull ChunkData chunkData
    ) {
        // niente bedrock
    }

    @Override
    public void generateCaves(
            @NotNull WorldInfo worldInfo,
            @NotNull Random random,
            int chunkX,
            int chunkZ,
            @NotNull ChunkData chunkData
    ) {
        // niente caverne
    }

    @Override
    public @NotNull BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        return new BiomeProvider() {
            @Override
            public @NotNull Biome getBiome(
                    @NotNull WorldInfo worldInfo,
                    int x, int y, int z
            ) {
                return Biome.JUNGLE;
            }

            @Override
            public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
                return List.of(Biome.JUNGLE);
            }
        };
    }
}
