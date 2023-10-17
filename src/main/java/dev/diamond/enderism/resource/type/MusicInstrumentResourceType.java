package dev.diamond.enderism.resource.type;

import dev.diamond.enderism.item.music.InstrumentWrapper;
import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.item.music.InstrumentBean;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class MusicInstrumentResourceType implements CognitionResourceType {
    @Override
    public Identifier getId() {
        return EnderismMod.id("instrument");
    }

    public static final String
            ADDITIVE = "extends",
            DEFAULT_SOUND = "default_sound",
            ITEMS = "instrument_items";

    public static final String // instrument id bean
            ITEM = "item", MODIFIER_ID = "id", NBT = "nbt", MODIFIER_PITCH = "pitch";


    public static InstrumentWrapper wrap(CognitionDataResource resource) {
        var serialized = resource.getAsClass(InstrumentBean.class);
        serialized.nonSerializedIdentifier = getNSI(resource.getId());
        return new InstrumentWrapper(serialized);
    }

    public static String getNSI(Identifier id) {
        String[] split = id.getPath().split("/");
        return split[split.length-1].split("\\.")[0]; // this will isolate the filename without the extension
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(DEFAULT_SOUND);
        keys.add(ITEMS);
    }
}
