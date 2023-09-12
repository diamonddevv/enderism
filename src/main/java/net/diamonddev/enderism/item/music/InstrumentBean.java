package net.diamonddev.enderism.item.music;

import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;

import static net.diamonddev.enderism.resource.type.MusicInstrumentResourceType.*;

public class InstrumentBean {

   public transient String nonSerializedIdentifier; // transient fields get ignored by gson, so we need to manually fill that

    @SerializedName(ADDITIVE)
    public String addTo = null;

    @SerializedName(DEFAULT_SOUND)
    public String defaultSoundId;

    @SerializedName(ITEMS)
    public ArrayList<InstrumentItemModifierBean> instrumentItemIds;


    public static class InstrumentItemModifierBean {
        @SerializedName(ITEM)
        public ItemBean item;

        @SerializedName(MODIFIER_PITCH)
        public float pitch = 1.0f;
    }

    public static class ItemBean {
        @SerializedName(MODIFIER_ID)
        public String stringifiedId;

        @SerializedName(NBT)
        public String nbtString = null;
    }
}
