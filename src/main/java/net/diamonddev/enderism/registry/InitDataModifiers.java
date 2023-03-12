package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.nbt.EnderismNbt;
import net.diamonddev.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.enderism.util.EnderismUtil;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InitDataModifiers implements RegistryInitializer {

    @Override
    public void register() {
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add(new SellMusicSheetFactory());
            factories.add(new SellWanderersCharmFactory());
        });


    }

    private static class SellMusicSheetFactory implements TradeOffers.Factory {
        @Nullable
        @Override
        public TradeOffer create(Entity entity, Random random) {
            List<String> ids = new ArrayList<>();

            InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager().forEachResource(InitResourceListener.MUSIC_TYPE, res -> {
                ids.add(MusicSheetResourceType.getAsSheet(res).id);
            });

            ItemStack stack = new ItemStack(InitItems.MUSIC_SHEET);
            String id = ids.get(random.nextInt(ids.size()));
            EnderismNbt.MusicSheetSongManager.setStringifiedId(stack, id);

            return new TradeOffer(new ItemStack(Items.EMERALD, 16), stack, 12, 8, 2);
        }
    }

    private static class SellWanderersCharmFactory implements TradeOffers.Factory {

        @Nullable
        @Override
        public TradeOffer create(Entity entity, Random random) {

            ItemStack stack = new ItemStack(InitItems.WANDERERS_CHARM);
            StatusEffect effect = Registries.STATUS_EFFECT.get(random.nextInt(Registries.STATUS_EFFECT.size()));
            if (effect == null) effect = StatusEffects.POISON;
            EnderismNbt.CharmEffectManager.set(stack, new StatusEffectInstance(effect, 60, 0));

            return new TradeOffer(new ItemStack(Items.EMERALD, 28), stack, 12, 8, 2);
        }
    }
}
