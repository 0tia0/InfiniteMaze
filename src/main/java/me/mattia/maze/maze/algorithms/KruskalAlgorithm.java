package me.mattia.maze.maze.algorithms;

import me.mattia.maze.maze.MazeScheme;

import java.util.*;

public final class KruskalAlgorithm {

    private static int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    private static void union(int[] parent, int a, int b) {
        parent[find(parent, a)] = find(parent, b);
    }

    public static void generate(MazeScheme maze) {
        int w = maze.getCellW();
        int h = maze.getCellH();
        Random r = new Random(maze.getSeed());

        int cells = w * h;
        int[] parent = new int[cells];
        for (int i = 0; i < cells; i++) parent[i] = i;

        List<int[]> edges = new ArrayList<>();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (x < w - 1) edges.add(new int[]{x, y, x + 1, y});
                if (y < h - 1) edges.add(new int[]{x, y, x, y + 1});
            }
        }

        Collections.shuffle(edges, r);

        for (int i = 0; i < cells; i++) {
            maze.carveCell(i % w, i / w);
        }

        for (int[] e : edges) {
            int ax = e[0], ay = e[1], bx = e[2], by = e[3];
            int aId = ay * w + ax;
            int bId = by * w + bx;
            if (find(parent, aId) != find(parent, bId)) {
                union(parent, aId, bId);
                maze.carveBetween(ax, ay, bx, by);
            }
        }

        if (maze.getCenterHoleSize() > 0) {
            maze.carveCentralHole(maze.getCenterHoleSize());
        }

        maze.carveEntranceExit(maze.isTopBottomEntrance());
    }
}