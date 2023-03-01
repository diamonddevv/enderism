package net.diamonddev.enderism.item.music;

import com.google.gson.annotations.SerializedName;

import static net.diamonddev.enderism.resource.type.MusicSheetResourceType.*;

public class SerializedNotes {
    @SerializedName(NAME)
    public String id;

    @SerializedName(TBN)
    public int tickdiff;

    @SerializedName(NOTES)
    public int[] notes;
}
