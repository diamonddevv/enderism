package net.diamonddev.enderism.item.music;

import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResource;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.world.World;

import java.util.Objects;

public class MusicSheetItem extends Item {

    public MusicSheetItem(Settings settings) {
        super(settings);
    }

    public static MusicSheetDataWrapper getWrapper(ItemStack stack) {
        if (stack.getItem() instanceof MusicSheetItem) {
            String name = stack.getName().getString();

            MusicSheetDataWrapper wrapper = null;

            for (DataLoaderResource res : InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager().CACHE.getOrCreateKey(InitResourceListener.MUSIC_TYPE)) {
                SerializedMusicSheet musicSheet = CharmRecipeResourceType.getAsNotes(res);
                if (Objects.equals(name, musicSheet.name)) {
                    wrapper = new MusicSheetDataWrapper(musicSheet);
                    break;
                }
            }

            return wrapper;
        } return null;
    }

    public void play(MusicSheetDataWrapper wrapper, MusicSheetInstrument instrument, World world, PlayerEntity user) {
        if (world.isClient) {
            if (wrapper.getSoundFromHash(instrument).isPresent()) {

            } else {

            }
        }
    }

}
