package net.diamonddev.enderism.item.music;

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
        String[] sep = this.notes.getNotes().split("-");
        double tickDiff = Double.parseDouble(sep[0]);

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

        long startTime = world.getTime();
        int notesToPlay = parsedNotes.size();
        int notesPlayed = 1;

        while (notesPlayed <= notesToPlay) {
            if (world.getTime() == (startTime + (notesPlayed * tickDiff))) {
                world.playSound(
                        user.getX(), user.getY(), user.getZ(),
                        instrument.getSound(),
                        SoundCategory.RECORDS,
                        2.0f,
                        parsedNotes.get(notesPlayed - 1).floatValue(),
                        true
                );

                notesPlayed++;
            }
        }
    }

    private double parseNote(int note) {
        return Math.pow(2.0, (note - 12) / 12.0);
    }

}
