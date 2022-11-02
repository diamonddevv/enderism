package net.diamonddev.enderism.mixin;

import net.diamonddev.enderism.init.EffectInit;
import net.diamonddev.enderism.init.EnchantInit;
import net.diamonddev.enderism.init.GameruleInit;
import net.diamonddev.libgenetics.common.api.v1.enchantment.EnchantHelper;
import net.diamonddev.libgenetics.common.api.v1.util.DirtyObject;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "tick", at = @At("TAIL"))
    private void enderism$tickOnGround(CallbackInfo ci) {
        if (this.isOnGround()) {
            this.ticksOnGround++;
        } else {
            this.ticksOnGround = 0;
        }
    }
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void enderism$resetUpthrustBoostCount(CallbackInfo ci) {
        if (this.ticksOnGround > 5) {
            dataTracker.set(boostCount, 0);
        }
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void enderism$performUpthrust(CallbackInfo ci) {
        if (this.hasStackEquipped(EquipmentSlot.CHEST)) {
            if (EnchantInit.isEnchantableElytraItem(this.getEquippedStack(EquipmentSlot.CHEST).getItem())) {
                ItemStack stack = this.getEquippedStack(EquipmentSlot.CHEST);
                if (ElytraItem.isUsable(stack)) {
                    if (EnchantHelper.hasEnchantment(EnchantInit.UPTHRUST, stack)) {
                        int level = EnchantmentHelper.getLevel(EnchantInit.UPTHRUST, stack);
                        if (this.ticksOnGround > 5) {
                            maxBoostCount.set(level * 2);
                            maxBoostCount.clean();
                        }
                        if (this.jumpingCooldown > 0 && dataTracker.get(boostCount) <= maxBoostCount.get()) {
                            maxBoostCount.clean();
                            this.jump();
                            this.jumpingCooldown = 10;
                        } else if (this.ticksOnGround < 5) {
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

    @Inject(method = "modifyAppliedDamage", at = @At("HEAD"))
    private void enderism$callChoruskirmish(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        LivingEntity user = (LivingEntity) (Object) this;
        int chance = this.world.getGameRules().getInt(GameruleInit.CHORUSKIRMISH_CHANCE);
        if (user.hasStatusEffect(EffectInit.CHORUSKIRMISH) && random.nextFloat() <= chance / 100.0f) { // Literally just the chorus fruit code lol
            double d = user.getX();
            double e = user.getY();
            double f = user.getZ();

            for (int i = 0; i < 16; ++i) {
                double g = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                double h = MathHelper.clamp(user.getY() + (double) (user.getRandom().nextInt(16) - 8), world.getBottomY(), (world.getBottomY() + ((ServerWorld) world).getLogicalHeight() - 1));
                double j = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                if (user.hasVehicle()) {
                    user.stopRiding();
                }

                Vec3d vec3d = user.getPos();
                if (user.teleport(g, h, j, true)) {
                    world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                    SoundEvent soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    world.playSound(null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    user.playSound(soundEvent, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }


}
