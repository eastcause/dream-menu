package cc.dreamcode.menu.adventure;

import cc.dreamcode.menu.DreamMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.item.ItemSlot;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import lombok.NonNull;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class BukkitMenuBuilder extends DreamMenuBuilder<BukkitMenu, InventoryType, ItemStack> {

    public BukkitMenuBuilder(@NonNull String name, int rows, Map<Integer, ItemStack> items) {
        super(InventoryType.CHEST, name, rows, items == null ? new HashMap<>() : items);
    }

    public BukkitMenuBuilder(@NonNull String name, int rows, Map<Integer, ItemStack> items,
                             ItemStack background, @NonNull Map<String, ItemSlot<ItemStack>> defaultItems) {
        super(InventoryType.CHEST, name, rows, items == null ? new HashMap<>() : items, background, defaultItems);
    }

    public BukkitMenuBuilder(@NonNull InventoryType inventoryType, @NonNull String name, Map<Integer, ItemStack> items) {
        super(inventoryType, name, inventoryType.getDefaultSize(), items == null ? new HashMap<>() : items);
    }

    public BukkitMenuBuilder(@NonNull InventoryType inventoryType, @NonNull String name, Map<Integer, ItemStack> items,
                             ItemStack background, @NonNull Map<String, ItemSlot<ItemStack>> defaultItems) {
        super(inventoryType, name, inventoryType.getDefaultSize(), items == null ? new HashMap<>() : items, background, defaultItems);
    }

    @Override
    public BukkitMenu buildEmpty() {
        if (this.getInventoryType().equals(InventoryType.CHEST)) {
            return new BukkitMenu(this.getName(), this.getRows(), 0);
        }

        return new BukkitMenu(this.getInventoryType(), this.getName(), 0);
    }

    @Override
    public BukkitMenu buildEmpty(@NonNull Map<String, Object> replaceMap) {
        if (this.getInventoryType().equals(InventoryType.CHEST)) {
            return new BukkitMenu(this.getName(), replaceMap, this.getRows(), 0);
        }

        return new BukkitMenu(this.getInventoryType(), this.getName(), replaceMap, 0);
    }

    @Override
    public BukkitMenu buildWithItems() {
        final BukkitMenu bukkitMenu = this.buildEmpty();

        if (this.getBackground() != null) {
            ItemStack background = ItemBuilder.of(this.getBackground())
                    .fixColors()
                    .toItemStack();

            IntStream.range(0, (bukkitMenu.getSize() - 1))
                    .forEach(i -> bukkitMenu.setItem(i, background));
        }

        this.getItems().forEach((slot, item) ->
                bukkitMenu.setItem(slot, ItemBuilder.of(item)
                        .fixColors()
                        .toItemStack()));

        this.getDefaultItems().forEach((key, itemSlot) ->
                bukkitMenu.setItem(itemSlot.getSlot(), ItemBuilder.of(itemSlot.getI())
                        .fixColors()
                        .toItemStack()));

        return bukkitMenu;
    }

    @Override
    public BukkitMenu buildWithItems(@NonNull Map<String, Object> replaceMap) {
        final BukkitMenu bukkitMenu = this.buildEmpty(replaceMap);

        if (this.getBackground() != null) {
            ItemStack background = ItemBuilder.of(this.getBackground())
                    .fixColors()
                    .toItemStack();

            IntStream.range(0, (bukkitMenu.getSize() - 1))
                    .forEach(i -> bukkitMenu.setItem(i, background));
        }

        this.getItems().forEach((slot, item) ->
                bukkitMenu.setItem(slot, ItemBuilder.of(item)
                        .fixColors(replaceMap)
                        .toItemStack()));

        this.getDefaultItems().forEach((key, itemSlot) ->
                bukkitMenu.setItem(itemSlot.getSlot(), ItemBuilder.of(itemSlot.getI())
                        .fixColors(replaceMap)
                        .toItemStack()));

        return bukkitMenu;
    }
}
