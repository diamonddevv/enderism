package net.diamonddev.enderism.item;

import net.diamonddev.enderism.nbt.EnderismNbt;
import net.diamonddev.enderism.registry.InitAdvancementCriteria;
import net.diamonddev.enderism.registry.InitBlocks;
import net.diamonddev.enderism.registry.InitSoundEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class CursedChorusItem extends Item {
    public CursedChorusItem() {
        super(new QuiltItemSettings().maxCount(1).food(
                new FoodComponent.Builder()
                        .saturationModifier(0.1f).hunger(10)
                        .alwaysEdible()
                        .build()));
    }

    public static void addCursedChorus(FabricItemGroupEntries content, CursedChorusItem itemInstance) {
        content.addAfter(Items.CHORUS_FRUIT, itemInstance.getDefaultStack());
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        EnderismNbt.CursedChorusBindManager.clearBinds(stack);
        return stack;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) { // todo: interdimensional travel

        if (user instanceof ServerPlayerEntity spe) InitAdvancementCriteria.EAT_BOUND_CURSED_CHORUS.trigger(spe);

        if (EnderismNbt.CursedChorusBindManager.isPlayerbound(stack)) { // Player Bind
            if (!world.isClient()) {
                if (EnderismNbt.CursedChorusBindManager.getBoundPlayer(stack, world) != null) {
                    if (
                            Arrays.stream(Objects.requireNonNull(world.getServer()).getPlayerNames())
                                    .anyMatch(Predicate.isEqual(EnderismNbt.CursedChorusBindManager.getBoundPlayer(stack, world).getGameProfile().getName()))

                    ) { // Check player is online
                        Vec3d coords = EnderismNbt.CursedChorusBindManager.getBoundPlayer(stack, world).getPos();
                        teleport(user, world, stack, coords);
                    } else {
                        failTeleport(user, world, stack);
                    }
                } else {
                    failTeleport(user, world, stack);
                }
            }
        } else if (EnderismNbt.CursedChorusBindManager.isMagnetiteBound(stack)) { // Chorus Magnetite Bind
            if (!world.isClient()) {
                Vec3d coords = EnderismNbt.CursedChorusBindManager.getBoundVector(stack);
                BlockPos pos = BlockPos.fromPosition(coords);

                // Check Magnetite Still Exists and is in this dimension
                if (world.getBlockState(pos).getBlock() == InitBlocks.CHORUS_MAGNETITE) {

                    world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 2f);
                    teleport(user, world, stack, alterVectorForMagnetite(coords));
                    world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 0.1f);
                    stack.setNbt(null);

                } else {
                    failTeleport(user, world, stack);
                }
            }
        }

        applyEatEffects(user);
        if (!(user instanceof PlayerEntity p && p.getAbilities().creativeMode)) EnderismNbt.CursedChorusBindManager.clearBinds(stack);

        return super.finishUsing(stack, world, user);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Handle adding an entity bind to the fruit

        if (stack.getItem() instanceof CursedChorusItem) {
            if (target instanceof PlayerEntity player) {
                target.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), InitSoundEvents.CURSED_CHORUS_FRUIT_PLAYER_BIND, SoundCategory.BLOCKS, 1.5f, 2f);
                EnderismNbt.CursedChorusBindManager.setPlayerBind(player, stack);
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // Handle adding a Chorus Magnetite bind to the fruit

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState blockstate = world.getBlockState(pos);
        Block block = blockstate.getBlock();
        ItemStack stack = context.getStack();

        if (block == InitBlocks.CHORUS_MAGNETITE) {
            EnderismNbt.CursedChorusBindManager.setMagnetiteBind(pos, stack);
            Vec3d vec = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            world.playSound(null, vec.x + 0.5, vec.y, vec.z + 0.5, InitSoundEvents.CURSED_CHORUS_FRUIT_CHORUS_MAGNETITE_BIND, SoundCategory.BLOCKS, 1.5f, 2f);
            for (int i = 0; i < 5; i++) {
                world.addParticle(ParticleTypes.WITCH, vec.x + 0.5, vec.y + 0.5, vec.z + 0.5, 1, 1, 1);
            }
        }

        return super.useOnBlock(context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return EnderismNbt.CursedChorusBindManager.isBound(stack);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(getTooltipData(stack, world));
    }

    private static MutableText getTooltipData(ItemStack stack, World world) {
        MutableText text = Text.translatable("item.enderism.cursed_chorus_fruit.bound");

        if (EnderismNbt.CursedChorusBindManager.isMagnetiteBound(stack)) {
            Vec3d pos = EnderismNbt.CursedChorusBindManager.getBoundVector(stack);
            text.append(Text.translatable(InitBlocks.CHORUS_MAGNETITE.getTranslationKey()));
            text.append(" ").append(Text.translatable("enderism.generic_terms.at")).append(" ");
            text.append("[" + pos.x + ", " + pos.y + ", " + pos.z + "]");

            return text;

        } else if (EnderismNbt.CursedChorusBindManager.isPlayerbound(stack)) {
            PlayerEntity player = EnderismNbt.CursedChorusBindManager.getBoundPlayer(stack, world);
            text.append(player.getName());

            return text;

        } else {
            return Text.translatable("item.enderism.cursed_chorus_fruit.unbound");
        }
    }

    private static MutableText getFailedTooltip() {
        return Text.literal("Something went wrong with this tooltip, oops!").formatted(Formatting.RED);
    }

    private void failTeleport(LivingEntity user, World world, ItemStack stack) {
        user.damage(user.getDamageSources().magic(), 6f);
        world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 1f, 0.1f);
    }

    private void teleport(LivingEntity user, World world, ItemStack stack, Vec3d coords) {
        world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 2f);
        user.teleport(coords.x, coords.y, coords.z, true);
        world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 0.1f);
    }

    public void applyEatEffects(LivingEntity user) {
        if (user instanceof PlayerEntity pe && pe.getAbilities().creativeMode) return;

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1), user);
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 1), user);
    }

    private static Vec3d alterVectorForMagnetite(Vec3d vec) {
        return new Vec3d(vec.x + 0.5, vec.y + 1, vec.z + 0.5);
    }
}
