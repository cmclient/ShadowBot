package net.shadow.bot.listener;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannelType() == ChannelType.TEXT && !event.getAuthor().isBot() && event.getChannel().getIdLong() == 1019205112947806260L) {
            event.getMessage().delete().queue();
            event.getAuthor().openPrivateChannel().queue(pc -> pc.sendMessage("Please use /suggest command.").queue());
        }
    }
}
