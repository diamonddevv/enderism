package net.diamonddev.enderism.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.diamonddev.enderism.network.InitPackets;
import net.diamonddev.enderism.network.SendHudContextInfoPacket;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.diamonddev.libgenetics.common.api.v1.network.nerve.NerveNetworker;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;

public class InitCommands implements RegistryInitializer {

    private static final LiteralArgumentBuilder<ServerCommandSource> SHCI_ROOT = CommandManager.literal("shci");

    @Override
    public void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> {
            registerShci(dispatcher);
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
