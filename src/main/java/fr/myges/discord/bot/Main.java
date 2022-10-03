package fr.myges.discord.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        String token = System.getenv("TOKEN");
        if (token == null) {
            System.out.println("Please provide a token in the TOKEN env var");
            return;
        }

        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(net.dv8tion.jda.api.entities.Activity.playing("self development"));
        JDA jda = builder.build();
        jda.awaitReady();

    }
}