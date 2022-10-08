package net.diamonddev.enderism.integration;

import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;


public class BetterEndIntegration extends AbstractModIntegration {
    public BetterEndIntegration() {
        super("betterend");
    }

    public static final ArrayList<Item> betterEndElytras = new ArrayList<>();
    @Override
    public void onModsLoaded() {
        betterEndElytras.add(getRegistryEntry(Registry.ITEM, "armored_elytra"));
        betterEndElytras.add(getRegistryEntry(Registry.ITEM, "elytra_crystalite"));
    }
}
