package me.mattia.maze.maze;

import lombok.Getter;
import lombok.Setter;

import java.util.BitSet;
import java.util.logging.Logger;

public class MazeScheme {

    @Getter private final int width;   // griglia reale
    @Getter private final int height;

    @Getter private final int cellW;   // numero di celle
    @Getter private final int cellH;

    @Getter private final BitSet maze;

    @Getter @Setter private long seed = System.currentTimeMillis();
    @Getter private int centerHoleSize = 0;
    @Getter @Setter private boolean topBottomEntrance = true;

    public MazeScheme(int width, int height, UsedAlgorithm algo) {
        if (width < 20 && height < 20) {
            throw new IllegalArgumentException("Width and height must be greater than 20");
        }

        this.width = makeOdd(width);
        this.height = makeOdd(height);

        this.cellW = (this.width-1)/2;
        this.cellH = (this.height-1)/2;

        this.maze = new BitSet(this.width * this.height);
        maze.set(0, this.width * this.height);
    }

    public void clearMaze() {
        maze.set(0, false);
    }

    public static int makeOdd(int n) {
        return n % 2 == 0 ? n + 1 : n;
    }

    public void setCenterHoleSize(int size) {
        this.centerHoleSize = size <= 0 ? 0 : makeOdd(size);
    }

    private int idx(int x, int y) {
        return y * width + x;
    }

    public boolean isWall(int x, int y) {
        return maze.get(idx(x, y));
    }

    public void setWall(int x, int y, boolean wall) {
        maze.set(idx(x, y), wall);
    }

    public boolean inBounds(int cx, int cy) {
        return cx >= 0 && cx < cellW && cy >= 0 && cy < cellH;
    }

    public void carveCell(int cx, int cy) {
        setWall(cx * 2 + 1, cy * 2 + 1, false);
    }

    public void carveBetween(int cx1, int cy1, int cx2, int cy2) {
        int x1 = cx1 * 2 + 1;
        int y1 = cy1 * 2 + 1;
        int x2 = cx2 * 2 + 1;
        int y2 = cy2 * 2 + 1;
        setWall((x1 + x2) / 2, (y1 + y2) / 2, false);
    }

    public void carveEntranceExit(boolean topBottom) {
        if (topBottom) {
            int cx = cellW / 2;
            setWall(cx * 2 + 1, 0, false);
            setWall(cx * 2 + 1, height - 1, false);
        } else {
            int cy = cellH / 2;
            setWall(0, cy * 2 + 1, false);
            setWall(width - 1, cy * 2 + 1, false);
        }
    }

    public void carveCentralHole(int size) {
        if (size <= 0) return;

        int cx = width / 2;
        int cy = height / 2;
        int r = size/2;

        for (int y = cy - r; y <= cy + r; y++) {
            for (int x = cx - r; x <= cx + r; x++) {
                if (x >= 0 && x < width && y >= 0 && y < height)
                    maze.set(idx(x, y), false);
            }
        }
    }

    public void printDebugScheme(Logger logger) {
        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                line.append(isWall(x, y) ? '#' : ' ');
            }
            logger.info(line.toString());
        }
    }
}