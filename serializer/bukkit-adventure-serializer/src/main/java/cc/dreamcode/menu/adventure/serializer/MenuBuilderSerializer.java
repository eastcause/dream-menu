package cc.dreamcode.menu.adventure.serializer;

import cc.dreamcode.menu.item.ItemSlot;
import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MenuBuilderSerializer implements ObjectSerializer<BukkitMenuBuilder> {
    /**
     * @param type the type checked for compatibility
     * @return {@code true} if serializer is able to process the {@code type}
     */
    @Override
    public boolean supports(@NonNull Class<? super BukkitMenuBuilder> type) {
        return BukkitMenuBuilder.class.isAssignableFrom(type);
    }

    /**
     * @param object   the object to be serialized
     * @param data     the serialization data
     * @param generics the generic information about the {@code object}
     */
    @Override
    public void serialize(@NonNull BukkitMenuBuilder object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        if (!object.getInventoryType().equals(InventoryType.CHEST)) {
            data.add("type", object.getInventoryType());
        }

        data.add("name", object.getName());

        if (object.getInventoryType().equals(InventoryType.CHEST)) {
            data.add("rows", object.getRows());
        }

        data.addAsMap("items", object.getItems(), Integer.class, ItemStack.class);

        if (object.getBackground() != null) {
            data.add("background", object.getBackground());
        }

        if (object.getDefaultItems() != null && !object.getDefaultItems().isEmpty()) {
            data.add("defaultItems", object.getDefaultItems());
        }
    }

    /**
     * @param data     the source deserialization data
     * @param generics the target generic type for the {@code data}
     * @return the deserialized object
     */
    @Override
    public BukkitMenuBuilder deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        Map<Integer, ItemStack> items = data.getAsMap("items", Integer.class, ItemStack.class);
        ItemStack background = data.containsKey("background")
                ? data.get("background", ItemStack.class)
                : null;
        Map<String, ItemSlot<ItemStack>> defaultItems = data.containsKey("defaultItems")
                ? data.getAsMap("defaultItems", String.class, (Class<ItemSlot<ItemStack>>)(Class<?>)ItemSlot.class)
                : new HashMap<>();

        if (data.containsKey("type")) {
            return new BukkitMenuBuilder(
                    data.get("type", InventoryType.class),
                    name,
                    items,
                    background,
                    defaultItems
            );
        }

        return new BukkitMenuBuilder(
                name,
                data.get("rows", Integer.class),
                items,
                background,
                defaultItems
        );
    }
}
