package me.mattia.maze.maze.algorithms;

import me.mattia.maze.maze.MazeScheme;

import java.util.*;

public final class DFSAlgorithm {

    public static void generate(MazeScheme maze) {
        int w = maze.getCellW();
        int h = maze.getCellH();
        Random r = new Random(maze.getSeed());

        boolean[][] visited = new boolean[h][w];
        Deque<int[]> stack = new ArrayDeque<>();

        int sx = r.nextInt(w);
        int sy = r.nextInt(h);
        visited[sy][sx] = true;
        maze.carveCell(sx, sy);
        stack.push(new int[]{sx, sy});

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        while (!stack.isEmpty()) {
            int[] c = stack.peek();
            List<int[]> nbs = new ArrayList<>();

            for (int[] d : dirs) {
                int nx = c[0] + d[0], ny = c[1] + d[1];
                if (maze.inBounds(nx, ny) && !visited[ny][nx])
                    nbs.add(new int[]{nx, ny});
            }

            if (nbs.isEmpty()) {
                stack.pop();
            } else {
                int[] n = nbs.get(r.nextInt(nbs.size()));
                visited[n[1]][n[0]] = true;
                maze.carveBetween(c[0], c[1], n[0], n[1]);
                maze.carveCell(n[0], n[1]);
                stack.push(n);
            }
        }

        if (maze.getCenterHoleSize() > 0) {
            maze.carveCentralHole(maze.getCenterHoleSize());
        }

        maze.carveEntranceExit(maze.isTopBottomEntrance());
    }
}