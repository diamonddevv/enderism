package net.diamonddev.enderism.advancement;

import net.diamonddev.enderism.EnderismMod;
import net.diamonddev.enderism.advancement.abstraction.AbstractRegistryPredicateTriggeredAdvancementCriterion;
import net.diamonddev.enderism.item.music.InstrumentItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class UseAllInstrumentsAdvancementCriterion extends AbstractRegistryPredicateTriggeredAdvancementCriterion<Item> {
    @Override
    public Registry<Item> getRegistry() {
        return Registries.ITEM;
    }

    @Override
    public Predicate<Item> getPredicate() {
        return item -> item instanceof InstrumentItem;
    }

    @Override
    public Identifier getId() {
        return EnderismMod.id("use_all_instruments");
    }
}
