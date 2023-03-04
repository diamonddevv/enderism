package net.diamonddev.enderism.item.music;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import static net.diamonddev.enderism.resource.type.MusicSheetResourceType.*;

public class SerializedMusicSheet {
    @SerializedName(NAME)
    public String name;

    @SerializedName(SOUNDS)
    public HashMap<String, String> hashedInstruments;
}
