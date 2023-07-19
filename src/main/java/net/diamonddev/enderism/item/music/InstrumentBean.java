package net.diamonddev.enderism.item.music;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static net.diamonddev.enderism.resource.type.MusicInstrumentResourceType.*;

public class InstrumentBean {

   public transient String nonSerializedIdentifier; // transient fields get ignored by gson, so we need to autofill that

    @SerializedName(DEFAULT_SOUND)
    public String defaultSoundId;

    @SerializedName(ITEMS)
    public ArrayList<String> instrumentItemIds;
}
