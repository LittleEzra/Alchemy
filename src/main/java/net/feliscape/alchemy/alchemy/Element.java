package net.feliscape.alchemy.alchemy;

import net.minecraft.ChatFormatting;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Difficulty;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.function.IntFunction;

public enum Element implements StringRepresentable {
    FIRE(0, "Fire", ChatFormatting.RED),
    WATER(1, "Water", ChatFormatting.BLUE),
    NATURE(2, "Nature", ChatFormatting.GREEN),
    ENCHANTMENT(3, "Enchantment", ChatFormatting.DARK_PURPLE),
    SPIRIT(4, "Spirit", ChatFormatting.AQUA);

    private final int id;
    private final String name;
    private final ChatFormatting chatFormatting;

    private static final IntFunction<Element> BY_ID = ByIdMap.continuous(Element::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);

    Element(int id, String name, ChatFormatting color){
        this.id = id;
        this.name = name;
        this.chatFormatting = color;
    }

    public int getId(){
        return id;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
    public String getLowercaseName() {
        return name.toLowerCase(Locale.ROOT);
    }

    public ChatFormatting getChatFormatting(){
        return chatFormatting;
    }
}
