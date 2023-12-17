package net.shadow.bot.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.shadow.bot.DiscordBot;
import net.shadow.bot.command.Command;

import java.time.Instant;

public class HelpCommand extends Command {

    private final DiscordBot bot;

    public HelpCommand(DiscordBot bot) {
        super(Commands.slash("help", "List of all commands"));
        (this.bot = bot).getCommandManager().getCommands().put(this.data.getName(), this);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        StringBuilder sb = new StringBuilder();

        this.bot.getCommandManager().getCommands().values().forEach((command) ->
                sb.append("**/").append(command.getData().getName())
                        .append("** - ").append(command.getData().getDescription())
                        .append('\n'));

        event.replyEmbeds(
                        (new EmbedBuilder())
                                .setTitle("List of available commands")
                                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                                .setDescription(sb.toString())
                                .setFooter("ShadowBot", this.bot.getJda().getSelfUser().getAvatarUrl())
                                .setTimestamp(Instant.now())
                                .build())
                .setEphemeral(true)
                .queue();
    }
}