package net.diamonddev.enderism.item;

import net.diamonddev.enderism.init.BlockInit;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class CursedChorusItem extends Item {


    private final String KEY_UUID = "enderism.uuid";
    private final String KEY_NAME = "enderism.name";
    private final String KEY_COORDS = "enderism.coords";
    private final String KEY_PLAYERBOUND = "enderism.playerbound";

    public PlayerEntity getPlayer(ItemStack stack, World world) {
        for (PlayerEntity p : world.getPlayers()) {
            if (p.getUuid().compareTo(stack.getOrCreateNbt().getUuid(KEY_UUID)) == 0) {
                return p;
            }
        }
        return null;
    }
    public String getBoundedPlayerName(ItemStack stack) {
        return stack.getOrCreateNbt().getString(KEY_NAME);
    }

    public List<Integer> getListCoords(ItemStack stack) {
        int[] ints = stack.getOrCreateNbt().getIntArray(KEY_COORDS);
        ArrayList<Integer> intList = new ArrayList<>();
        for (int i : ints) {
            intList.add(ints[i]);
        }
        return intList;
    }

    public boolean isPlayerbound(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean(KEY_PLAYERBOUND);
    }

    public void addNBT(ItemStack stack, String name, UUID uuid) {
        // Playerbound Method

        stack.setNbt(null);
        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean(KEY_PLAYERBOUND, true);
        nbt.putString(KEY_NAME, name);
        nbt.putUuid(KEY_UUID, uuid);
        stack.setNbt(nbt);
    }

    public void addNBT(ItemStack stack, ArrayList<Integer> blockcoords) {
        // Chorus Magnetite Bound Method
        String name = "(" + blockcoords.get(0) + ", " + blockcoords.get(1) + ", " + blockcoords.get(2) + ")";

        stack.setNbt(null);
        NbtCompound nbt = new NbtCompound();
        nbt.putBoolean(KEY_PLAYERBOUND, false);
        nbt.putString(KEY_NAME, name);
        nbt.putIntArray(KEY_COORDS, blockcoords);
        stack.setNbt(nbt);
    }
    public CursedChorusItem() {
        super(new FabricItemSettings().maxCount(1).group(ItemGroup.FOOD).food(
                new FoodComponent.Builder()
                        .saturationModifier(0.1f).hunger(10)
                        .alwaysEdible()
                        .statusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 1), 1.0f)
                        .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 1), 1.0f)
                        .build()));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

        if (isPlayerbound(stack)) { // Player Bind
            if (!world.isClient()) {
                if (Arrays.stream(Objects.requireNonNull(world.getServer()).getPlayerNames())
                        .anyMatch(Predicate.isEqual(getPlayer(stack, world).getGameProfile().getName()))) {
                    Vec3d coords = getPlayer(stack, world).getPos();

                    world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 2f);
                    user.teleport(coords.x, coords.y, coords.z, true);
                    world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 0.1f);
                    stack.setNbt(null);
                }
            }
        } else if (getListCoords(stack) != null) { // Chorus Magnetite Bind
            if (!world.isClient()) {
                Vec3d coords = new Vec3d(getListCoords(stack).get(0), getListCoords(stack).get(1), getListCoords(stack).get(2));

                world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 2f);
                user.teleport(coords.x, coords.y, coords.z, true);
                world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 0.1f);
                stack.setNbt(null);
            }
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Handle adding an entity bind to the fruit

        if (stack.getItem() instanceof CursedChorusItem) {
            if (target instanceof PlayerEntity) {
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
            ArrayList<Integer> ints = new ArrayList<>();
            ints.add(pos.getX());
            ints.add(pos.getY() + 1);
            ints.add(pos.getZ());
            addNBT(stack, ints);
        }

        return super.useOnBlock(context);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt();
    }

    @Override
    public boolean isFood() {
        return this.getDefaultStack().hasNbt();
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
            tooltip.add(Text.translatable("item.enderism.cursed_chorus_fruit.bound", getBoundedPlayerName(stack).formatted(Formatting.GRAY)));
        } else if (!stack.hasNbt() && world != null) {
            tooltip.add(Text.translatable("item.enderism.cursed_chorus_fruit.unbound").formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.literal("Something went wrong with this tooltip, oops!").formatted(Formatting.RED));
        }
    }
}
