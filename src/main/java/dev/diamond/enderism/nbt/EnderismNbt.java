package dev.diamond.enderism.nbt;

import dev.diamond.enderism.util.EnderismUtil;
import net.diamonddev.libgenetics.common.api.v1.nbt.cerebellum.CerebellumCompoundedNbtComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class EnderismNbt {

    private static final NbtStatusEffectInstanceComponent CHARM_EFFECT = new NbtStatusEffectInstanceComponent("effect");
    private static final NbtBooleanComponent CHARM_HAS_EFFECT = new NbtBooleanComponent("hasEffect");

    private static final CerebellumCompoundedNbtComponent CURSED_CHORUS_BINDS = new CerebellumCompoundedNbtComponent("bindData");
    private static final NbtStringComponent CURSED_CHORUS_BIND_STATE = new NbtStringComponent("bindState");
    private static final NbtUuidComponent CURSED_CHORUS_UUID = new NbtUuidComponent("uuid");
    private static final NbtVec3dComponent CURSED_CHORUS_VEC = new NbtVec3dComponent("pos");

    private static final NbtStringComponent MUSIC_SHEET_ID = new NbtStringComponent("song_id");

    private static final NbtLongComponent FINISH_TIME = new NbtLongComponent("sheet_end_time");

    public static final NbtIntComponent MULTICLIP_ARROWS = new NbtIntComponent("multiclipArrowCount");

    public static class InstrumentFinishTimeManager {
        public static long get(ItemStack stack) {
            return FINISH_TIME.read(stack.getOrCreateNbt());
        }
        public static void set(ItemStack stack, long i) {
            FINISH_TIME.write(stack.getOrCreateNbt(), i);
        }

        public static void setFromLength(ItemStack stack, long i, World world) {
            set(stack, world.getTime() + i);
        }
    }
    public static class MusicSheetSongManager {
        public static String getStringifiedId(ItemStack stack) {
            return MUSIC_SHEET_ID.read(stack.getOrCreateNbt());
        }

        public static void setStringifiedId(ItemStack stack, String id) {
            MUSIC_SHEET_ID.write(stack.getOrCreateNbt(), id);
        }
    }
    public static class CursedChorusBindManager {
        // Set Binding
        public static void setPlayerBind(PlayerEntity player, ItemStack stack) {
            clearBinds(stack);
            CURSED_CHORUS_BINDS.modify(CURSED_CHORUS_UUID, player.getUuid(), stack.getOrCreateNbt());
            CURSED_CHORUS_BINDS.modify(CURSED_CHORUS_BIND_STATE, BindStates.PLAYER.toString(), stack.getOrCreateNbt());
        }
        public static void setMagnetiteBind(BlockPos pos, ItemStack stack) {
            clearBinds(stack);
            CURSED_CHORUS_BINDS.modify(CURSED_CHORUS_VEC, EnderismUtil.toVec(pos), stack.getOrCreateNbt());
            CURSED_CHORUS_BINDS.modify(CURSED_CHORUS_BIND_STATE, BindStates.MAGNETITE.toString(), stack.getOrCreateNbt());
        }
        public static void clearBinds(ItemStack stack) {
            // Zero-ify UUID and Vector
            CURSED_CHORUS_BINDS.modify(CURSED_CHORUS_VEC, new Vec3d(0,0,0), stack.getOrCreateNbt());
            CURSED_CHORUS_BINDS.modify(CURSED_CHORUS_UUID, EnderismUtil.UUID_ZERO, stack.getOrCreateNbt());

            // Set Bindstate to BindStates.NONE
            CURSED_CHORUS_BINDS.modify(CURSED_CHORUS_BIND_STATE, BindStates.NONE.toString(), stack.getOrCreateNbt());
        }

        // Get Binding
        public static PlayerEntity getBoundPlayer(ItemStack stack, World world) {
            return world.getPlayerByUuid(CURSED_CHORUS_BINDS.getValue(CURSED_CHORUS_UUID, stack.getOrCreateNbt()));
        }
        public static Vec3d getBoundVector(ItemStack stack) {
            return CURSED_CHORUS_BINDS.getValue(CURSED_CHORUS_VEC, stack.getOrCreateNbt());
        }

        // Check Binding
        public static boolean isPlayerbound(ItemStack stack) {
            return Objects.equals(CURSED_CHORUS_BINDS.getValue(CURSED_CHORUS_BIND_STATE, stack.getOrCreateNbt()), BindStates.PLAYER.name());
        }
        public static boolean isMagnetiteBound(ItemStack stack) {
            return Objects.equals(CURSED_CHORUS_BINDS.getValue(CURSED_CHORUS_BIND_STATE, stack.getOrCreateNbt()), BindStates.MAGNETITE.name());
        }
        public static boolean isBound(ItemStack stack) {
            return !Objects.equals(CURSED_CHORUS_BINDS.getValue(CURSED_CHORUS_BIND_STATE, stack.getOrCreateNbt()), BindStates.NONE.name());
        }

        // Bind States
        private enum BindStates {
            NONE,
            PLAYER,
            MAGNETITE
        }
    }
    public static class CharmEffectManager {
        public static void set(ItemStack stack, StatusEffectInstance effect) {
            CHARM_EFFECT.write(stack.getOrCreateNbt(), effect);
            setHas(stack, true);
        }
        public static StatusEffectInstance get(ItemStack stack) {
            return CHARM_EFFECT.read(stack.getOrCreateNbt());
        }

        public static boolean has(ItemStack stack) {
            return CHARM_HAS_EFFECT.read(stack.getOrCreateNbt());
        }

        public static void setHas(ItemStack stack, boolean bl) {
            CHARM_HAS_EFFECT.write(stack.getOrCreateNbt(), bl);
        }

    }

    public static class MulticlipProjectileManager {
        public static int getProjectiles(ItemStack stack) {
            return MULTICLIP_ARROWS.read(stack.getOrCreateNbt());
        }

        public static void setProjectiles(ItemStack stack, int count) {
            MULTICLIP_ARROWS.write(stack.getOrCreateNbt(), count);
        }

        public static void decrementProjectileCount(ItemStack stack) {
            setProjectiles(stack, getProjectiles(stack) - 1);
        }
    }
}
