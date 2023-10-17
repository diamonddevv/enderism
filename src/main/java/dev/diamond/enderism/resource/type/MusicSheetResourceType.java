package dev.diamond.enderism.resource.type;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.item.music.MusicSheetBean;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class MusicSheetResourceType implements CognitionResourceType {


    public static final String
            ID = "id",
            KEY = "key",
            COOLDOWN = "length";

    @Override
    public Identifier getId() {
        return EnderismMod.id("music_sheet");
    }


    public static MusicSheetBean getAsSheet(CognitionDataResource resource) {
        return resource.getAsClass(MusicSheetBean.class);
    }

    public static BiFunction<JsonObject, Gson, MusicSheetBean> REMAPPER = (obj, gson) -> gson.fromJson(obj, MusicSheetBean.class);

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(ID);
        keys.add(KEY);
        keys.add(COOLDOWN);
    }
}
