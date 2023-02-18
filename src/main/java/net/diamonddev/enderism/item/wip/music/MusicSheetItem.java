package net.diamonddev.enderism.item.wip.music;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

import java.util.ArrayList;

public class MusicSheetItem extends Item {
    private final MusicSheetNoteProvider notes;

    public MusicSheetItem(Settings settings, MusicSheetNoteProvider notes) {
        super(settings);
        this.notes = notes;
    }

    public void play(MusicSheetInstrument instrument, World world, PlayerEntity user) {
        if (world.isClient) {
            String[] sep = this.notes.getNotes().split("-");
            int tickDiff = Integer.parseInt(sep[0]);

            String[] notes = sep[1].split(",");

            ArrayList<Double> parsedNotes = new ArrayList<>();
            for (String note : notes) {
                int readInt = Integer.parseInt(note);
                if (readInt < 0) {
                    parsedNotes.add((double) readInt);
                }
                double pitch = parseNote(readInt);
                parsedNotes.add(pitch);
            }

            long lastNote = user.age;
            boolean complete = false;

            while (!complete) {
                if (lastNote + tickDiff == user.age) {
                    user.playSound(
                            instrument.getSound(),
                            SoundCategory.RECORDS,
                            2.0f,
                            parsedNotes.get(0).floatValue()
                    );
                    parsedNotes.remove(0);
                    lastNote = user.age;

                    if (parsedNotes.isEmpty()) complete = true;
                }
            }
        }
    }

    private double parseNote(int note) {
        return Math.pow(2.0, (note - 12) / 12.0);
    }

}
