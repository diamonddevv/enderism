package net.diamonddev.enderism.item.music;

import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class InstrumentWrapper {
    private final InstrumentBean bean;

    public InstrumentWrapper(InstrumentBean instrument) {
        this.bean = instrument;
    }

    public SoundEvent getDefaultSound() {
        return Registries.SOUND_EVENT.get(new Identifier(bean.defaultSoundId));
    }

    public boolean containsInstrumentItem(Identifier itemId) {
        return instrumentIdModifierBeansToIdsAdapter(bean.instrumentItemIds).contains(itemId);
    }

    public String getIdentifier() {
        return bean.nonSerializedIdentifier;
    }

    public float getPitchByItemId(Identifier itemId) {
        for (var bean : bean.instrumentItemIds) {
            if (bean.stringifiedId.equals(itemId.toString())) return bean.pitch;
        }
        return 1f;
    }

    private static ArrayList<Identifier> instrumentIdModifierBeansToIdsAdapter(ArrayList<InstrumentBean.InstrumentItemModifierBean> beans) {
        ArrayList<Identifier> identifiers = new ArrayList<>();
        for (var bean : beans) identifiers.add(new Identifier(bean.stringifiedId));
        return identifiers;
    }
}
