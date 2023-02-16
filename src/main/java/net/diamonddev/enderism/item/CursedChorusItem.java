package net.diamonddev.enderism.item;

import net.diamonddev.enderism.registry.BlockInit;
import net.diamonddev.enderism.registry.SoundEventInit;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class CursedChorusItem extends Item {


    private final String KEY_UUID = "enderism.uuid";
    private final String KEY_NAME = "enderism.name";
    private final String KEY_COORDS = "enderism.coords";
    private final String KEY_PLAYERBOUND = "enderism.playerbound";
    private final String KEY_MAGNETITEBOUND = "enderism.magnetitebound";

    public PlayerEntity getPlayer(ItemStack stack, World world) {
        for (PlayerEntity p : Objects.requireNonNull(world.getServer()).getPlayerManager().getPlayerList()) {
            if (p.getUuid().compareTo(stack.getOrCreateNbt().getUuid(KEY_UUID)) == 0) {
                return p;
            }
        }
        return null;
    }
    public String getBoundedPlayerName(ItemStack stack) {
        return stack.getOrCreateNbt().getString(KEY_NAME);
    }
    public int[] getListCoords(ItemStack stack) {
        return stack.getOrCreateNbt().getIntArray(KEY_COORDS);
    }
    public boolean isPlayerbound(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean(KEY_PLAYERBOUND);
    }
    public boolean isMagnetiteBound(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean(KEY_MAGNETITEBOUND);
    }

    public void addNBT(ItemStack stack, String name, UUID uuid) {
        // Playerbound Method

        stack.setNbt(null);
        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean(KEY_PLAYERBOUND, true);
        nbt.putBoolean(KEY_MAGNETITEBOUND, false);
        nbt.putString(KEY_NAME, name);
        nbt.putUuid(KEY_UUID, uuid);
        stack.setNbt(nbt);
    }

    public void addNBT(ItemStack stack, BlockPos blockcoords) { // todo: interdimensional travel with chorus magnetite
        // Chorus Magnetite Bound Method
        String name = "Chorus Magnetite at (" + blockcoords.getX() + ", " + blockcoords.getY() + ", " + blockcoords.getZ() + ")";
        int[] ints = getIntArrFromBP(blockcoords);

        stack.setNbt(null);
        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean(KEY_PLAYERBOUND, false);
        nbt.putBoolean(KEY_MAGNETITEBOUND, true);
        nbt.putString(KEY_NAME, name);
        nbt.putIntArray(KEY_COORDS, ints);
        stack.setNbt(nbt);
    }
    public CursedChorusItem() {
        super(new FabricItemSettings().maxCount(1).food(
                new FoodComponent.Builder()
                        .saturationModifier(0.1f).hunger(10)
                        .alwaysEdible()
                        .statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1), 1.0f)
                        .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 1), 1.0f)
                        .build()));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) { // todo: interdimensional travel

        if (isPlayerbound(stack)) { // Player Bind
            if (!world.isClient()) {
                if (getPlayer(stack, world) != null) {
                    if (
                            Arrays.stream(Objects.requireNonNull(world.getServer()).getPlayerNames())
                                    .anyMatch(Predicate.isEqual(getPlayer(stack, world).getGameProfile().getName()))

                    ) { // Check player is online
                        Vec3d coords = getPlayer(stack, world).getPos();
                        teleport(user, world, stack, coords);
                    } else {
                        failTeleport(user, world, stack);
                    }
                } else {
                    failTeleport(user, world, stack);
                }
            }
        } else if (isMagnetiteBound(stack)) { // Chorus Magnetite Bind
            if (!world.isClient()) {
                int[] ia = getListCoords(stack);
                Vec3d coords = new Vec3d(ia[0], ia[1], ia[2]);
                BlockPos pos = new BlockPos(coords);

                // Check Magnetite Still Exists and is in this dimension
                if (world.getBlockState(pos).getBlock() == BlockInit.CHORUS_MAGNETITE) {

                    world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 2f);
                    coords = new Vec3d(ia[0], ia[1] + 1, ia[2]);
                    teleport(user, world, stack, coords);
                    world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 0.1f);
                    stack.setNbt(null);

                } else {
                    failTeleport(user, world, stack);
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Handle adding an entity bind to the fruit

        if (stack.getItem() instanceof CursedChorusItem) {
            if (target instanceof PlayerEntity) {
                target.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEventInit.CURSED_CHORUS_FRUIT_PLAYER_BIND, SoundCategory.BLOCKS, 1.5f, 2f);
                addNBT(stack,((PlayerEntity) target).getGameProfile().getName(), target.getUuid());
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

        if (block == BlockInit.CHORUS_MAGNETITE) {
            addNBT(stack, pos);
            Vec3d vec = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            world.playSound(null, vec.x + 0.5, vec.y, vec.z + 0.5, SoundEventInit.CURSED_CHORUS_FRUIT_CHORUS_MAGNETITE_BIND, SoundCategory.BLOCKS, 1.5f, 2f);
            for (int i = 0; i < 5; i++) {
                world.addParticle(ParticleTypes.WITCH, vec.x + 0.5, vec.y + 0.5, vec.z + 0.5, 1, 1, 1);
            }
        }

        return super.useOnBlock(context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt();
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack s = new ItemStack(this);
        s.getOrCreateNbt();
        return s;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt() && world != null) {
            tooltip.add(Text.translatable("item.enderism.cursed_chorus_fruit.bound", getBoundedPlayerName(stack)));
        } else if (!stack.hasNbt() && world != null) {
            tooltip.add(Text.translatable("item.enderism.cursed_chorus_fruit.unbound"));
        } else {
            tooltip.add(Text.literal("Something went wrong with this tooltip, oops!").formatted(Formatting.RED));
        }
    }

    private int[] getIntArrFromBP(BlockPos bp) {
        return new int[]{bp.getX(), bp.getY(), bp.getZ()};
    }

    private void failTeleport(LivingEntity user, World world, ItemStack stack) {
        user.damage(DamageSource.MAGIC, 6f);
        world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, SoundCategory.PLAYERS, 1f, 0.1f);
        stack.setNbt(null);
    }

    private void teleport(LivingEntity user, World world, ItemStack stack, Vec3d coords) {
        world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 2f);
        user.teleport(coords.x + 0.5, coords.y, coords.z + 0.5, true);
        world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 0.1f);
        stack.setNbt(null);
    }
}
