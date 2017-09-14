package com.jason.model.json.patch;

import com.fasterxml.jackson.databind.JsonNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Command {
    private String op;
    private String path;
    private String from;
    private JsonNode value;

    public Command() {
    }

    Command(String op, String path, String from, JsonNode value) {
        this.op = op;
        this.path = path;
        this.from = from;
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public JsonNode getValue() {
        return value;
    }

    public void setValue(JsonNode value) {
        this.value = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void apply() {
        throw new NotImplementedException();
    }
}
