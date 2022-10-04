package fr.myges.discord.bot.MyGesClient.Commands;

import fr.myges.discord.bot.Config;
import fr.myges.discord.bot.MyGesClient.Models.Course;
import fr.myges.discord.bot.MyGesClient.Models.Response.AgendaResponse;
import fr.myges.discord.bot.MyGesClient.MyGesClient;
import fr.myges.discord.bot.MyGesClient.WeekBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AgendaCmdListener extends ListenerAdapter {
    // https://jda.wiki/using-jda/interactions/#slash-commands
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("agenda")) {

            event.deferReply().queue();

            try {
                // c'est codé sale mais je m'en fou
                int offset;
                try { offset = Objects.requireNonNull(event.getOption("offset")).getAsInt(); }
                catch (NullPointerException e) { offset = 0; }
                ArrayList<Long> weekRange = WeekBuilder.getWeekRange(offset);


                MyGesClient client = new MyGesClient();
                client.login(Config.USER, Config.PASS);
                AgendaResponse agendaResponse = client.getAgenda(weekRange);

                if (agendaResponse.getResult() == null || agendaResponse.getResult().isEmpty()) {
                    event.getHook().sendMessage("Aucun cours trouve").queue();
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Agenda");
                embed.setDescription("Agenda de la semaine");

                // todo create one field per day

                String currentDay = null;

                // current string
                StringBuilder currentString = new StringBuilder();

                ArrayList<Course> courses = agendaResponse.getResult();

                // order by start date
                courses.sort((o1, o2) -> {
                    if (o1.getStart_date() > o2.getStart_date()) return 1;
                    else if (o1.getStart_date() < o2.getStart_date()) return -1;
                    else return 0;
                });

                StringBuilder currentDayString = new StringBuilder();

                // https://stackoverflow.com/questions/18623838/java-get-hours-from-a-timestamp

                for (Course course : courses) {
                    currentDayString.append(course.getTeacher());
                    currentDayString.append(" - ");
                    currentDayString.append(course.getName());
                    currentDayString.append("\n");
                }

                embed.addField("", currentDayString.toString(), false);

                event.getHook().sendMessageEmbeds(embed.build()).queue();


            } catch (Exception e) {
                e.printStackTrace();
                event.getHook().sendMessage("Une erreur est survenue").queue();
            }

        }
    }
}
