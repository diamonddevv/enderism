package dev.diamond.enderism.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.diamond.enderism.registry.InitResourceListener;
import dev.diamond.enderism.resource.type.MusicSheetResourceType;
import net.diamonddev.libgenetics.core.command.abstraction.StringArrayListArgType;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MusicSheetSongIdArgumentType extends StringArrayListArgType {

    public static MusicSheetSongIdArgumentType songId() {
        return new MusicSheetSongIdArgumentType();
    }

    private MusicSheetSongIdArgumentType() {
    }

    public static String getSongId(CommandContext<ServerCommandSource> context, String argumentName) throws CommandSyntaxException {
        return context.getArgument(argumentName, String.class);
    }

    @Override
    public ArrayList<String> getArray() {
        return InitResourceListener.ENDERISM_MUSIC_SHEETS.getManager()
                .getAllResources(InitResourceListener.MUSIC_TYPE).stream()
                .map(res -> MusicSheetResourceType.getAsSheet(res).id)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
