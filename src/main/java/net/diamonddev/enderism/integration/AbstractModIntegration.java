package net.diamonddev.enderism.integration;

import net.diamonddev.enderism.api.IdentifierBuilder;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public abstract class AbstractModIntegration {

    private final IdentifierBuilder idbuilder;
    private final String modid;

    public static ArrayList<AbstractModIntegration> integrations = new ArrayList<>();
    protected AbstractModIntegration(String targetModID) {
        this.idbuilder = new IdentifierBuilder(targetModID);
        this.modid = targetModID;
        integrations.add(this);
    }

    public boolean getModLoaded() {
        return FabricLoaderImpl.INSTANCE.isModLoaded(this.modid);
    }

    public <T> T getRegistryEntry(Registry<T> registry, String path) {
        return registry.get(idbuilder.build(path));
    }

    public IdentifierBuilder getIdbuilder() {
        return idbuilder;
    }

    public abstract void onModsLoaded();
}
