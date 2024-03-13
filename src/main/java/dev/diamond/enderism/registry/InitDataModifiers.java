package dev.diamond.enderism.registry;

import dev.diamond.enderism.item.CharmItem;
import dev.diamond.enderism.modifier.LootTableModifier;
import dev.diamond.enderism.nbt.EnderismNbt;
import dev.diamond.enderism.resource.type.MusicSheetResourceType;
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
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InitDataModifiers implements RegistryInitializer {

    @Override
    public void register() {
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add(new SellMusicSheetFactory());

            factories.add(new SellWanderersCharmFactory());
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
        public TradeOffer create(Entity entity, Random random) {
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
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(Items.EMERALD, random.nextBetween(emMn, emMx)), new ItemStack(item, 1), uses, 8, 2);
        }
    }
    private static class SellWanderersCharmFactory implements TradeOffers.Factory {

        @Nullable
        @Override
        public TradeOffer create(Entity entity, Random random) {

            ItemStack stack = createConfiguredRandomisedCharm(InitItems.WANDERERS_CHARM, random, InitConfig.ENDERISM.charmConfig.wanderersCharmTradeConfig);

            int cost = random.nextBetween(30, 60);
            if (!InitConfig.ENDERISM.charmConfig.wanderersCharmTradeConfig.overrides.stream().filter(o -> {
                StatusEffectInstance sei = EnderismNbt.CharmEffectManager.get(stack);
                return Objects.equals(o.id, Registries.STATUS_EFFECT.getId(sei.getEffectType()).toString());
            }).toList().isEmpty()) {
                int minCost = InitConfig.ENDERISM.charmConfig.wanderersCharmTradeConfig.overrides.stream().filter(o -> {
                    StatusEffectInstance sei = EnderismNbt.CharmEffectManager.get(stack);
                    return Objects.equals(o.id, Registries.STATUS_EFFECT.getId(sei.getEffectType()).toString());
                }).toList().get(0).minCost;

                cost = random.nextBetween(minCost, 64);
            }

            return new TradeOffer(new ItemStack(Items.EMERALD, cost), stack, 5, 8, 2);
        }

        public static ItemStack createConfiguredRandomisedCharm(CharmItem instance, Random random, InitConfig.EnderismConfig.CharmConfig.WanderersCharmTradeConfig config) {

            /*
             * - Create list of all effects
             * - Filter out whitelist/blacklist
             * - Create Weighted List for effects
             *
             * - - Iterate over each effect in list
             * - - If has entry in overrides,
             * - - - Add to weighted list with overrided weight
             * - - else
             * - - - Add to weighted list with weight of 50
             *
             * - Pick random weighted effect
             * - Randomly pick duration and amplifier from config
             * - return
             */

            List<StatusEffect> effects = Registries.STATUS_EFFECT.stream().toList();


            boolean isWhitelist = config.whitelist; // Whitelist is ONLY these ; Blacklist is everything BUT these
            if (isWhitelist) {
                effects = effects.stream().filter(s -> { // predicate returns true if matched
                    Identifier id = Registries.STATUS_EFFECT.getId(s);
                    if (id != null) { // should always be true
                        String sId = id.toString();
                        return config.disallowedEffects.contains(sId);
                    } else return false;
                }).toList();
            } else {
                effects = effects.stream().filter(s -> {
                    Identifier id = Registries.STATUS_EFFECT.getId(s);
                    if (id != null) { // should always be true
                        String sId = id.toString();
                        return !config.disallowedEffects.contains(sId);
                    } else return true;
                }).toList();
            }


            WeightedList<StatusEffect> weightedList = new WeightedList<>(); // minecrafts implementation is enough for this
            for (StatusEffect effect : effects) {
                if (config.overrides.stream().anyMatch(o -> Objects.equals(o.id, Registries.STATUS_EFFECT.getId(effect).toString()))) {
                    weightedList.add(effect,
                            config.overrides.stream().filter(o -> Objects.equals(o.id, Registries.STATUS_EFFECT.getId(effect).toString())).toList().get(0).weight
                    );
                } else {
                    weightedList.add(effect, 50); // default weight
                }
            }

            weightedList.shuffle(); // randomies
            StatusEffect effect = weightedList.iterator().next(); // get first

            int amplifier;
            int duration;

            if (config.overrides.stream().anyMatch(o -> Objects.equals(o.id, Registries.STATUS_EFFECT.getId(effect).toString()))) {
                InitConfig.EnderismConfig.EffectOverride override = config.overrides.stream().filter(o -> Objects.equals(o.id, Registries.STATUS_EFFECT.getId(effect).toString())).toList().get(0);
                amplifier = random.nextInt(override.maxPotency);
                duration = random.nextInt(override.maxDurSecs);
            } else {
                amplifier = random.nextInt(config.maxPotency);
                duration = random.nextInt(config.maxDurSecs);
            }

            StatusEffectInstance sei = new StatusEffectInstance(effect, duration * 20, amplifier);
            return CharmItem.createCharm(sei, instance);
        }
    }
}
