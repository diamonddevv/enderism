package net.diamonddev.enderism.init;

import net.diamonddev.enderism.item.CursedChorusItem;
import net.diamonddev.enderism.api.Identifier;
import net.diamonddev.enderism.api.Registerable;
import net.minecraft.util.registry.Registry;

public class ItemInit implements Registerable {


    public static CursedChorusItem CURSED_CHORUS = new CursedChorusItem();

    @Override
    public void register() {
        Registry.register(Registry.ITEM, new Identifier("cursed_chorus_fruit"), CURSED_CHORUS);
    }
}
