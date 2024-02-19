package net.shadow.bot.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.shadow.bot.DiscordBot;
import net.shadow.bot.command.Command;

import java.time.Instant;
import java.util.Objects;

public class SuggestCommand extends Command {

    private final DiscordBot bot;
    private TextChannel channel;

    public SuggestCommand(DiscordBot bot) {
        super(Commands.slash("suggest", "Suggest something")
                .addOption(OptionType.STRING, "text", "Message of a suggestion", true)
                .setGuildOnly(true));
        (this.bot = bot).getCommandManager().getCommands().put(this.data.getName(), this);
    }

    @Override
    public void onReady() {
        this.channel = Objects.requireNonNull(this.bot.getJda().getGuildById(1019204023963885588L)).getTextChannelById(1019205112947806260L);
        if (this.channel == null) {
            this.bot.getLogger().warn("Suggestion channel does not exists!");
        }
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (this.channel == null) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Error!")
                    .setDescription("Suggestion channel does not exist.")
                    .setColor(0xFF0000); // Red color
            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            return;
        }
        if (event.getMember() == null) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Error!")
                    .setDescription("Member is null. (not in a guild?)")
                    .setColor(0xFF0000); // Red color
            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            return;
        }
        OptionMapping option = event.getOption("text");
        if (option == null || option.getAsString().isEmpty()) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Error!")
                    .setDescription("You did not provide the text of the suggestion.")
                    .setColor(0xFF0000); // Red color
            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            return;
        }
        String text = option.getAsString();
        this.channel.sendMessageEmbeds((new EmbedBuilder()).setTitle("Suggestion from " + event.getMember().getUser().getName()).setThumbnail(event.getMember().getUser().getAvatarUrl()).setDescription(text).setFooter("ShadowBot", this.bot.getJda().getSelfUser().getAvatarUrl()).setTimestamp(Instant.now()).build(), new MessageEmbed[0]).queue((message) -> {
            message.addReaction(Emoji.fromUnicode("✅")).queue();
            message.addReaction(Emoji.fromUnicode("❌")).queue();
        });
        EmbedBuilder successEmbed = new EmbedBuilder()
                .setTitle("Suggestion Added")
                .setDescription("Your suggestion has been successfully added.")
                .setColor(0x00FF00); // Green color
        event.replyEmbeds(successEmbed.build()).setEphemeral(true).queue();
    }
}
