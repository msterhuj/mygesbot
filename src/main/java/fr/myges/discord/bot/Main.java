package fr.myges.discord.bot;

import fr.myges.discord.bot.Commands.AgendaCmdListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Main {
    public static void main(String[] args) throws Exception {

        Config.init();

        JDABuilder builder = JDABuilder.createDefault(Config.TOKEN);
        builder.setActivity(net.dv8tion.jda.api.entities.Activity.playing("self development"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        JDA jda = builder.build();
        jda.awaitReady();

        // init all guilds commands
        jda.getGuilds().forEach(guild -> {
            System.out.println("Init commands for guild " + guild.getName());
            guild.updateCommands().addCommands(
                Commands.slash("agenda", "Give you the agenda for the week")
                    .addOption(OptionType.INTEGER, "offset", "Change offset of week from actual date (default 0)", false)
            ).queue();
        });

        // register all listeners
        jda.addEventListener(new AgendaCmdListener());
    }
}