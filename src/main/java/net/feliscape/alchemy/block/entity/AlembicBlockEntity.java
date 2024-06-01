package net.feliscape.alchemy.block.entity;

import com.google.common.collect.ImmutableMap;
import net.feliscape.alchemy.Alchemy;
import net.feliscape.alchemy.alchemy.AlchemicalCharge;
import net.feliscape.alchemy.alchemy.Element;
import net.feliscape.alchemy.item.ModItems;
import net.feliscape.alchemy.item.custom.TabletItem;
import net.feliscape.alchemy.recipe.DistillationRecipe;
import net.feliscape.alchemy.screen.AlembicMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlembicBlockEntity extends BlockEntity implements MenuProvider, StackedContentsCompatible {
    private final Map<Element, Integer> elements = new HashMap<>(Map.of(
            Element.FIRE, 0,
            Element.WATER, 0,
            Element.NATURE, 0,
            Element.ENCHANTMENT, 0,
            Element.SPIRIT, 0
    ));

    protected static final int SLOT_INPUT = 0;
    protected static final int SLOT_FUEL = 1;
    protected static final int SLOT_TABLET = 2;
    protected static final int SLOT_COUNT = 3;

    public static final int DATA_LIT_TIME = 0;
    public static final int DATA_LIT_DURATION = 1;
    public static final int DATA_COOKING_PROGRESS = 2;
    public static final int DATA_COOKING_TOTAL_TIME = 3;
    public static final int NUM_DATA_VALUES = 4;
    public static final int BURN_TIME_STANDARD = 200;
    public static final int BURN_COOL_SPEED = 2;
    public static final int TABLET_CHARGE_TIME = 3;

    private final ItemStackHandler itemHandler = new ItemStackHandler(SLOT_COUNT){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return slot == SLOT_TABLET ? 1 : super.getStackLimit(slot, stack);
        }

        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            super.setStackInSlot(slot, stack);
            ItemStack itemstack = this.getStackInSlot(slot);
            boolean addingToStack = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack);

            if (slot == SLOT_INPUT && !addingToStack && !(stack.getItem() instanceof TabletItem)) {
                AlembicBlockEntity.this.cookingTotalTime = getTotalCookTime();
                AlembicBlockEntity.this.cookingProgress = 0;
                AlembicBlockEntity.this.setChanged();
            } else if (slot == SLOT_TABLET || (slot == SLOT_INPUT && stack.getItem() instanceof TabletItem)){
                AlembicBlockEntity.this.fillProgress = 0;
                AlembicBlockEntity.this.setChanged();
            }
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    int litTime;
    int litDuration;
    int cookingProgress;
    int cookingTotalTime;;
    int fillProgress;
    protected final ContainerData data;

    public AlembicBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALEMBIC_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> AlembicBlockEntity.this.litTime;
                    case 1 -> AlembicBlockEntity.this.litDuration;
                    case 2 -> AlembicBlockEntity.this.cookingProgress;
                    case 3 -> AlembicBlockEntity.this.cookingTotalTime;

                    case 4 -> AlembicBlockEntity.this.elements.get(Element.FIRE);
                    case 5 -> AlembicBlockEntity.this.elements.get(Element.WATER);
                    case 6 -> AlembicBlockEntity.this.elements.get(Element.NATURE);
                    case 7 -> AlembicBlockEntity.this.elements.get(Element.ENCHANTMENT);
                    case 8 -> AlembicBlockEntity.this.elements.get(Element.SPIRIT);
                    default ->  0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> AlembicBlockEntity.this.litTime = value;
                    case 1 -> AlembicBlockEntity.this.litDuration = value;
                    case 2 -> AlembicBlockEntity.this.cookingProgress = value;
                    case 3 -> AlembicBlockEntity.this.cookingTotalTime = value;

                    case 4 -> AlembicBlockEntity.this.setElement(Element.FIRE, value);
                    case 5 -> AlembicBlockEntity.this.setElement(Element.WATER, value);
                    case 6 -> AlembicBlockEntity.this.setElement(Element.NATURE, value);
                    case 7 -> AlembicBlockEntity.this.setElement(Element.ENCHANTMENT, value);
                    case 8 -> AlembicBlockEntity.this.setElement(Element.SPIRIT, value);
                }
            }

            public int getCount() {
                return 9;
            }
        };
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("Inventory"));
        if (itemHandler.getSlots() != SLOT_COUNT) itemHandler.setSize(SLOT_COUNT);
        this.litTime = pTag.getInt("BurnTime");
        this.cookingProgress = pTag.getInt("CookTime");
        this.cookingTotalTime = pTag.getInt("CookTimeTotal");
        this.fillProgress = pTag.getInt("FillProgress");
        this.litDuration = this.getBurnDuration(this.itemHandler.getStackInSlot(SLOT_FUEL));
        for (Element element : elements.keySet()){
            this.setElement(element, pTag.getInt("Element." + element.getSerializedName()));
        }
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("BurnTime", this.litTime);
        pTag.putInt("CookTime", this.cookingProgress);
        pTag.putInt("CookTimeTotal", this.cookingTotalTime);
        pTag.putInt("FillProgress", this.fillProgress);
        pTag.put("Inventory", itemHandler.serializeNBT());
        for (Element element : elements.keySet()){
            pTag.putInt("Element." + element.getSerializedName(), elements.get(element));
        }
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, AlembicBlockEntity pBlockEntity) {
        boolean wasLit = pBlockEntity.isLit();
        boolean changed = false;
        if (pBlockEntity.isLit()) {
            --pBlockEntity.litTime;
        }

        ItemStack fuelItem = pBlockEntity.itemHandler.getStackInSlot(SLOT_FUEL);
        boolean ingredientNotEmpty = !pBlockEntity.itemHandler.getStackInSlot(SLOT_INPUT).isEmpty();
        boolean fuelItemNotEmpty = !fuelItem.isEmpty();

        ItemStack tabletStack = pBlockEntity.itemHandler.getStackInSlot(SLOT_TABLET);
        if (tabletStack.getItem() instanceof TabletItem tabletItem){ // Charge Tablet
            ++pBlockEntity.fillProgress;
            if (pBlockEntity.fillProgress >= TABLET_CHARGE_TIME && pBlockEntity.getElement(tabletItem.getElement()) > 0){
                pBlockEntity.fillProgress = 0;
                pBlockEntity.addElement(tabletItem.getElement(), -1);
                TabletItem.setAmount(tabletStack, TabletItem.getAmount(tabletStack) + 1);
            }
        }
        ItemStack inputStack = pBlockEntity.itemHandler.getStackInSlot(SLOT_INPUT);
        if (inputStack.getItem() instanceof TabletItem tabletItem){ // Empty Tablet
            ++pBlockEntity.fillProgress;
            if (pBlockEntity.fillProgress >= TABLET_CHARGE_TIME){
                pBlockEntity.fillProgress = 0;
                if (TabletItem.getAmount(inputStack) > 0){
                    TabletItem.setAmount(inputStack, TabletItem.getAmount(inputStack) - 1);
                    pBlockEntity.addElement(tabletItem.getElement(), 1);
                }
            }
            changed = true;
        }

        if (pBlockEntity.isLit() || fuelItemNotEmpty && ingredientNotEmpty) {
            if (!pBlockEntity.isLit() && pBlockEntity.canDistill()) {
                pBlockEntity.litTime = pBlockEntity.getBurnDuration(fuelItem);
                pBlockEntity.litDuration = pBlockEntity.litTime;
                if (pBlockEntity.isLit()) {
                    changed = true;
                    if (fuelItem.hasCraftingRemainingItem())
                        pBlockEntity.itemHandler.setStackInSlot(SLOT_FUEL, fuelItem.getCraftingRemainingItem());
                    else {
                        fuelItem.shrink(1);
                        if (fuelItem.isEmpty()) {
                            pBlockEntity.itemHandler.setStackInSlot(SLOT_FUEL, fuelItem.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (pBlockEntity.isLit() && pBlockEntity.canDistill()) {
                ++pBlockEntity.cookingProgress;
                changed = true;
                if (pBlockEntity.cookingProgress >= pBlockEntity.cookingTotalTime) {
                    pBlockEntity.cookingProgress = 0;
                    pBlockEntity.cookingTotalTime = getTotalCookTime();
                    pBlockEntity.distill();
                }
            } else {
                pBlockEntity.cookingProgress = 0;
            }
        } else if (!pBlockEntity.isLit() && pBlockEntity.cookingProgress > 0) {
            pBlockEntity.cookingProgress = Mth.clamp(pBlockEntity.cookingProgress - 2, 0, pBlockEntity.cookingTotalTime);
        }

        if (wasLit != pBlockEntity.isLit()) {
            changed = true;
            pState = pState.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(pBlockEntity.isLit()));
            pLevel.setBlock(pPos, pState, 3);
        }

        if (changed) {
            setChanged(pLevel, pPos, pState);
        }

    }

    private boolean canDistill(){
        ItemStack itemStack = this.itemHandler.getStackInSlot(SLOT_INPUT);
        ItemStack tabletStack = this.itemHandler.getStackInSlot(SLOT_TABLET);
        return hasRecipe() || itemStack.is(Items.ENCHANTED_BOOK) ||
                (itemStack.isEnchanted() && (tabletStack.isEmpty() || (tabletStack.is(itemStack.getItem()) && itemStack.getCount() < itemStack.getMaxStackSize())));
    }

    private boolean distill() {
        if (canDistill()) {
            ItemStack itemStack = this.itemHandler.extractItem(SLOT_INPUT, 1, false);
            if (itemStack.isEnchanted() || itemStack.is(Items.ENCHANTED_BOOK)){
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);

                for (Enchantment enchantment : enchantments.keySet()){
                    if (enchantment.isCurse()) continue;

                    int level = enchantments.get(enchantment);
                    addElement(Element.ENCHANTMENT, Math.round((enchantment.getRarity().ordinal() + 1) * (level / 2f)));
                }

                ItemStack unenchantedStack = itemStack.copyWithCount(1);
                unenchantedStack.removeTagKey("Enchantments");
                unenchantedStack.removeTagKey("StoredEnchantments");

                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack).entrySet().stream().filter((enchantmentIntegerEntry) -> {
                    return enchantmentIntegerEntry.getKey().isCurse();
                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                EnchantmentHelper.setEnchantments(map, unenchantedStack);
                unenchantedStack.setRepairCost(0);
                if (unenchantedStack.is(Items.ENCHANTED_BOOK) && map.size() == 0) {
                    unenchantedStack = new ItemStack(Items.BOOK);
                    if (itemStack.hasCustomHoverName()) {
                        unenchantedStack.setHoverName(itemStack.getHoverName());
                    }
                }

                this.itemHandler.insertItem(SLOT_TABLET, unenchantedStack, false);
                return true;
            }
            else if (hasRecipe()){
                AlchemicalCharge alchemicalCharge = getCraftingResult();
                if (alchemicalCharge == null) {
                    Alchemy.printDebug("No AlchemicalCharge");
                    return false;
                }

                addElement(Element.FIRE, alchemicalCharge.get(Element.FIRE));
                addElement(Element.WATER, alchemicalCharge.get(Element.WATER));
                addElement(Element.NATURE, alchemicalCharge.get(Element.NATURE));
                addElement(Element.ENCHANTMENT, alchemicalCharge.get(Element.ENCHANTMENT));
                addElement(Element.SPIRIT, alchemicalCharge.get(Element.SPIRIT));
                return true;
            }
        }
        return false;
    }

    private Optional<DistillationRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(DistillationRecipe.Type.INSTANCE, inventory, level);
    }
    public boolean hasRecipe(){
        Optional<DistillationRecipe> recipe = getCurrentRecipe();

        return recipe.isPresent();
    }
    private AlchemicalCharge getCraftingResult() {
        Optional<DistillationRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()){
            return null;
        }

        return recipe.get().getAlchemicalCharge();
    }


    private void addElement(Element element, int alchemicalCharge) {
        int elementAmount = elements.get(element);
        elements.put(element, elementAmount + alchemicalCharge);
    }
    private void setElement(Element element, int alchemicalCharge) {
        elements.put(element, alchemicalCharge);
    }
    public int getElement(Element element) {
        return elements.get(element);
    }

    protected int getBurnDuration(ItemStack pFuel) {
        if (pFuel.isEmpty()) {
            return 0;
        } else {
            Item item = pFuel.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(pFuel, RecipeType.SMELTING);
        }
    }

    private static int getTotalCookTime() {
        return 200;
    }

    public void fillStackedContents(StackedContents pHelper) {
        for (int i = 0; i < this.itemHandler.getSlots(); i++){
            pHelper.accountStack(itemHandler.getStackInSlot(i));
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.alchemy.alembic");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AlembicMenu(pContainerId, pPlayerInventory, this, this.data);
    }
}
