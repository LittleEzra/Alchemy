package net.feliscape.alchemy.item.custom;

import net.feliscape.alchemy.alchemy.Element;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class TabletItem extends Item {
    private final Element element;

    public TabletItem(Properties pProperties, Element element) {
        super(pProperties);
        this.element = element;
    }

    public static ItemStack setAmount(ItemStack itemStack, int charge){
        itemStack.getOrCreateTag().putInt("elementAmount", charge);
        return itemStack;
    }
    public static int getAmount(ItemStack itemStack){
        if (!itemStack.hasTag() || !itemStack.getTag().contains("elementAmount")) return 0;
        return itemStack.getTag().getInt("elementAmount");
    }

    public Element getElement(){
        return element;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if (pStack.hasTag()){
            CompoundTag compoundtag = pStack.getTag();
            int elementAmount = compoundtag.getInt("elementAmount");

            pTooltipComponents.add(Component.translatable("item.alchemy.tablet_tooltip." + element.getLowercaseName(), elementAmount).withStyle(element.getChatFormatting()));
        }
    }
}
