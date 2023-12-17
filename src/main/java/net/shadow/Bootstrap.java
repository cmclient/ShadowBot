package net.shadow;

import net.shadow.bot.DiscordBot;

public class Bootstrap {

    public static void main(String[] args) {
        new Thread(DiscordBot::new, "DiscordThread").start();
    }
}