package net.diamonddev.enderism.item.music;

import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;

public class InstrumentWrapper {
    private final InstrumentBean bean;

    public InstrumentWrapper(InstrumentBean instrument) {
        this.bean = instrument;
    }

    public boolean shouldAddToExistingInstrument() {
        return bean.addTo != null;
    }
    public Identifier getInstrumentToAddTo() {
        return new Identifier(bean.addTo);
    }

    public SoundEvent getDefaultSound() {
        return Registries.SOUND_EVENT.get(new Identifier(bean.defaultSoundId));
    }

    public boolean containsInstrumentItem(Identifier itemId, NbtCompound nbt) {
        return itemBeanListContainsMatch(instrumentIdModifierBeansToIdsAdapter(bean.instrumentItemIds), itemId, nbt);
    }

    public String getIdentifier() {
        return bean.nonSerializedIdentifier;
    }

    public float getPitchByItem(Identifier itemId, NbtCompound nbt) {
        for (var bean : bean.instrumentItemIds) {
            if (bean.item.stringifiedId.equals(itemId.toString())
                    && (bean.item.nbtString == null || NbtHelper.matches(readNbt(bean.item.nbtString), nbt, true))) return bean.pitch;
        }
        return 1f;
    }

    private static ArrayList<InstrumentBean.ItemBean> instrumentIdModifierBeansToIdsAdapter(ArrayList<InstrumentBean.InstrumentItemModifierBean> beans) {
        ArrayList<InstrumentBean.ItemBean> items = new ArrayList<>();
        for (var bean : beans) items.add(bean.item);
        return items;
    }

    private static boolean itemBeanListContainsMatch(ArrayList<InstrumentBean.ItemBean> list, Identifier id, NbtCompound nbt) {
        for (var item : list) {
            if (item.stringifiedId.equals(id.toString())) {
                if (item.nbtString != null) {
                    return NbtHelper.matches(readNbt(item.nbtString), nbt, true);
                } else return true;
            }
        } return false;
    }

    private static NbtCompound readNbt(String snbt) { // i defo did not steal this from Calio (https://github.com/apace100/calio/blob/1.20/src/main/java/io/github/apace100/calio/data/SerializableDataTypes.java#L350)
        try {
            return new StringNbtReader(new StringReader(snbt)).parseCompound();
        }
        catch (CommandSyntaxException e) {
            throw new JsonSyntaxException("Could not parse NBT: " + e.getMessage());
        }
    }
}
