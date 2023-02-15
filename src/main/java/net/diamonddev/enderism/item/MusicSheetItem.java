package net.diamonddev.enderism.item;

import net.minecraft.item.Item;
public class MusicSheetItem extends Item {
    private final MusicSheetNoteProvider notes;

    public MusicSheetItem(Settings settings, MusicSheetNoteProvider notes) {
        super(settings);
        this.notes = notes;
    }
}
