package net.diamonddev.enderism.registry;

import com.google.gson.annotations.SerializedName;
import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFile;
import net.diamonddev.libgenetics.common.api.v1.config.chromosome.ChromosomeConfigFileRegistry;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;

import java.util.ArrayList;

public class InitConfig implements RegistryInitializer {

    public static EnderismConfig ENDERISM = new EnderismConfig();
    @Override
    public void register() {
        ENDERISM = ChromosomeConfigFileRegistry.registerAndReadAsSelf(EnderismMod.id("enderism_config"), new EnderismConfig(), EnderismConfig.class);
    }



    public static class EnderismConfig implements ChromosomeConfigFile {
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

            @SerializedName("wanderersCharmTrades")
            public WanderersCharmTradeConfig wanderersCharmTradeConfig = new WanderersCharmTradeConfig();

            public static class WanderersCharmTradeConfig {
                @SerializedName("maxPotency") public int maxPotency = 4;
                @SerializedName("maxDurationSeconds") public int maxDurSecs = 90;
                @SerializedName("disallowedEffectIds") public ArrayList<String> disallowedEffects = new ArrayList<>();
            }
        }
    }
}
