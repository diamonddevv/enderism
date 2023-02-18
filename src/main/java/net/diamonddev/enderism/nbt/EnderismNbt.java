package net.diamonddev.enderism.nbt;

import net.diamonddev.enderism.util.EnderismUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.UUID;

public class EnderismNbt {

    private static final NbtStatusEffectInstanceComponent CHARM_EFFECT = new NbtStatusEffectInstanceComponent("effect");
    private static final NbtBooleanComponent CHARM_HAS_EFFECT = new NbtBooleanComponent("hasEffect");

    private static final CompoundedNbtComponent CURSED_CHORUS_BINDS = new CompoundedNbtComponent("bindData");
    private static final NbtStringComponent CURSED_CHORUS_BIND_STATE = new NbtStringComponent("bindState");
    private static final NbtUuidComponent CURSED_CHORUS_UUID = new NbtUuidComponent("uuid");
    private static final NbtVec3dComponent CURSED_CHORUS_VEC = new NbtVec3dComponent("pos");

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
}
