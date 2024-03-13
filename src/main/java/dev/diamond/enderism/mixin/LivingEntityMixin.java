package dev.diamond.enderism.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.diamond.enderism.advancement.UseCharmAdvancementCriterion;
import dev.diamond.enderism.cca.EnderismCCA;
import dev.diamond.enderism.effect.ChargedEffect;
import dev.diamond.enderism.item.CharmItem;
import dev.diamond.enderism.item.ShulkerShellmetItem;
import dev.diamond.enderism.item.music.InstrumentItem;
import dev.diamond.enderism.nbt.EnderismNbt;
import dev.diamond.enderism.registry.InitAdvancementCriteria;
import dev.diamond.enderism.util.EnderismUtil;
import dev.diamond.enderism.registry.InitEffects;
import dev.diamond.enderism.registry.InitEnchants;
import dev.diamond.enderism.registry.InitGamerules;
import net.diamonddev.libgenetics.common.api.v1.util.helper.EnchantHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow public abstract ItemStack getMainHandStack();

    @Shadow public abstract ItemStack getOffHandStack();

    @Shadow public abstract void setNoDrag(boolean noDrag);

    @Shadow public abstract boolean isFallFlying();

    @Shadow private BlockPos lastBlockPos;

    @Shadow public abstract int getRoll();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @ModifyArg(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;",
                    ordinal = 3
            ),
            index = 1
    )
    private double enderism$decreaseSpeedLostWithUpthrust(double y) {
        if (this.hasStackEquipped(EquipmentSlot.CHEST)) {
            if (InitEnchants.isEnchantableElytraItem(this.getEquippedStack(EquipmentSlot.CHEST).getItem())) {
                ItemStack stack = this.getEquippedStack(EquipmentSlot.CHEST);
                if (ElytraItem.isUsable(stack)) {
                    if (EnchantHelper.hasEnchantment(InitEnchants.UPTHRUST, stack)) {
                        int level = EnchantmentHelper.getLevel(InitEnchants.UPTHRUST, stack);

                        return y * ((level * 0.085) + 1);
                    }
                }
            }
        }
        return y;
    }

    @Inject(method = "tickInVoid", at = @At("HEAD"))
    private void enderism$tickVoidRecall(CallbackInfo ci) {
        if (this.hasStatusEffect(InitEffects.VOID_RECALL)) {
            if (this.getWorld().getDimensionKey() == DimensionTypes.THE_END) {
                try {
                    if (Objects.requireNonNull(getWorld().getServer()).getWorld(World.END) != null) {
                        ServerWorld.createEndSpawnPlatform(Objects.requireNonNull(getWorld().getServer().getWorld(World.END)));
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

    @Inject(method = "damage", at = @At("HEAD"))
    private void enderism$choruskirmish(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        LivingEntity user = (LivingEntity) (Object) this;
        int chance = this.getWorld().getGameRules().getInt(InitGamerules.CHORUSKIRMISH_CHANCE);
        if (user.hasStatusEffect(InitEffects.CHORUSKIRMISH) && random.nextFloat() <= chance / 100.0f) {
            int i = user.getStatusEffect(InitEffects.CHORUSKIRMISH).getAmplifier();
            EnderismUtil.chorusTeleport(user, 16, 16 * i);
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void enderism$applyCharmEffect(DamageSource dmgsource, float amount, CallbackInfoReturnable<Float> cir) {
        if (dmgsource.getSource() == null) return;

        if (dmgsource.getSource() instanceof LivingEntity || dmgsource.getSource() instanceof ProjectileEntity) {
            LivingEntity source = dmgsource.getSource() instanceof ProjectileEntity proj ? (LivingEntity) proj.getOwner() : (LivingEntity) dmgsource.getSource();


            if (source.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CharmItem || source.getStackInHand(Hand.OFF_HAND).getItem() instanceof CharmItem) {
                if (source != null) {
                    boolean b = true;
                    if (source instanceof PlayerEntity player) b = CharmItem.canUseAnyCharm(player);
                    if (b) {
                        LivingEntity thisLivEn = (LivingEntity) (Object) this;
                        applyCharmEffectForStackIfCan(source.getStackInHand(Hand.MAIN_HAND), thisLivEn, source);
                        applyCharmEffectForStackIfCan(source.getStackInHand(Hand.OFF_HAND), thisLivEn, source);

                        if (source instanceof ServerPlayerEntity spe) {
                            InitAdvancementCriteria.USE_CHARM.trigger(spe, source.getStackInHand(Hand.MAIN_HAND),
                                    UseCharmAdvancementCriterion.buildContextJson(false));
                            InitAdvancementCriteria.USE_CHARM.trigger(spe, source.getStackInHand(Hand.OFF_HAND),
                                    UseCharmAdvancementCriterion.buildContextJson(false));
                        }
                    }
                }
            }
        }
    }

    private static void applyCharmEffectForStackIfCan(ItemStack stack, LivingEntity target, LivingEntity user) {
        if (stack.getItem() instanceof CharmItem) {
            CharmItem.damageStack(stack, user);

            if (user instanceof PlayerEntity player) {
                CharmItem.setCooldownForAllCharms(player, 20 * 15);
            }

            if (EnderismNbt.CharmEffectManager.has(stack)) {
                CharmItem.applyEffect(stack, target, user);
            }
        }
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private boolean enderism$redirectLevitationCheckWithShellmet(LivingEntity instance, StatusEffect effect, Operation<Boolean> original) {
        if (instance.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ShulkerShellmetItem) {
            if (!instance.isSneaking()) {
                return false;
            }
        }
        return instance.hasStatusEffect(effect);
    }

    @WrapOperation(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target ="Lnet/minecraft/util/math/Vec3d;multiply(DDD)Lnet/minecraft/util/math/Vec3d;",
                    ordinal = 2
            )
    )
    private Vec3d enderism$aerodynamicSpeed(Vec3d instance, double x, double y, double z, Operation<Vec3d> original) {
        if (this.hasStackEquipped(EquipmentSlot.CHEST)) {
            if (InitEnchants.isEnchantableElytraItem(this.getEquippedStack(EquipmentSlot.CHEST).getItem())) {
                ItemStack stack = this.getEquippedStack(EquipmentSlot.CHEST);
                if (EnchantHelper.hasEnchantment(InitEnchants.AERODYNAMIC, stack)) {
                    if (ElytraItem.isUsable(stack)) {
                        if (this.isFallFlying()) {
                            int level = EnchantHelper.getEnchantmentLevel(stack, InitEnchants.AERODYNAMIC);
                            double calc = (level * 0.00085) + 1;
                            return original.call(instance, x, y, z).multiply(calc, 1, calc);
                        }
                    }
                }
            }
        }
        return original.call(instance, x, y, z);
    }


    @Inject(
            method = "tick",
            at = @At(
                    value = "TAIL"
            )
    )
    private void enderism$aerodynamicDivebomb(CallbackInfo ci) {
        if (this.hasStackEquipped(EquipmentSlot.CHEST)) {
            if (InitEnchants.isEnchantableElytraItem(this.getEquippedStack(EquipmentSlot.CHEST).getItem())) {
                ItemStack stack = this.getEquippedStack(EquipmentSlot.CHEST);
                if (EnchantHelper.hasEnchantment(InitEnchants.AERODYNAMIC, stack)) {
                    if (ElytraItem.isUsable(stack)) {
                        if (this.isFallFlying()) {
                            if (this.getPitch() < 0) {
                                if (this.isOnGround()) {
                                    int level = EnchantHelper.getEnchantmentLevel(stack, InitEnchants.AERODYNAMIC);
                                    float calc = (level * 5) + 1;
                                    getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), calc, World.ExplosionSourceType.NONE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void enderism$addNoteParticlesWhenInstrumenting(CallbackInfo ci) {
        ItemStack stack = null;
        if (getMainHandStack().getItem() instanceof InstrumentItem) {
            stack = getMainHandStack();
        } else if (getOffHandStack().getItem() instanceof InstrumentItem) {
            stack = getOffHandStack();
        }

        if (stack != null) {
            if (EnderismNbt.InstrumentFinishTimeManager.get(stack) > 0 && EnderismNbt.InstrumentFinishTimeManager.get(stack) > getWorld().getTime()) {
                if (EnderismNbt.InstrumentFinishTimeManager.get(stack) < getWorld().getTime()) {
                    EnderismNbt.InstrumentFinishTimeManager.set(stack, -1);
                }

                if (this.age % 20 == 0) {
                    double i = Math.sin(this.age) * 24; // screw psuedorandomness via new Random(), all my homies prefer psuedorandomness via Math.sin() with an ever-changing number
                    getWorld().addParticle(ParticleTypes.NOTE, this.getX(), this.getY() + 2.2, this.getZ(), i / 24.0, 0.0, 0.0);
                }
            }
        }
    }


    @Inject(at = @At("HEAD"), method = "modifyAppliedDamage")
    private void enderism$retributionalDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (source.getSource() instanceof LivingEntity target) {
            storeRetributionDmgIfPossible(target, amount);
        }

        if (source.getSource() instanceof ProjectileEntity projectileEntity) {
            if (projectileEntity.getOwner() instanceof LivingEntity target) {
                storeRetributionDmgIfPossible(target, amount);
            }
        }
    }

    @Unique
    private static void storeRetributionDmgIfPossible(LivingEntity target, float damageDealt) {
        if (target.hasStatusEffect(InitEffects.RETRIBUTION)) {
            EnderismCCA.RetributionalDamageManager.addDmg(target, damageDealt);
        }
    }

    @Inject(at = @At("HEAD"), method = "modifyAppliedDamage", cancellable = true)
    private void enderism$injectStaticDamage(DamageSource source, float amount,
                                            CallbackInfoReturnable<Float> cir) {
        if (source.getSource() instanceof LivingEntity attacker) {
            if (attacker.hasStatusEffect(InitEffects.CHARGED)) {
                int dur = Objects.requireNonNull(attacker.getStatusEffect(InitEffects.CHARGED)).getDuration();
                int amp = Objects.requireNonNull(attacker.getStatusEffect(InitEffects.CHARGED)).getAmplifier() + 1;

                cir.setReturnValue(ChargedEffect.calculateDamage(amount, amp, dur, 0.1F, this.hasStatusEffect(InitEffects.CHARGED)));
            }
        }
    }
}
