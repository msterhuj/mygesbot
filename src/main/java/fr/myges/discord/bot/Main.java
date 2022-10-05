package fr.myges.discord.bot;

import fr.myges.discord.bot.Commands.AgendaCmdListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Main {

    private static JDA jdaInstance;
    public static void main(String[] args) throws Exception {

        Config.init();

        JDABuilder builder = JDABuilder.createDefault(Config.TOKEN);
        builder.setActivity(net.dv8tion.jda.api.entities.Activity.playing("self development"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        try {
            Main.jdaInstance = builder.build();
            Main.jdaInstance.awaitReady();
        } catch (InvalidTokenException e) {
            System.out.println("Invalid discord bot token");
            System.exit(1);
        }

        // init all guilds commands
        Main.jdaInstance.getGuilds().forEach(guild -> {
            System.out.println("Init commands for guild " + guild.getName());
            guild.updateCommands().addCommands(
                Commands.slash("agenda", "Give you the agenda for the week")
                    .addOption(OptionType.INTEGER, "offset", "Change offset of week from actual date (default 0)", false)
            ).queue();
        });

        // register all listeners
        Main.jdaInstance.addEventListener(new AgendaCmdListener());
    }

    public static JDA getJdaInstance() {
        return jdaInstance;
    }
}