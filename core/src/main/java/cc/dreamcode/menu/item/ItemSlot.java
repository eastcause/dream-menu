package cc.dreamcode.menu.item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemSlot<I> {
    private int slot;
    private I i;
}
