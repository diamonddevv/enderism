package net.diamonddev.enderism.item.music;

import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.enderism.resource.type.CharmRecipeResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResource;
import net.diamonddev.libgenetics.common.api.v1.dataloader.DataLoaderResourceManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.IntUnaryOperator;

public class MusicSheetItem extends Item {

    public MusicSheetItem(Settings settings) {
        super(settings);
    }

    public static NoteWrapper getWrapper(ItemStack stack) {
        if (stack.getItem() instanceof MusicSheetItem) {
            String name = stack.getName().getString();

            NoteWrapper wrapper = null;

            for (DataLoaderResource res : InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager().CACHE.getOrCreateKey(InitResourceListener.MUSIC_TYPE)) {
                SerializedNotes notes = CharmRecipeResourceType.getAsNotes(res);
                if (Objects.equals(name, notes.id)) {
                    wrapper = new NoteWrapper(notes);
                    break;
                }
            }

            return wrapper;
        } return null;
    }

    public void play(NoteWrapper wrapper, MusicSheetInstrument instrument, World world, PlayerEntity user) {
        if (world.isClient) {
            // todo
        }
    }

}
