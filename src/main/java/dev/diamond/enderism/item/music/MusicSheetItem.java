package dev.diamond.enderism.item.music;

import dev.diamond.enderism.resource.type.MusicSheetResourceType;
import dev.diamond.enderism.client.EnderismClient;
import dev.diamond.enderism.nbt.EnderismNbt;
import dev.diamond.enderism.network.InitPackets;
import dev.diamond.enderism.network.SendHudContextInfoPacket;
import dev.diamond.enderism.registry.InitResourceListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MusicSheetItem extends Item {
    public MusicSheetItem(Settings settings) {
        super(settings);
    }

    public static ArrayList<ItemStack> getAllMusicSheets(List<MusicSheetBean> sheets, MusicSheetItem item) {
        ArrayList<ItemStack> list = new ArrayList<>();
        for (var sheet : sheets) {
            ItemStack stack = new ItemStack(item);
            EnderismNbt.MusicSheetSongManager.setStringifiedId(stack, sheet.id);
            list.add(stack);
        }

        return list;
    }


    public static MusicSheetWrapper getWrapper(ItemStack stack) {
        if (stack.getItem() instanceof MusicSheetItem) {

            String id = EnderismNbt.MusicSheetSongManager.getStringifiedId(stack);

            MusicSheetWrapper wrapper = null;

            for (CognitionDataResource cdr : InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager().getAllResources(InitResourceListener.MUSIC_TYPE)) {
                MusicSheetBean musicSheet = MusicSheetResourceType.getAsSheet(cdr);
                if (Objects.equals(id, musicSheet.id)) {
                    wrapper = new MusicSheetWrapper(musicSheet);
                    break;
                }
            }

            return wrapper;
        } return null;
    }

    public boolean play(ItemStack instrumentStack, MusicSheetWrapper wrapper, InstrumentWrapper instrument, World world, PlayerEntity user, float pitch, int coolTicks) {
        boolean produceError = true;

        if (wrapper.getSoundFromHash(instrument).isPresent()) {
            if (wrapper.canBePlayedWithInstrument(instrument)) {
                EnderismNbt.InstrumentFinishTimeManager.setFromLength(instrumentStack, coolTicks, world);
                world.playSound(null, user.getBlockPos(), wrapper.getSoundFromHash(instrument).get(), SoundCategory.RECORDS, 10f, pitch);
                produceError = false;
            }
        }

        if (produceError) {
            if (user.getServer() != null) {
                ServerPlayerEntity spe = user.getServer().getPlayerManager().getPlayer(user.getUuid());

                SendHudContextInfoPacket.SHCIPacketData data = new SendHudContextInfoPacket.SHCIPacketData();
                data.isTranslated = true;
                data.contextData = "context.enderism.music_sheet_missing";

                NerveNetworker.send(spe, InitPackets.SHCI, data);
            }
        }

        return !produceError;
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        EnderismClient.getAllAsT(InitResourceListener.MUSIC_TYPE, MusicSheetResourceType.REMAPPER).forEach(sheet -> {
            if (Objects.equals(EnderismNbt.MusicSheetSongManager.getStringifiedId(stack), sheet.id)) {
                String key = sheet.descTranslationKey;
                tooltip.add(Text.translatable(key).formatted(Formatting.GRAY));
            }
        });
    }
}
