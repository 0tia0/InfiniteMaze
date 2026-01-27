package me.mattia.maze.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.mattia.maze.InfiniteMaze;
import me.mattia.maze.map.GameMap;
import me.mattia.maze.maze.MazeScheme;
import me.mattia.maze.maze.UsedAlgorithm;
import me.mattia.maze.maze.algorithms.DFSAlgorithm;
import me.mattia.maze.maze.algorithms.KruskalAlgorithm;
import me.mattia.maze.maze.algorithms.PrimAlgorithm;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class MazeConfigGUI extends GUI {
    PatternPane pane = this.getLargeChestPatternPane();

    private int size = 20;

    // hole as percentage of maze size
    private int holePercent = 0; // min 0%, max 50%
    private int holeSize = 0;

    private UsedAlgorithm algorithm = UsedAlgorithm.DFS;

    private Material wallsBlock = Material.BARRIER;
    private Material floorBlock = Material.BARRIER;

    public MazeConfigGUI(InfiniteMaze infiniteMaze) {
        super(new ChestGui(6, "Maze : Configurator"), new StaticPane(0, 0, 9, 6), infiniteMaze);

        this.getGui().addPane(this.staticPane);
        this.getGui().addPane(pane);

        refreshItems();
    }

    public void refreshItems() {
        this.staticPane.clear();

        this.addItem(this.getExitButton(), 4, 5);

        this.addItem(this.getAlgorithmButton(), 6, 1);
        this.addItem(this.getHoleButton(), 4, 1);
        this.addItem(this.getSizeButton(), 2, 1);

        this.addItem(this.getFloorButton(), 5, 2);
        this.addItem(this.getWallsButton(), 3, 2);

        this.addItem(this.getSaveButton(), 2,4);
        this.addItem(this.getGenerateButton(), 6,4);

        this.getGui().addPane(pane);

        this.getGui().update();
    }

    public GuiItem getGenerateButton() {
        CustomizableItem item = new CustomizableItem(Material.EMERALD);
        item.setName("§6Generate!");
        item.setLore(List.of(
                "",
                "§7Summary",
                "§6■ §7SIZE: §6" + size + "x" + size,
                "§6■ §7HOLE: §6" + holeSize + "x" + holeSize,
                "§6■ §7ALGORITHM: §6" + algorithm.name(),
                "§6■ §7WALLS: §6" + (wallsBlock == Material.BARRIER ? "§cMISSING" : wallsBlock.name()),
                "§6■ §7FLOOR: §6" + (floorBlock == Material.BARRIER ? "§cMISSING" : floorBlock.name()),
                "",
                "§7Click to generate the maze",
                ""
        ));
        return new GuiItem(item, e -> {
            e.setCancelled(true);
            refreshItems();

            if(floorBlock == Material.BARRIER || wallsBlock == Material.BARRIER) {
                playErrorSound((Player) e.getWhoClicked());
                return;
            }

            playConfirmationSound((Player) e.getWhoClicked());

            ((Player) e.getWhoClicked()).sendMessage(ChatColor.GREEN + "Stiamo generando il labirinto richiesto!");
            GameMap tempGameMap = createGameMap("test");
            tempGameMap.buildMap(wallsBlock, floorBlock);

            ((Player) e.getWhoClicked()).teleport(tempGameMap.getSpawnLocation());
        });
    }

    public GuiItem getSaveButton() {
        CustomizableItem item = new CustomizableItem(Material.NETHER_STAR);
        item.setName("§cSave configuration!");
        item.setLore(List.of(
                "",
                "§7Work In Progress",
                "",
                "§7Click to save (§cComing Soon§7)",
                ""
        ));
        return new GuiItem(item, e -> {
            e.setCancelled(true);
            refreshItems();
            playErrorSound((Player) e.getWhoClicked());
        });
    }

    public GuiItem getWallsButton() {
        return new GuiItem(getChoosenBlock("walls", wallsBlock), e -> {
            e.setCancelled(true);

            if (e.getWhoClicked() instanceof Player player) {
                ItemStack inHand = player.getItemOnCursor();
                if (inHand.getType() != Material.AIR && inHand.getType().isBlock()) {
                    wallsBlock = inHand.getType();
                    refreshItems();
                    playConfirmationSound(player);
                } else {
                    playErrorSound(player);
                }
            }

            refreshItems();
        });
    }

    public GuiItem getFloorButton() {
        return new GuiItem(getChoosenBlock("floor", floorBlock), e -> {
            e.setCancelled(true);

            if (e.getWhoClicked() instanceof Player player) {
                ItemStack inHand = player.getItemOnCursor();
                if (inHand.getType() != Material.AIR && inHand.getType().isBlock()) {
                    floorBlock = inHand.getType();
                    refreshItems();
                    playConfirmationSound(player);
                } else {
                    playErrorSound(player);
                }
            }

            refreshItems();
        });
    }

    public GuiItem getAlgorithmButton() {
        CustomizableItem item = new CustomizableItem(Material.COMPASS);
        item.setName("§bChoose Algorithm");
        item.setLore(List.of(
                "",
                formatAlgorithm("DFS") + " §7(easy)",
                formatAlgorithm("KRUSKAL") + " §7(hard)",
                formatAlgorithm("PRIM") + " §7(funny)",
                "",
                "§7Click to change",
                ""
        ));

        return new GuiItem(item, e -> {
            e.setCancelled(true);
            algorithm = UsedAlgorithm.values()[(algorithm.ordinal() + 1) % UsedAlgorithm.values().length];
            refreshItems();
            playConfirmationSound((Player) e.getWhoClicked());
        });
    }

    public GuiItem getSizeButton() {
        CustomizableItem item = new CustomizableItem(Material.PAPER);
        item.setName("§eMaze Size");
        item.setLore(List.of(
                "",
                "§7Select the size",
                "§7Current: §e" + size + "x" + size,
                "",
                "§e■ §7LEFT CLICK §eADD 10",
                "§e■ §7RIGHT CLICK §eREMOVE 10",
                "",
                "§7§iThe size will be multiplied by the config voice wall-width",
                ""
        ));

        return new GuiItem(item, e -> {
            e.setCancelled(true);

            size = updateSize(e, size, 200, 20, 10);

            refreshItems();
        });
    }

    public GuiItem getHoleButton() {
        holeSize = (size * holePercent) / 100;

        CustomizableItem item = new CustomizableItem(holePercent > 0 ? Material.LIME_DYE : Material.GRAY_DYE);

        item.setName("§5Hole Size");
        item.setLore(List.of(
                "",
                "§7Select the size for the hole in the middle (0% - 50%)",
                "§7Current: §5" + holeSize + "x" + holeSize + " §7(§5" + holePercent + "%§7)",
                "",
                "§5■ §7LEFT CLICK §5ADD 5",
                "§5■ §7RIGHT CLICK §5REMOVE 5",
                "",
                "§7§iThe size will be multiplied by the config voice wall-width",
                ""
        ));

        return new GuiItem(item, e -> {
            e.setCancelled(true);

            holePercent = updateSize(e, holePercent, 50, 0, 5);

            refreshItems();
        });
    }

    public ItemStack getChoosenBlock(String porpuse, Material block) {
        CustomizableItem item = new CustomizableItem(block);
        item.setName("§aBlock to use for the " + porpuse);
        item.setLore(List.of(
                "",
                "§7Click holding the block you want to use",
                "§7Current: §a" + (block == Material.BARRIER ? "NONE" : block.name()),
                ""
        ));

        return item;
    }

    public int updateSize(InventoryClickEvent e, int current, int max, int min, int step) {
        int delta = e.isLeftClick() ? step : -step;
        int value = current + delta;

        if (value < min || value > max) {
            playErrorSound((Player) e.getWhoClicked());
            return current;
        }

        playConfirmationSound((Player) e.getWhoClicked());
        return value;
    }

    private String formatAlgorithm(String name) {
        boolean active = algorithm.name().equals(name);
        return (active ? "§a■ " : "§c■ ") + name;
    }

    private MazeScheme generateMazeScheme() {
        MazeScheme mazeScheme = new MazeScheme(size, size, algorithm);

        mazeScheme.setCenterHoleSize(holeSize);
        mazeScheme.setTopBottomEntrance(true);

        switch (algorithm) {
            case DFS -> {
                DFSAlgorithm.generate(mazeScheme);
                break;
            }
            case KRUSKAL ->  {
                KruskalAlgorithm.generate(mazeScheme);
                break;
            }
            case PRIM ->  {
                PrimAlgorithm.generate(mazeScheme);
                break;
            }
        }

        return mazeScheme;
    }

    private GameMap createGameMap(String mapName) {
        GameMap gameMap = new GameMap(generateMazeScheme(), mapName);
        gameMap.createWorld();
        return gameMap;
    }
}