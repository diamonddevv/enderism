package dev.diamond.enderism.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.diamond.enderism.command.MusicSheetSongIdArgumentType;
import dev.diamond.enderism.EnderismMod;
import dev.diamond.enderism.nbt.EnderismNbt;
import dev.diamond.enderism.network.InitPackets;
import dev.diamond.enderism.network.SendHudContextInfoPacket;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;

public class InitCommands implements RegistryInitializer {

    private static final LiteralArgumentBuilder<ServerCommandSource> SHCI_ROOT = CommandManager.literal("shci");
    private static final LiteralArgumentBuilder<ServerCommandSource> SHEET_ROOT = CommandManager.literal("musicsheet");

    @Override
    public void register() {
        ArgumentTypeRegistry.registerArgumentType(EnderismMod.id("songid_arg"),
                MusicSheetSongIdArgumentType.class, ConstantArgumentSerializer.of(MusicSheetSongIdArgumentType::songId));

        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> {
            registerShci(dispatcher);
            registerMusicSheet(dispatcher);
        });
    }

    private void registerShci(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                SHCI_ROOT.then(
                        argument("players", EntityArgumentType.players()).then(
                                argument("text", StringArgumentType.string())
                                        .executes(InitCommands::exeShci)
                        )
                )
        );
    }

    private void registerMusicSheet(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                SHEET_ROOT.then(
                        argument("players", EntityArgumentType.players()).then(
                                argument("song", MusicSheetSongIdArgumentType.songId())
                                        .executes(InitCommands::exeGiveMusicSheet)
                        )
                )
        );
    }

    private static int exeGiveMusicSheet(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> spes = EntityArgumentType.getPlayers(ctx, "players");
        String song = MusicSheetSongIdArgumentType.getSongId(ctx, "song");

        if (spes == null || spes.isEmpty()) throw EntityArgumentType.PLAYER_NOT_FOUND_EXCEPTION.create();

        ItemStack stack = new ItemStack(InitItems.MUSIC_SHEET);
        EnderismNbt.MusicSheetSongManager.setStringifiedId(stack, song);

        spes.forEach(serverPlayer -> serverPlayer.giveItemStack(stack));

        return 1;
    }

    private static int exeShci(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> spes = EntityArgumentType.getPlayers(ctx, "players");
        String text = StringArgumentType.getString(ctx, "text");

        if (spes == null || spes.isEmpty()) throw EntityArgumentType.PLAYER_NOT_FOUND_EXCEPTION.create();

        var data = new SendHudContextInfoPacket.SHCIPacketData();
        data.contextData = text;
        data.isTranslated = false;

        spes.forEach(serverPlayer -> NerveNetworker.send(serverPlayer, InitPackets.SHCI, data));

        return 1;
    }
}
