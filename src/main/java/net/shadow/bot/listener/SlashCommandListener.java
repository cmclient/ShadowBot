package net.shadow.bot.listener;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.shadow.bot.DiscordBot;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class SlashCommandListener extends ListenerAdapter {

    private final DiscordBot bot;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String name = event.getName();
        this.bot.getCommandManager().get(name).ifPresent(command -> command.execute(event));
    }
}
