package me.mattia.maze.map;

import lombok.Getter;
import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.maze.UsedAlgorithm;
import me.mattia.maze.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

public class MazeWorld {
    private final UsedAlgorithm usedAlgorithm;
    private final InfiniteMaze infiniteMaze;
    @Getter private World world;

    public MazeWorld(UsedAlgorithm usedAlgorithm, InfiniteMaze infiniteMaze) throws IOException {
        this.usedAlgorithm = usedAlgorithm;
        this.infiniteMaze = infiniteMaze;

        world = createMazeWorld();
    }

    private World createMazeWorld() throws IOException {
        if (Bukkit.getWorld(infiniteMaze.getTemplateWorldName()) != null) {
            Bukkit.unloadWorld(infiniteMaze.getTemplateWorldName(), false);
        }

        File wc = Bukkit.getWorldContainer();
        String worldName = "maze_" + System.currentTimeMillis();

        File source = new File(wc, infiniteMaze.getTemplateWorldName());
        File target = new File(wc, worldName);

        FileUtil.copyWorld(source, target);

        WorldCreator creator = new WorldCreator(worldName);
        World world = creator.createWorld();

        if(world == null) throw new IOException("World could not be created!");

        world.setAutoSave(true);
        return world;
    }
}
