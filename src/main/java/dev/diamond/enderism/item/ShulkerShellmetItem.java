package dev.diamond.enderism.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ShulkerShellmetItem extends Item implements Equipment {
    public ShulkerShellmetItem() {
        super(new FabricItemSettings().equipmentSlot((stack -> EquipmentSlot.HEAD)));
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public TypedActionResult<ItemStack> equipAndSwap(Item item, World world, PlayerEntity entity, Hand hand) {
        return Equipment.super.equipAndSwap(item, world, entity, hand);
    }
}
