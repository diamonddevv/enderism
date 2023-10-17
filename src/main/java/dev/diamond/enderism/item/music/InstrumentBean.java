package dev.diamond.enderism.item.music;

import com.google.gson.annotations.SerializedName;
import dev.diamond.enderism.resource.type.MusicInstrumentResourceType;

import java.util.ArrayList;

public class InstrumentBean {

   public transient String nonSerializedIdentifier; // transient fields get ignored by gson, so we need to manually fill that

    @SerializedName(MusicInstrumentResourceType.ADDITIVE)
    public String addTo = null;

    @SerializedName(MusicInstrumentResourceType.DEFAULT_SOUND)
    public String defaultSoundId;

    @SerializedName(MusicInstrumentResourceType.ITEMS)
    public ArrayList<InstrumentItemModifierBean> instrumentItemIds;


    public static class InstrumentItemModifierBean {
        @SerializedName(MusicInstrumentResourceType.ITEM)
        public ItemBean item;

        @SerializedName(MusicInstrumentResourceType.MODIFIER_PITCH)
        public float pitch = 1.0f;
    }

    public static class ItemBean {
        @SerializedName(MusicInstrumentResourceType.MODIFIER_ID)
        public String stringifiedId;

        @SerializedName(MusicInstrumentResourceType.NBT)
        public String nbtString = null;
    }
}
