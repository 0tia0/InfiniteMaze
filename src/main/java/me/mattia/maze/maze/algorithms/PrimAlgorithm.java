package me.mattia.maze.maze.algorithms;

import me.mattia.maze.maze.MazeScheme;

import java.util.*;

public final class PrimAlgorithm {

    private static void addFrontier(int x, int y, int w, int h, boolean[][] inMaze, List<int[]> frontier, Set<String> added) {
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            int nx = x + d[0], ny = y + d[1];
            String key = nx + "," + ny;
            if (nx >= 0 && nx < w && ny >= 0 && ny < h && !inMaze[ny][nx] && !added.contains(key)) {
                frontier.add(new int[]{nx, ny});
                added.add(key);
            }
        }
    }

    public static void generate(MazeScheme maze) {
        int w = maze.getCellW();
        int h = maze.getCellH();
        Random r = new Random(maze.getSeed());

        boolean[][] inMaze = new boolean[h][w];
        List<int[]> frontier = new ArrayList<>();
        Set<String> added = new HashSet<>();

        int sx = r.nextInt(w);
        int sy = r.nextInt(h);
        inMaze[sy][sx] = true;
        maze.carveCell(sx, sy);
        addFrontier(sx, sy, w, h, inMaze, frontier, added);

        while (!frontier.isEmpty()) {
            int idx = r.nextInt(frontier.size());
            int[] f = frontier.remove(idx);
            int x = f[0], y = f[1];

            List<int[]> neighbors = new ArrayList<>();
            if (x > 0 && inMaze[y][x-1]) neighbors.add(new int[]{x-1, y});
            if (x < w-1 && inMaze[y][x+1]) neighbors.add(new int[]{x+1, y});
            if (y > 0 && inMaze[y-1][x]) neighbors.add(new int[]{x, y-1});
            if (y < h-1 && inMaze[y+1][x]) neighbors.add(new int[]{x, y+1});

            if (!neighbors.isEmpty()) {
                int[] n = neighbors.get(r.nextInt(neighbors.size()));
                inMaze[y][x] = true;
                maze.carveCell(x, y);
                maze.carveBetween(x, y, n[0], n[1]);
                addFrontier(x, y, w, h, inMaze, frontier, added);
            }
        }

        if (maze.getCenterHoleSize() > 0) {
            maze.carveCentralHole(maze.getCenterHoleSize());
        }
        maze.carveEntranceExit(maze.isTopBottomEntrance());
    }
}