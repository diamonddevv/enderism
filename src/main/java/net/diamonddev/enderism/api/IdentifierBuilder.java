package net.diamonddev.enderism.api;

import net.minecraft.util.Identifier;

public class IdentifierBuilder {
    private final String id;

    public IdentifierBuilder(String namespace) {
        this.id = namespace;
    }


    public Identifier build(String path) {
        return new Identifier(this.id, path);
    }
}
