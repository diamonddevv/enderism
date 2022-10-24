package net.diamonddev.enderism.integration;

import net.diamonddev.enderism.api.IdentifierBuilder;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Optionally-Dependent Mod Integration Framework Superclass. Will be moved into a Library and expanded upon.
 */
public abstract class AbstractModIntegration {

    private final IdentifierBuilder idbuilder;
    public final String modid;
    public final Logger integrationlogger;


    public static ArrayList<AbstractModIntegration> integrations = new ArrayList<>();

    /**
     * Optionally-Dependent Mod Integration Constructor.
     * @param targetModID - Mod ID of the Target Mod
     * @param associatedModName - Name of this mod. (i.e. "DiaLabs", "Enderism", "FrogJam", etc.)
     */
    protected AbstractModIntegration(String targetModID, String associatedModName) {
        this.idbuilder = new IdentifierBuilder(targetModID);
        this.modid = targetModID;
        String modname = FabricLoaderImpl.INSTANCE.getModContainer(this.modid).orElseThrow().getMetadata().getName();

        integrations.add(this);
        integrationlogger = LogManager.getLogger(associatedModName + " Mod Integration - " + modname);
    }

    public boolean getModLoaded() {
        return FabricLoaderImpl.INSTANCE.isModLoaded(this.modid);
    }

    public Identifier buildIdentifier(String path) {
        return idbuilder.build(path);
    }
    public Registry<?> getRegistry(String path) {
        return Registry.REGISTRIES.get(this.idbuilder.build(path));
    }
    public <T> T getRegistryEntry(Registry<T> registry, String path) {
        return registry.get(idbuilder.build(path));
    }

    /**
     * Called on TitleScreen.init();
     */
    public abstract void onModsLoaded();
}
