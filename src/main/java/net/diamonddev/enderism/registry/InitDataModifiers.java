package net.diamonddev.enderism.registry;

import net.diamonddev.enderism.nbt.EnderismNbt;
import net.diamonddev.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
}
