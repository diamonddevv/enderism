package net.diamonddev.enderism.item.music;

import net.diamonddev.enderism.nbt.EnderismNbt;
import net.diamonddev.enderism.network.InitPackets;
import net.diamonddev.enderism.network.SendHudContextInfoPacket;
import net.diamonddev.enderism.registry.InitResourceListener;
import net.diamonddev.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.libgenetics.common.api.v1.dataloader.cognition.CognitionDataResource;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MusicSheetItem extends Item {
    public MusicSheetItem(Settings settings) {
        super(settings);
    }


    public static MusicSheetDataWrapper getWrapper(ItemStack stack) {
        if (stack.getItem() instanceof MusicSheetItem) {

            String id = EnderismNbt.MusicSheetSongManager.getStringifiedId(stack);

            MusicSheetDataWrapper wrapper = null;

            for (CognitionDataResource res : InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager().CACHE.getOrCreateKey(InitResourceListener.MUSIC_TYPE)) {
                SerializedMusicSheet musicSheet = MusicSheetResourceType.getAsSheet(res);
                if (Objects.equals(id, musicSheet.id)) {
                    wrapper = new MusicSheetDataWrapper(musicSheet);
                    break;
                }
            }

            return wrapper;
        } return null;
    }

    public void play(MusicSheetDataWrapper wrapper, MusicSheetInstrument instrument, World world, PlayerEntity user) {

        if (wrapper.getSoundFromHash(instrument).isPresent()) {
            world.playSound(null, user.getBlockPos(), wrapper.getSoundFromHash(instrument).get(), SoundCategory.RECORDS);
        } else {
            if (user.getServer() != null) {
                ServerPlayerEntity spe = user.getServer().getPlayerManager().getPlayer(user.getUuid());

                SendHudContextInfoPacket.SHCIPacketData data = new SendHudContextInfoPacket.SHCIPacketData();
                data.isTranslated = true;
                data.contextData = "context.enderism.music_sheet_missing";

                NerveNetworker.send(spe, InitPackets.SHCI, data);
            }
        }
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager().forEachResource(InitResourceListener.MUSIC_TYPE, res -> {
            SerializedMusicSheet sheet = MusicSheetResourceType.getAsSheet(res);
            if (Objects.equals(EnderismNbt.MusicSheetSongManager.getStringifiedId(stack), sheet.id)) {
                String key = sheet.descTranslationKey;
                tooltip.add(Text.translatable(key).formatted(Formatting.GRAY));
            }
        });
    }
}
