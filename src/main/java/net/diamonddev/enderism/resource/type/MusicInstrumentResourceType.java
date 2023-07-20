package net.diamonddev.enderism.resource.type;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.item.music.InstrumentWrapper;
import net.diamonddev.enderism.item.music.InstrumentBean;
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
            DEFAULT_SOUND = "default_sound",
            ITEMS = "instrument_items";

    public static final String // instrument id bean
            MODIFIER_ID = "item", MODIFIER_PITCH = "pitch";

    public static InstrumentWrapper wrap(CognitionDataResource resource) {
        var serialized = resource.getAsClass(InstrumentBean.class);
        String[] split = resource.getId().getPath().split("/");
        serialized.nonSerializedIdentifier = split[split.length-1].split("\\.")[0]; // this will isolate the filename without the extension
        return new InstrumentWrapper(serialized);
    }

    @Override
    public void addJsonKeys(ArrayList<String> keys) {
        keys.add(DEFAULT_SOUND);
        keys.add(ITEMS);
    }
}
