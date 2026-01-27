package me.mattia.maze.gui;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CustomizableItem extends ItemStack {
    List<String> composableLore;
    ItemMeta meta;

    public CustomizableItem(Material material) {
        super(material);
        this.meta = this.getItemMeta();
        this.setItemMeta(this.meta);
    }

    public void setName(String name) {
        this.meta.setDisplayName(name);
        this.setItemMeta(this.meta);
    }

    public void setLore(List<String> lore) {
        this.meta.setLore(lore);
        this.composableLore = lore;
        this.setItemMeta(this.meta);
    }

    public void addLore(String lore) {
        this.composableLore.add(lore);
        this.meta.setLore(this.composableLore);
        this.setItemMeta(this.meta);
    }
}
