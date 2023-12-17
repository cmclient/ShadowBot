package net.shadow.bot.manager;

import lombok.Getter;
import net.shadow.bot.command.Command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class CommandManager {

    private final Map<String, Command> commands = new LinkedHashMap<>();

    public Optional<Command> get(String name) {
        return Optional.of(this.commands.get(name));
    }
}
