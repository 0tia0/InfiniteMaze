package me.mattia.maze.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.mattia.maze.InfiniteMaze;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@RequiredArgsConstructor
public abstract class GUI {
    @Getter private final ChestGui gui;
    protected final StaticPane staticPane;
    protected final InfiniteMaze infiniteMaze;

    //
    // USEFUL FUNCTIONS
    //

    public void addItem(GuiItem item, int x, int y) {
        this.staticPane.addItem(item, x, y);
    }

    //
    // GUI SOUNDS
    //

    public void playErrorSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 2f, 0.5f);
    }

    public void playConfirmationSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,2f,1.2f);
    }

    //
    // DEFAILT ITEMS
    //

    public ItemStack getGrayFillerItem() {
        CustomizableItem fillerItem = new CustomizableItem(Material.GRAY_STAINED_GLASS_PANE);
        fillerItem.setName("§7Infinite Maze");
        fillerItem.setLore(List.of("", "§7Made with §c❤ §7by 0tia0", "§7github.com/0tia0", ""));
        return fillerItem;
    }

    public ItemStack getBlackFillerItem() {
        CustomizableItem fillerItem = new CustomizableItem(Material.BLACK_STAINED_GLASS_PANE);
        fillerItem.setName("§7Infinite Maze");
        fillerItem.setLore(List.of("", "§7Made with §c❤ §7by 0tia0", "§7github.com/0tia0", ""));
        return fillerItem;
    }

    public ItemStack getWhiteFillerItem() {
        CustomizableItem fillerItem = new CustomizableItem(Material.WHITE_STAINED_GLASS_PANE);
        fillerItem.setName("§7Infinite Maze");
        fillerItem.setLore(List.of("", "§7Made with §c❤ §7by 0tia0", "§7github.com/0tia0", ""));
        return fillerItem;
    }

    public ItemStack getExitItem() {
        CustomizableItem closeItem = new CustomizableItem(Material.BARRIER);
        closeItem.setName("§c❌ Exit");
        closeItem.setLore(List.of("", "§7Click to close this GUI!", ""));
        return closeItem;
    }

    //
    // DEFAULT BUTTONS
    //

    public GuiItem getExitButton() {
        return new GuiItem(getExitItem(), e -> {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            playErrorSound((Player) e.getWhoClicked());
        });
    }

    public GuiItem getFillerButton(ItemStack fillerItem) {
        return new GuiItem(fillerItem, e -> {
            e.setCancelled(true);
        });
    }

    //
    // DEFAULT PATTERNS
    //

    public PatternPane getLargeChestPatternPane() {
        Pattern pattern = new Pattern(
                "100000001",
                "100000001",
                "100000001",
                "100000001",
                "100000001",
                "122222221"
        );

        PatternPane pane = new PatternPane(0, 0, 9, 6, pattern);

        pane.bindItem('0', getFillerButton(getGrayFillerItem()));
        pane.bindItem('1', getFillerButton(getWhiteFillerItem()));
        pane.bindItem('2', getFillerButton(getBlackFillerItem()));

        return pane;
    }
}
