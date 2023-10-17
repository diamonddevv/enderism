package dev.diamond.enderism.item.music;

import com.google.gson.annotations.SerializedName;
import dev.diamond.enderism.resource.type.MusicSheetResourceType;

public class MusicSheetBean {
    @SerializedName(MusicSheetResourceType.ID)
    public String id;

    @SerializedName(MusicSheetResourceType.KEY)
    public String descTranslationKey;

    @SerializedName(MusicSheetResourceType.COOLDOWN)
    public int cooldown = 20;
}
