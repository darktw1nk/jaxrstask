package com.jason.model.json.patch;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class PatchBuilder {
    private List<Command> commands;
    private Object domain;

    public PatchBuilder setCommands(List<Command> commands) {
        this.commands = commands;
        return this;
    }

    public PatchBuilder setDomain(Object domain) {
        this.domain = domain;
        return this;
    }

    public Patch createPatch() {
        return new Patch(commands, domain);
    }
}