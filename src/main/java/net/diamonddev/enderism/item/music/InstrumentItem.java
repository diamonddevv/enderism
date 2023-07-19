package net.diamonddev.enderism.item.music;

import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.enderism.resource.type.MusicInstrumentResourceType;
import net.diamonddev.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.enderism.util.EnderismUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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

    @Nullable
    private InstrumentWrapper instrumentWrapper = null;


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stackInHand = user.getStackInHand(hand);
        ItemStack other = user.getStackInHand(EnderismUtil.otherHand(hand));

        if (stackInHand.getItem() instanceof InstrumentItem ii) {
            if (ii.getInstrument() != null) {

                if (other.getItem() instanceof MusicSheetItem sheet) {
                    MusicSheetWrapper wrapper = MusicSheetItem.getWrapper(other);
                    if (wrapper != null) {
                        boolean played = sheet.play(stackInHand, wrapper, getInstrument(), world, user);
                        if (played) {
                            world.emitGameEvent(user, GameEvent.INSTRUMENT_PLAY, user.getPos()); // this will ensure we get some nice vibrations
                            if (!user.isCreative()) setCooldownForAllInstruments(user, wrapper.getCooldownTicks());
                            return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
                        } else {
                            return new TypedActionResult<>(ActionResult.PASS, stackInHand);
                        }
                    }

                    return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
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
    public SoundEvent getDefaultSoundEvent() {
        var v =  this.getInstrument();
        if (v != null) return v.getDefaultSound();
        else return null;
    }

    @Nullable
    public InstrumentWrapper getInstrument() {
        if (instrumentWrapper == null) {
            ArrayList<InstrumentWrapper> wrappers = new ArrayList<>();
            Identifier thisId = Registries.ITEM.getId(this);

            InitResourceListener.ENDERISM_INSTRUMENTS.getManager().forEachResource(InitResourceListener.INSTRUMENT_TYPE, (resource) -> {
                wrappers.add(MusicInstrumentResourceType.wrap(resource));
            });
            wrappers.removeIf((wrapper) -> !wrapper.containsInstrumentItem(thisId));

            try {
                return wrappers.get(0); // just get the first
            } catch (IndexOutOfBoundsException ignored) {} // if this is caught, then there isnt an instrument so we do nothing
        }
        return instrumentWrapper;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.getInstrument() != null) {
            tooltip.add(Text.translatable(CAN_PLAY_KEY).formatted(Formatting.WHITE));
            InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager().forEachResource(InitResourceListener.MUSIC_TYPE, res -> {
                MusicSheetWrapper sheet = new MusicSheetWrapper(MusicSheetResourceType.getAsSheet(res));
                if (sheet.canBePlayedWithInstrument(getInstrument())) {
                    String key = sheet.bean.descTranslationKey;
                    tooltip.add(Text.translatable(key).formatted(Formatting.GRAY));
                }
            });
        }
    }
}
