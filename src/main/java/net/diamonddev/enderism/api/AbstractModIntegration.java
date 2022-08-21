package net.diamonddev.enderism.api;

import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;

public abstract class AbstractModIntegration {

    private final IdentifierBuilder idbuilder;
    private final String modid;

    public static final ArrayList<AbstractModIntegration> INTEGRATIONS = Lists.newArrayList();

    public AbstractModIntegration(String targetModID) {
        this.idbuilder = new IdentifierBuilder(targetModID);
        this.modid = targetModID;
    }

    public boolean getModLoaded() {
        return FabricLoaderImpl.INSTANCE.isModLoaded(this.modid);
    }

    @Deprecated
    public Object getDefaultedRegistryEntry(DefaultedRegistry<?> registry, String path) {
        return registry.get(idbuilder.build(path));
    }

    public Block getBlock(net.minecraft.util.Identifier id) {
        return Registry.BLOCK.get(id);
    }

    public static Item getItem(net.minecraft.util.Identifier id) {
        return Registry.ITEM.get(id);
    }

    public IdentifierBuilder getIdbuilder() {
        return idbuilder;
    }

    public abstract void onInitializeWithMod();

    public static AbstractModIntegration register(AbstractModIntegration integration) {
        INTEGRATIONS.add(integration);
        return integration;
    }
}
