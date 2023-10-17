package dev.diamond.enderism.item.music;

import dev.diamond.enderism.registry.InitAdvancementCriteria;
import dev.diamond.enderism.resource.EnderismMusicalInstrumentListener;
import dev.diamond.enderism.resource.type.MusicInstrumentResourceType;
import dev.diamond.enderism.resource.type.MusicSheetResourceType;
import dev.diamond.enderism.client.EnderismClient;
import dev.diamond.enderism.registry.InitResourceListener;
import dev.diamond.enderism.util.EnderismUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InstrumentItem extends Item {
    public InstrumentItem(Settings settings) {
        super(settings);
    }


    private static final String CAN_PLAY_KEY = "tooltip.enderism.instrument.canplay";
    private static final String INSTRUMENT_KEY = "tooltip.enderism.instrument.instrument";
    private static final String PITCHSHIFT_KEY = "tooltip.enderism.instrument.pitchshift";

    @Nullable private InstrumentWrapper instrumentWrapper = null;
    @Nullable private InstrumentWrapper instrumentWrapperClient = null;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        ItemStack other = user.getStackInHand(EnderismUtil.otherHand(hand));

        if (!world.isClient) {

            if (stackInHand.getItem() instanceof InstrumentItem ii) {
                if (ii.getInstrument(stackInHand) != null) {

                    float pitch = ii.getInstrument(stackInHand).getPitchByItem(Registries.ITEM.getId(stackInHand.getItem()), stackInHand.getOrCreateNbt());

                    if (other.getItem() instanceof MusicSheetItem sheet) {
                        MusicSheetWrapper wrapper = MusicSheetItem.getWrapper(other);
                        if (wrapper != null) {
                            int coolTicks = Math.round(calculateNewLengthFromOldLengthAndPitch(wrapper.getCooldownTicks(), pitch));

                            boolean played = sheet.play(stackInHand, wrapper, getInstrument(stackInHand), world, user, pitch, coolTicks);
                            if (played) {
                                createVibration(user);

                                if (user instanceof ServerPlayerEntity spe) {
                                    InitAdvancementCriteria.USE_INSTRUMENT.trigger(spe, stackInHand);
                                }

                                if (!user.isCreative()) setCooldownForAllInstruments(user, coolTicks);
                                return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
                            } else {
                                return new TypedActionResult<>(ActionResult.PASS, stackInHand);
                            }
                        }

                        return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
                    } else {
                        user.playSound(getDefaultSoundEvent(stackInHand), SoundCategory.RECORDS, 10f, pitch);
                        createVibration(user);
                    }
                }
            }


        }
        return new TypedActionResult<>(ActionResult.PASS, stackInHand);
    }

    private static void setCooldownForAllInstruments(PlayerEntity player, int ticks) {
        for (Item item : Registries.ITEM) {
            if (item instanceof InstrumentItem instrument) {
                player.getItemCooldownManager().set(instrument, ticks);
            }
        }
    }

    @Nullable
    public SoundEvent getDefaultSoundEvent(ItemStack stack) {
        var v =  this.getInstrument(stack);
        if (v != null) return v.getDefaultSound();
        else return null;
    }

    @Nullable
    public InstrumentWrapper getInstrument(ItemStack stack) {
        if (instrumentWrapper == null) {
            Identifier thisId = Registries.ITEM.getId(this);

            List<InstrumentWrapper> wrappers =
                    new ArrayList<>(InitResourceListener.ENDERISM_INSTRUMENTS.getManager().getAllResources(InitResourceListener.INSTRUMENT_TYPE).stream().map(MusicInstrumentResourceType::wrap).toList());

            wrappers.removeIf((wrapper) -> !wrapper.containsInstrumentItem(thisId, stack.getOrCreateNbt()));

            try {
                return wrappers.get(0); // just get the first
            } catch (IndexOutOfBoundsException ignored) {} // if this is caught, then there isnt an instrument so we do nothing
        }
        return instrumentWrapper;
    }


    @Nullable
    public InstrumentWrapper getInstrumentClient(ItemStack stack) {
        if (instrumentWrapperClient == null) {
            Identifier thisId = Registries.ITEM.getId(this);

            List<InstrumentWrapper> wrappers = EnderismClient.getAllAsT(InitResourceListener.INSTRUMENT_TYPE, EnderismMusicalInstrumentListener.REMAPPER);

            wrappers.removeIf((wrapper) -> !wrapper.containsInstrumentItem(thisId, stack.getOrCreateNbt()));

            try {
                return wrappers.get(0); // just get the first
            } catch (IndexOutOfBoundsException ignored) {} // if this is caught, then there isnt an instrument so we do nothing
        }
        return instrumentWrapperClient;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.getInstrumentClient(stack) != null) {
            float pitch = this.getInstrumentClient(stack).getPitchByItem(Registries.ITEM.getId(stack.getItem()), stack.getOrCreateNbt());

            tooltip.add(EnderismUtil.compoundText(Text.translatable(INSTRUMENT_KEY).formatted(Formatting.AQUA), generateInstrumentText(getInstrumentClient(stack)).formatted(Formatting.GRAY)));
            tooltip.add(EnderismUtil.compoundText(Text.translatable(PITCHSHIFT_KEY).formatted(Formatting.AQUA), getPercentageChangeText(pitch)));

            tooltip.add(Text.empty()); // blank line

            tooltip.add(Text.translatable(CAN_PLAY_KEY).formatted(Formatting.AQUA));

            EnderismClient.getAllAsT(InitResourceListener.MUSIC_TYPE, MusicSheetResourceType.REMAPPER).forEach(bean -> {
                MusicSheetWrapper sheet = new MusicSheetWrapper(bean);

                if (sheet.canBePlayedWithInstrument(getInstrumentClient(stack))) {
                    String key = sheet.bean.descTranslationKey;
                    tooltip.add(EnderismUtil.compoundText(Text.literal(" "), Text.translatable(key).formatted(Formatting.GRAY)));
                }
            });
        }
    }

    private static float calculateNewLengthFromOldLengthAndPitch(float oldLength, float pitch) {
        // this function is from https://github.com/audacity/audacity/blob/aa59545651dd7b88c87b6bb87a38ad1332c1361a/src/effects/ChangeTempo.cpp#L140
        // i took it from audacity
        // i couldnt figure out how it was calculated lol so i looked at the source
        // thank god audacity is open source

        float percentageChange = EnderismUtil.Math.decimalToPercentageChange(pitch);

        return (oldLength * 100.0f) / (100.0f + percentageChange);
    }

    private static MutableText getPercentageChangeText(float pitch) {
        float percent = EnderismUtil.Math.decimalToPercentageChange(pitch);
        boolean over0 = percent > 0;


        String stringifiedPercent = percent-Math.floor(percent) == 0 ? "" + (int)percent : "" + percent;

        return Text.literal((over0 ? "+" : "") + stringifiedPercent + "%").formatted(Formatting.GRAY);
    }

    private MutableText generateInstrumentText(InstrumentWrapper instrumentWrapper) {
        return Text.translatable("name.instrument.enderism." + instrumentWrapper.getIdentifier());
    }

    private static void createVibration(LivingEntity user) {
        user.getWorld().emitGameEvent(user, GameEvent.INSTRUMENT_PLAY, user.getPos()); // this will ensure we get some nice vibrations for the warden and stuff yk
    }
}
