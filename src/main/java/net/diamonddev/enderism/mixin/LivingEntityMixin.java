package net.diamonddev.enderism.mixin;


import net.diamonddev.enderism.enchantment.target.ElytraEnchantTarget;
import net.diamonddev.enderism.init.EffectInit;
import net.diamonddev.enderism.init.EnchantInit;
import net.diamonddev.enderism.util.DirtyObject;
import net.diamonddev.enderism.util.EnderismEnchantHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    public abstract Iterable<ItemStack> getArmorItems();

    @Shadow
    public abstract boolean hasStackEquipped(EquipmentSlot slot);

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    private int jumpingCooldown;

    @Shadow
    protected abstract void jump();

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);



    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }



    @Unique private static TrackedData<Integer> boostCount = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Unique private DirtyObject<Integer> maxBoostCount = new DirtyObject<>(0);

    @Unique private int ticksOnGround = 0;



    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void enderism$initializeDataTrackers(CallbackInfo ci) {
        dataTracker.startTracking(boostCount, 0);
    }
    @Inject(method = "jump", at = @At("HEAD"))
    private void enderism$increaseUpthrustBoostCount(CallbackInfo ci) {
        dataTracker.set(boostCount, dataTracker.get(boostCount) + 1);
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void enderism$tickOnGround(CallbackInfo ci) {
        if (this.isOnGround()) {
            this.ticksOnGround++;
        } else {
            this.ticksOnGround = 0;
        }

        if (this.isSneaking()) {
            System.out.println(this.maxBoostCount.get() + " - " + this.dataTracker.get(boostCount));
        }
    }
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void enderism$resetUpthrustBoostCount(CallbackInfo ci) {
        if (this.ticksOnGround >= 5) {
            dataTracker.set(boostCount, 0);
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void enderism$performUpthrust(CallbackInfo ci) {
        if (this.hasStackEquipped(EquipmentSlot.CHEST)) {
            if (ElytraEnchantTarget.isEnchantableElytraItem(this.getEquippedStack(EquipmentSlot.CHEST).getItem())) {
                ItemStack stack = this.getEquippedStack(EquipmentSlot.CHEST);
                if (ElytraItem.isUsable(stack)) {
                    if (EnderismEnchantHelper.hasEnchantment(EnchantInit.UPTHRUST, stack)) {
                        int level = EnchantmentHelper.getLevel(EnchantInit.UPTHRUST, stack);
                        if (this.ticksOnGround > 5) {
                            maxBoostCount.set(level * 2);
                        }
                        if (this.jumpingCooldown > 0 && dataTracker.get(boostCount) <= maxBoostCount.get()) {
                            maxBoostCount.clean();
                            this.jump();
                            this.jumpingCooldown = 10;
                        } else {
                            maxBoostCount.set(maxBoostCount.get() - 1);
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "tickInVoid", at = @At("HEAD"))
    private void enderism$tickVoidRecall(CallbackInfo ci) {
        if (this.hasStatusEffect(EffectInit.VOID_RECALL)) {
            if (this.getEntityWorld().getDimensionKey() == DimensionTypes.THE_END) {
                try {
                    if (Objects.requireNonNull(world.getServer()).getWorld(World.END) != null) {
                        ServerWorld.createEndSpawnPlatform(Objects.requireNonNull(world.getServer().getWorld(World.END)));
                        BlockPos b = ServerWorld.END_SPAWN_POS;
                        this.fallDistance = 0;
                        this.teleport(b.getX(), b.getY(), b.getZ());
                        this.getWorld().playSound(null, b, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 2, 1);
                        this.refreshPositionAfterTeleport(b.getX(), b.getY(), b.getZ());
                    }
                } catch (NullPointerException ignored) {}
            }
        }
    }

}
