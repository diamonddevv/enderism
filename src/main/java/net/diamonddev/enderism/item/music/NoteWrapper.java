package net.diamonddev.enderism.item.music;

import java.util.Arrays;
import java.util.List;

public class NoteWrapper {
    private final SerializedNotes notes;

    public final String id;
    public final List<NoteProvider> notation;
    public final int tickDiff;

    public NoteWrapper(SerializedNotes notes) {
        this.id = notes.id;
        this.notation = parseNotes(notes);
        this.tickDiff = notes.tickdiff;

        this.notes = notes;
    }

    private List<NoteProvider> parseNotes(SerializedNotes serializedNotes) {
        return Arrays.stream(serializedNotes.notes).mapToDouble(this::parseNote).mapToObj(NoteProvider::new).toList();
    }


    private double parseNote(int note) {
        return Math.pow(2.0, (note - 12) / 12.0);
    }
}
