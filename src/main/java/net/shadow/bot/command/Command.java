package net.shadow.bot.command;

import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@Getter
public abstract class Command {

    protected final SlashCommandData data;

    public Command(SlashCommandData data) {
        this.data = data;
    }

    public abstract void execute(SlashCommandInteractionEvent event);

    public void onReady() {}
}
