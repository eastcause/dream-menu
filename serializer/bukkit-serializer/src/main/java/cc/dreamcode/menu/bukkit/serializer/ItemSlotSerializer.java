package cc.dreamcode.menu.bukkit.serializer;

import cc.dreamcode.menu.item.ItemSlot;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class ItemSlotSerializer implements ObjectSerializer<ItemSlot<ItemStack>> {
    @Override
    public boolean supports(@NonNull Class<? super ItemSlot<ItemStack>> type) {
        return ItemSlot.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull ItemSlot<ItemStack> object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("slot", object.getSlot());
        data.add("item", object.getI());
    }

    @Override
    public ItemSlot<ItemStack> deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        int slot = data.get("slot", int.class);
        ItemStack item = data.get("item", ItemStack.class);

        return new ItemSlot<>(slot, item);
    }
}
