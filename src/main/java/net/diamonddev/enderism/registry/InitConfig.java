package net.diamonddev.enderism.registry;

import com.google.gson.annotations.SerializedName;
import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.config.JsonConfigFile;
import net.diamonddev.libgenetics.common.api.v1.config.JsonConfigFileRegistry;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

public class InitConfig implements RegistryInitializer {

    public static EnderismConfig ENDERISM = new EnderismConfig();
    @Override
    public void register() {
        ENDERISM = JsonConfigFileRegistry.registerAndReadAsSelf(EnderismMod.id("enderism_config"), new EnderismConfig(), EnderismConfig.class);
    }



    public static class EnderismConfig implements JsonConfigFile {
        @Override
        public String getFilePathFromConfigDirectory() {
            return ".diamonddev/enderism.json";
        }

        //

        @SerializedName("charms")
        public CharmConfig charmConfig = new CharmConfig();

        public static class CharmConfig {
            @SerializedName("charmCraftingUsesPotions")
            public boolean charmCraftsUsePotions = true;

            @SerializedName("creativeHasAllRegisteredEffectCharms")
            public boolean creativeHasAllCharms = false;
        }
    }
}
