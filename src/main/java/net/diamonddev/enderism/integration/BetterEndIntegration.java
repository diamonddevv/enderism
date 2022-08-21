package net.diamonddev.enderism.integration;

import net.diamonddev.enderism.api.AbstractModIntegration;
import net.diamonddev.enderism.api.IdentifierBuilder;
import net.minecraft.item.Item;

import java.util.ArrayList;


public class BetterEndIntegration extends AbstractModIntegration {
    public BetterEndIntegration() {
        super("betterend");
    }

    public static ArrayList<Item> betterEndElytras = new ArrayList<>();

    private final IdentifierBuilder idb = this.getIdbuilder();
    @Override
    public void onInitializeWithMod() {
        betterEndElytras.add(getItem(idb.build("elytra_armored")));
        betterEndElytras.add(getItem(idb.build("elytra_crystalite")));
    }
}
