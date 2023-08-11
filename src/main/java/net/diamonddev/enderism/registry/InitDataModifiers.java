package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.client.EnderismClient;
import net.diamonddev.enderism.item.CharmItem;
import net.diamonddev.enderism.item.music.MusicSheetBean;
import net.diamonddev.enderism.item.music.MusicSheetWrapper;
import net.diamonddev.enderism.modifier.LootTableModifier;
import net.diamonddev.enderism.nbt.EnderismNbt;
import net.diamonddev.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionRegistry;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionResourceManager;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InitDataModifiers implements RegistryInitializer {

    @Override
    public void register() {
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add(new SellMusicSheetFactory());
            factories.add(new SellWanderersCharmFactory());

            factories.add(new EnderismSellItemFactory(10, 20, InitItems.PURPUR_FLUTE, 1));
            factories.add(new EnderismSellItemFactory(15, 30, InitItems.CHORUS_CELLO, 1));
            factories.add(new EnderismSellItemFactory(15, 30, InitItems.VIBRATOTAMATONE, 1));
        });

        new LootTableModifier().register(); // Loot Tables
    }

    private static class SellMusicSheetFactory implements TradeOffers.Factory {
        @Nullable
        @Override
        public TradeOffer create(Entity entity, RandomGenerator random) {
            List<String> ids = new ArrayList<>();
            InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager().forEachResource(InitResourceListener.MUSIC_TYPE, cdr -> {
                ids.add(MusicSheetResourceType.getAsSheet(cdr).id);
            });

            ItemStack stack = new ItemStack(InitItems.MUSIC_SHEET);
            String id = ids.get(random.nextInt(ids.size()));
            EnderismNbt.MusicSheetSongManager.setStringifiedId(stack, id);

            return new TradeOffer(new ItemStack(Items.EMERALD, 16), stack, 12, 8, 2);
        }
    }

    private static class EnderismSellItemFactory implements TradeOffers.Factory {

        private final int emMn;
        private final int emMx;
        private final ItemConvertible item;
        private final int uses;

        public EnderismSellItemFactory(int emeraldsMin, int emeraldsMax, ItemConvertible sells, int uses) {
            this.emMn = emeraldsMin;
            this.emMx = emeraldsMax;
            this.item = sells;
            this.uses = uses;
        }

        @Nullable
        @Override
        public TradeOffer create(Entity entity, RandomGenerator random) {
            return new TradeOffer(new ItemStack(Items.EMERALD, random.range(emMn, emMx)), new ItemStack(item, 1), uses, 8, 2);
        }
    }
    private static class SellWanderersCharmFactory implements TradeOffers.Factory {

        @Nullable
        @Override
        public TradeOffer create(Entity entity, RandomGenerator random) {

            ItemStack stack = createConfiguredRandomisedCharm(InitItems.WANDERERS_CHARM, random, InitConfig.ENDERISM);

            return new TradeOffer(new ItemStack(Items.EMERALD, random.range(30, 60)), stack, 3, 8, 2);
        }

        public static ItemStack createConfiguredRandomisedCharm(CharmItem instance, RandomGenerator random, InitConfig.EnderismConfig config) {

            List<Identifier> mappedList = config.charmConfig.wanderersCharmTradeConfig.disallowedEffects.stream().map((Identifier::new)).toList();
            Predicate<StatusEffect> predicate = effect -> {
              if (config.charmConfig.wanderersCharmTradeConfig.whitelist) {
                  return !mappedList.contains(Registries.STATUS_EFFECT.getId(effect));
              } else return mappedList.contains(Registries.STATUS_EFFECT.getId(effect));
            };

            StatusEffect effect = null;
            while (effect == null || predicate.test(effect)) {
                effect = Registries.STATUS_EFFECT.getOrThrow(random.nextInt(Registries.STATUS_EFFECT.size()-1) + 1);
            }

            int duration = 20 * random.nextInt(config.charmConfig.wanderersCharmTradeConfig.maxDurSecs);
            int amplifier = random.range(0, config.charmConfig.wanderersCharmTradeConfig.maxPotency);

            if (effect.isInstant()) duration = 0;

            StatusEffectInstance sei = new StatusEffectInstance(effect, duration, amplifier);
            return CharmItem.createCharm(sei, instance);
        }
    }
}
