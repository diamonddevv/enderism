package net.diamonddev.enderism.resource.type;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class MusicSheetResourceType implements DataLoaderResourceType {


    public static final String NAME = "name";
    public static final String TBN = "ticksBetweenNotes";
    public static final String NOTES = "notes";
    @Override
    public Identifier getId() {
        return EnderismMod.id("music_sheet");
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(NAME);
        keys.add(TBN);
        keys.add(NOTES);
    }
}
