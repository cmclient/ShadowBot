package net.shadow.bot;

import com.typesafe.config.Config;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.shadow.bot.command.Command;
import net.shadow.bot.command.impl.HelpCommand;
import net.shadow.bot.command.impl.SuggestCommand;
import net.shadow.bot.settings.Settings;
import net.shadow.bot.listener.MessageListener;
import net.shadow.bot.listener.SlashCommandListener;
import net.shadow.bot.manager.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Getter
public class DiscordBot {

    private Logger logger;
    private Config config;
    private JDA jda;
    private CommandManager commandManager;

    public DiscordBot() {
        try {
            this.start();
        } catch (Exception ex) {
            this.logger.error("Failed to start ShadowBot!", ex);
        }
    }

    private void start() throws InterruptedException {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.logger.info("Loading ShadowBot...");
        this.config = new Settings().getConfig();
        this.jda = JDABuilder.createLight(this.config.getString("bot.token"), Arrays.asList(GatewayIntent.values()))
                .addEventListeners(new MessageListener(), new SlashCommandListener(this))
                .setActivity(Activity.watching("/help")).build();
        this.logger.info("Loading commands...");
        this.commandManager = new CommandManager();
        this.jda.updateCommands().addCommands(new CommandData[]{(new HelpCommand(this)).getData(), (new SuggestCommand(this)).getData()}).queue();
        this.logger.info("Waiting for bot to be ready..");
        this.jda.awaitReady();
        this.commandManager.getCommands().values().forEach(Command::onReady);
        this.logger.info("Bot is ready, invite: {}", this.jda.getInviteUrl(Permission.ADMINISTRATOR));
    }
}