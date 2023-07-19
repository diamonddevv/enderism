package net.diamonddev.enderism.resource.type;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.music.MusicSheetBean;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

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

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(ID);
        keys.add(KEY);
        keys.add(COOLDOWN);
    }
}
