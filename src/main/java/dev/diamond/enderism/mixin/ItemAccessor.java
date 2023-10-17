package dev.diamond.enderism.mixin;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(Item.class)
public interface ItemAccessor {
    @Accessor("ATTACK_SPEED_MODIFIER_ID")
    static UUID accessConstantAttackSpeedModifierId()  {
        throw new AssertionError("this should not happen doofus lol");
    }
}
