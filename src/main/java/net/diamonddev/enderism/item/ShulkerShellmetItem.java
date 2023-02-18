package net.diamonddev.enderism.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Wearable;

public class ShulkerShellmetItem extends Item implements Wearable {
    public ShulkerShellmetItem() {
        super(new FabricItemSettings().equipmentSlot((stack -> EquipmentSlot.HEAD)));
    }
}
