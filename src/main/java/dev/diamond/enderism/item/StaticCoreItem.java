package dev.diamond.enderism.item;

import dev.diamond.enderism.registry.InitAdvancementCriteria;
import dev.diamond.enderism.registry.InitEffects;
import dev.diamond.enderism.registry.InitSoundEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

import static dev.diamond.enderism.registry.InitGamerules.STATIC_CORE_LENGTH;
import static dev.diamond.enderism.registry.InitGamerules.STATIC_CORE_STRENGTH;

public class StaticCoreItem extends Item {
    public StaticCoreItem(Settings settings) {
        super(settings);
    }

    private static final Random random = new Random();

    private static final int COOLDOWN = 60;
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        int effectStrength = world.getGameRules().getInt(STATIC_CORE_STRENGTH);
        int durationInSec = world.getGameRules().getInt(STATIC_CORE_LENGTH);

        player.addStatusEffect(new StatusEffectInstance(InitEffects.CHARGED, durationInSec * 20, effectStrength - 1));
        player.getStackInHand(hand).decrement(1);

        player.getItemCooldownManager().set(this, COOLDOWN);
        if (player instanceof ServerPlayerEntity) {
            InitAdvancementCriteria.USE_ITEM.trigger((ServerPlayerEntity) player, player.getStackInHand(hand));
        }

        player.playSound(InitSoundEvents.STATIC_CORE_USE, 1.0f, random.nextFloat(0.5f, 1.5f));

        return TypedActionResult.success(player.getStackInHand(hand));

    }
}