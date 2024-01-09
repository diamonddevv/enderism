package dev.diamond.enderism.modifier;

import dev.diamond.enderism.EnderismMod;
import net.diamonddev.libgenetics.common.api.v1.interfaces.RegistryInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

import java.util.List;

public class LootTableModifier implements RegistryInitializer {

    // i got this code from https://github.com/masik16u/More-Armor-Trims-Fabric/blob/main/src/main/java/net/masik/morearmortrims/util/ModLootTableModifiers.java
    // thank you for open sourcing!
    private static Identifier getModifierPath(Identifier modifyId) {
        return new Identifier(EnderismMod.modid, "modifier/" + modifyId.getNamespace() + "/" + modifyId.getPath());
    }
    private static void iteratePools(LootTable.Builder supplier, LootPool[] pools) {
        for (var pool : pools) {
            supplier.pool(pool);
        }
    }

    @Override
    public void register() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            LootTable table = lootManager.getLootTable(getModifierPath(id));
            if (table != LootTable.EMPTY) {
                LootPool[] pools = table.pools;
                if (pools != null) iteratePools(tableBuilder, pools);
            }
        }));
    }
}
