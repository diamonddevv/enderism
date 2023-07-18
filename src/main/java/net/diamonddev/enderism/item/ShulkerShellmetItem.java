package net.diamonddev.enderism.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equippable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class ShulkerShellmetItem extends Item implements Equippable {
    public ShulkerShellmetItem() {
        super(new QuiltItemSettings().equipmentSlot((stack -> EquipmentSlot.HEAD)));
    }

    @Override
    public EquipmentSlot getPreferredSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public TypedActionResult<ItemStack> use(Item item, World world, PlayerEntity entity, Hand hand) {
        return Equippable.super.use(item, world, entity, hand);
    }
}
