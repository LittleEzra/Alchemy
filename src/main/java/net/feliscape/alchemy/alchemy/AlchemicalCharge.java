package net.feliscape.alchemy.alchemy;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nullable;

public class AlchemicalCharge {
    private final int fire;
    private final int water;
    private final int nature;
    private final int enchantment;
    private final int spirit;

    public AlchemicalCharge(int fire, int water, int nature, int enchantment, int spirit) {
        this.fire = fire;
        this.water = water;
        this.nature = nature;
        this.enchantment = enchantment;
        this.spirit = spirit;
    }

    public int get(Element element){
        return switch (element){
            case FIRE -> fire;
            case WATER -> water;
            case NATURE -> nature;
            case ENCHANTMENT -> enchantment;
            case SPIRIT -> spirit;
        };
    }

    public int getFire() {
        return fire;
    }

    public int getWater() {
        return water;
    }

    public int getNature() {
        return nature;
    }

    public int getEnchantment() {
        return enchantment;
    }

    public int getSpirit() {
        return spirit;
    }

    public JsonObject toJson(){
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("fire", this.fire);
        jsonobject.addProperty("water", this.water);
        jsonobject.addProperty("nature", this.nature);
        jsonobject.addProperty("enchantment", this.enchantment);
        jsonobject.addProperty("spirit", this.spirit);
        return jsonobject;
    }

    public static AlchemicalCharge fromJson(@Nullable JsonElement pJson){
        if (pJson == null || !pJson.isJsonObject()) throw new IllegalArgumentException("JsonElement is null or not JsonObject");

        int fire = pJson.getAsJsonObject().get("fire").getAsInt();
        int water = pJson.getAsJsonObject().get("water").getAsInt();
        int nature = pJson.getAsJsonObject().get("nature").getAsInt();
        int enchantment = pJson.getAsJsonObject().get("enchantment").getAsInt();
        int spirit = pJson.getAsJsonObject().get("spirit").getAsInt();

        return new AlchemicalCharge(fire, water, nature, enchantment, spirit);
    }

    public void toNetwork(FriendlyByteBuf pBuffer){
        pBuffer.writeInt(fire);
        pBuffer.writeInt(water);
        pBuffer.writeInt(nature);
        pBuffer.writeInt(enchantment);
        pBuffer.writeInt(spirit);
    }
    public static AlchemicalCharge fromNetwork(FriendlyByteBuf pBuffer){
        return new AlchemicalCharge(pBuffer.readInt(), pBuffer.readInt(), pBuffer.readInt(), pBuffer.readInt(), pBuffer.readInt());
    }
}
