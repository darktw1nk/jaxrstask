package com.jason.model.json.patch;

import com.fasterxml.jackson.databind.JsonNode;

public class CommandBuilder {
    private String op;
    private String path;
    private String from;
    private JsonNode value;

    public CommandBuilder setOp(String op) {
        this.op = op;
        return this;
    }

    public CommandBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public CommandBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public CommandBuilder setValue(JsonNode value) {
        this.value = value;
        return this;
    }

    public Command createCommand() {
        return new Command(op, path, from, value);
    }
}