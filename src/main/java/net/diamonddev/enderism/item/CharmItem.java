package net.diamonddev.enderism.item;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.mixin.StatusEffectAccessor;
import net.diamonddev.enderism.nbt.EnderismNbt;
import net.diamonddev.enderism.registry.InitConfig;
import net.diamonddev.enderism.registry.InitSoundEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharmItem extends Item {

    public CharmItem(Settings settings) {
        super(settings);
    }

    public static void addAllCharms(FabricItemGroupEntries content, CharmItem itemInstance) {
        ItemStack emptyStack = new ItemStack(itemInstance);
        EnderismNbt.CharmEffectManager.setHas(emptyStack, false);
        content.addStack(emptyStack);

        if (InitConfig.ENDERISM.charmConfig.creativeHasAllCharms) {
            for (StatusEffect effect : Registries.STATUS_EFFECT) {
                ItemStack stack = new ItemStack(itemInstance);
                EnderismNbt.CharmEffectManager.set(stack, new StatusEffectInstance(effect, 200, 0));
                content.addStack(stack);
            }
        } else {
            for (Potion potion : Registries.POTION) {
                ItemStack stack = new ItemStack(itemInstance);
                if (!potion.getEffects().isEmpty()) {
                    StatusEffectInstance sei = potion.getEffects().get(0);
                    EnderismNbt.CharmEffectManager.set(stack, sei);
                    content.addStack(stack);
                }
            }
        }
    }

    public static boolean canUseAnyCharm(PlayerEntity player) {
        if (player.getAbilities().creativeMode) return true;

        for (Item item : Registries.ITEM) {
            if (item instanceof CharmItem charmItem) {
                return !player.getItemCooldownManager().isCoolingDown(charmItem);
            }
        }
        EnderismMod.logger.error("Tried to find first CharmItem in Item Registry to test for item cooldown, but none was found!");
        return false;
    }
    public static void setCooldownForAllCharms(PlayerEntity player, int ticks) {
        if (!player.getAbilities().creativeMode) {
            for (Item item : Registries.ITEM) {
                if (item instanceof CharmItem charmItem) {
                    player.getItemCooldownManager().set(charmItem, ticks);
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (canUseAnyCharm(user)) {
            applyEffect(stack, user, user);
            damageStack(stack, user);
            setCooldownForAllCharms(user, 20 * 15);
            return new TypedActionResult<>(ActionResult.SUCCESS, stack);
        }

        return new TypedActionResult<>(ActionResult.PASS, stack);
    }

    public static void applyEffect(ItemStack stack, LivingEntity target, LivingEntity user) {
        user.playSound(InitSoundEvents.CHARM_USE, 0.5f, user.getRandom().nextFloat());
        StatusEffectInstance instance = EnderismNbt.CharmEffectManager.get(stack);
        target.addStatusEffect(instance, user);
    }

    public static void damageStack(ItemStack stack, LivingEntity user) {
        stack.damage(1, user, living -> {});
    }

    public static ItemStack createCharm(StatusEffectInstance sei, CharmItem instance) {
        ItemStack stack = new ItemStack(instance);
        EnderismNbt.CharmEffectManager.set(stack, sei);
        return stack;
    }

    public static ItemStack createEmptyCharm(CharmItem instance) {
        ItemStack stack = new ItemStack(instance);
        EnderismNbt.CharmEffectManager.setHas(stack, false);
        return stack;
    }

    public static boolean hasEffect(ItemStack stack) {
        return EnderismNbt.CharmEffectManager.has(stack);
    }

    @Override
    public ItemStack getDefaultStack() {
        return createEmptyCharm(this);
    }

    public static int getColor(ItemStack stack) {
        if (EnderismNbt.CharmEffectManager.has(stack)) {
            return EnderismNbt.CharmEffectManager.get(stack).getEffectType().getColor();
        } return 0xfff;
    }

    private static void buildTooltipExtension(List<Text> tooltip, ItemStack stack) {
        if (!EnderismNbt.CharmEffectManager.has(stack)) {
            tooltip.add(Text.translatable("effect.none").formatted(Formatting.GRAY));
            return;
        }

        StatusEffectInstance sei = EnderismNbt.CharmEffectManager.get(stack);

        StatusEffect effect = sei.getEffectType();
        boolean beneficial = ((StatusEffectAccessor)effect).accessType() != StatusEffectType.HARMFUL;

        MutableText fx = Text.translatable(effect.getTranslationKey());
        MutableText level = Text.translatable("potion.potency." + sei.getAmplifier());
        MutableText time = Text.literal("(" + ticksToTime(sei.getDuration()) + ")");

        MutableText text = fx.append(" ");
        if (sei.getAmplifier() > 0)  text.append(level).append(" ");
        if (!sei.getEffectType().isInstant()) text.append(time);

        tooltip.add(text.formatted(beneficial ? Formatting.BLUE : Formatting.RED));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        buildTooltipExtension(tooltip, stack);
    }

    private static String ticksToTime(int ticks) {
        int seconds = ticks / 20;
        int minutes = Math.floorDiv(seconds, 60);
        int remainingSeconds = seconds - (minutes * 60);

        String s = remainingSeconds < 10 ? "0" + remainingSeconds : Integer.toString(remainingSeconds);
        String m = minutes < 10 ? "0" + minutes : Integer.toString(minutes);

        return m + ":" + s;
    }
}
