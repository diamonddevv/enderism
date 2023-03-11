package net.diamonddev.enderism.item.music;

import net.diamonddev.enderism.util.EnderismUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class InstrumentItem extends Item {
    public InstrumentItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stackInHand = user.getStackInHand(hand);
        ItemStack other = user.getStackInHand(EnderismUtil.otherHand(hand));

        if (other.getItem() instanceof MusicSheetItem sheet) {
            MusicSheetDataWrapper wrapper = MusicSheetItem.getWrapper(other);
            if (wrapper != null) {
                sheet.play(wrapper, getInstrument(), world, user);
                if (!user.isCreative()) setCooldownForAllInstruments(user, wrapper.getCooldownTicks());
                return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
            }
        }

        world.playSoundFromEntity(null, user, this.getDefaultSoundEvent(), SoundCategory.NEUTRAL, 1.0f, 1.0f);

        return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
    }

    private static void setCooldownForAllInstruments(PlayerEntity player, int ticks) {
        for (Item item : Registries.ITEM) {
            if (item instanceof InstrumentItem instrument) {
                player.getItemCooldownManager().set(instrument, ticks);
            }
        }
    }
    public SoundEvent getDefaultSoundEvent() {
        return this.getInstrument().sound();
    }

    public abstract MusicSheetInstrument getInstrument();
}
