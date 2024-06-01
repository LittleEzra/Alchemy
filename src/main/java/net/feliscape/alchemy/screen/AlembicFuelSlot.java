package net.feliscape.alchemy.screen;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlembicFuelSlot extends SlotItemHandler {
    private final AlembicMenu menu;

    public AlembicFuelSlot(AlembicMenu menu, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.menu = menu;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean mayPlace(ItemStack pStack) {
        return this.menu.isFuel(pStack) || isBucket(pStack);
    }

    public int getMaxStackSize(ItemStack pStack) {
        return isBucket(pStack) ? 1 : super.getMaxStackSize(pStack);
    }

    public static boolean isBucket(ItemStack pStack) {
        return pStack.is(Items.BUCKET);
    }
}
