package net.diamonddev.enderism.item.music;

import com.google.gson.annotations.SerializedName;

import static net.diamonddev.enderism.resource.type.MusicSheetResourceType.*;

public class SerializedMusicSheet {
    @SerializedName(ID)
    public String id;

    @SerializedName(KEY)
    public String descTranslationKey;

    @SerializedName(COOLDOWN)
    public int cooldown = 20;
}
