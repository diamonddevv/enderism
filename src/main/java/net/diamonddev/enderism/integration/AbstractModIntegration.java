package net.diamonddev.enderism.integration;

import net.diamonddev.enderism.api.IdentifierBuilder;
import net.diamonddev.enderism.api.Registerable;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Optional;

public abstract class AbstractModIntegration {

    private final IdentifierBuilder idbuilder;
    public final String modid;
    private final String modname;
    public final Logger integrationlogger;


    public static ArrayList<AbstractModIntegration> integrations = new ArrayList<>();


    protected AbstractModIntegration(String targetModID) {
        this.idbuilder = new IdentifierBuilder(targetModID);
        this.modid = targetModID;
        Optional<ModContainer> opcon = FabricLoaderImpl.INSTANCE.getModContainer(this.modid);
        if (opcon.isPresent()) {
            this.modname = opcon.get().getMetadata().getName();
        } else {
            this.modname = "";
        }

        integrations.add(this);
        integrationlogger = LogManager.getLogger("Enderism Mod Integration - " + this.modname + "");
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
