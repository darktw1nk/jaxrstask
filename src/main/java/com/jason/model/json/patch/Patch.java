package com.jason.model.json.patch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public class Patch {
    private List<Command> commands;
    private JsonNode domain;

    Patch(List<Command> commands, Object domain) {
        ObjectMapper mapper = new ObjectMapper();

        this.commands = commands;
        this.domain = mapper.valueToTree(domain);
    }

    public JsonNode apply() throws PatchException {
        for (Command command : commands) {
            switch (command.getOp()) {
                case "replace":
                    applyReplace(command);
                    break;
                case "add":
                    applyAdd(command);
                    break;
                case "remove":
                    applyRemove(command);
                    break;
                case "move":
                    applyMove(command);
                    break;
                case "copy":
                    applyCopy(command);
                    break;
                case "test":
                    applyTest(command);
                    break;
            }
        }
        return domain;
    }

    private void applyReplace(Command command) throws PatchException {
        if (domain.path(command.getPath()).isMissingNode()) throw new PatchException("cant find path");
        ((ObjectNode) domain).set(command.getPath(), command.getValue());
    }

    private void applyAdd(Command command) throws PatchException {
        ((ObjectNode) domain).set(command.getPath(), command.getValue());
    }

    private void applyRemove(Command command) throws PatchException {
        if (domain.path(command.getPath()).isMissingNode() || domain.path(command.getPath()).isNull()) {
            throw new PatchException("path already null");
        }
        ((ObjectNode) domain).remove(command.getPath());
    }

    private void applyMove(Command command) throws PatchException {
        JsonNode movingValue = ((ObjectNode) domain).get(command.getFrom());
        applyRemove(new CommandBuilder().setPath(command.getFrom()).setOp("remove").createCommand());
        applyAdd(new CommandBuilder().setPath(command.getPath()).setOp("add").setValue(movingValue).createCommand());
    }

    private void applyCopy(Command command) throws PatchException {
        JsonNode movingValue = ((ObjectNode) domain).get(command.getFrom());
        applyAdd(new CommandBuilder().setPath(command.getPath()).setOp("add").setValue(movingValue).createCommand());
    }

    private void applyTest(Command command) throws PatchException {
        JsonNode testingValue = ((ObjectNode) domain).get(command.getPath());
        if(!testingValue.equals(command.getValue())) throw new PatchException("nodes not equal");
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public JsonNode getDomain() {
        return domain;
    }

    public void setDomain(JsonNode domain) {
        this.domain = domain;
    }
}
