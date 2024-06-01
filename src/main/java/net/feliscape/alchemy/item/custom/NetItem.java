package net.feliscape.alchemy.item.custom;

import net.feliscape.alchemy.item.ModItems;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NetItem extends Item {
    public NetItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        DimensionSpecialEffects effects = DimensionSpecialEffects.forType(pLevel.dimensionType());
        float cloudHeight = effects.getCloudHeight();
        if (!Float.isNaN(cloudHeight) && pPlayer.position().y() >= cloudHeight - 4f){
            pPlayer.getInventory().add(new ItemStack(ModItems.CLOUD_FLUFF.get()));
            itemStack.hurtAndBreak(2, pPlayer, (player) -> {
                player.broadcastBreakEvent(pUsedHand);
            });
            return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
