package fr.myges.discord.bot.Commands;

import fr.myges.discord.api.DateUtils;
import fr.myges.discord.api.Models.Course;
import fr.myges.discord.api.Models.Response.AgendaResponse;
import fr.myges.discord.api.Models.Room;
import fr.myges.discord.api.MyGesClient;
import fr.myges.discord.api.TimeBuilder;
import fr.myges.discord.bot.Config;
import fr.myges.discord.bot.EmbedColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class AgendaCmdListener extends ListenerAdapter {

    private final SimpleDateFormat formatDateString;
    private final SimpleDateFormat hourFormat;

    public AgendaCmdListener() {
        this.formatDateString = new SimpleDateFormat("EEEE, d MMM", Locale.FRANCE);
        this.hourFormat = new SimpleDateFormat("hh:mm", Locale.FRANCE);
        this.hourFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
    }

    private EmbedBuilder buildEmbed(ArrayList<Course> courses) {

        if (courses.isEmpty()) return null;
        int now_day_number = DateUtils.getDayNumberFromDate(new Date(System.currentTimeMillis()));

        StringBuilder currentDayString = new StringBuilder();
        EmbedBuilder embed = new EmbedBuilder();
        Date currentLoopDate = courses.get(0).getStartDate();

        embed.setTitle(formatDateString.format(currentLoopDate));

        if (now_day_number == DateUtils.getDayNumberFromDate(currentLoopDate)) {
            embed.setColor(EmbedColor.BLUE.getValue());
        } else if (now_day_number > DateUtils.getDayNumberFromDate(currentLoopDate)) {
            embed.setColor(EmbedColor.RED.getValue());
        } else {
            embed.setColor(EmbedColor.GREEN.getValue());
        }

        for (Course course : courses) {
            currentDayString.append(course.getName());
            currentDayString.append(" (");
            currentDayString.append(hourFormat.format(course.getStart_date()));
            currentDayString.append("-");
            currentDayString.append(hourFormat.format(course.getEnd_date()));
            currentDayString.append(")");

            ArrayList<Room> rooms = course.getRooms();

            if (rooms != null) {
                for (Room room : rooms) {
                    currentDayString.append(" - ");
                    currentDayString.append(room.getName());
                }
            }

            currentDayString.append("\n");
        }
        embed.addField("Cour", currentDayString.toString(), false);
        return embed;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("agenda")) {
            System.out.println("Agenda command received from " + event.getUser().getName() + " on guild " + Objects.requireNonNull(event.getGuild()).getName());

            event.deferReply().queue(); // defer reply to avoid timeout

            try {
                // c'est codé sale mais je m'en fou
                // init value for query
                int offset;
                try {
                    offset = Objects.requireNonNull(event.getOption("offset")).getAsInt();
                } catch (NullPointerException e) {
                    offset = 0;
                }

                // get data
                MyGesClient client = new MyGesClient();
                client.login(Config.USER, Config.PASS);
                AgendaResponse agendaResponse = client.getAgenda(TimeBuilder.getWeekRangeTimestamp(offset));

                if (agendaResponse.getResult() == null || agendaResponse.getResult().isEmpty()) {
                    event.getHook().sendMessage("Aucun cours trouve").queue();
                    return;
                }

                for (Map.Entry<Integer, ArrayList<Course>> entry : agendaResponse.getCoursesByDay().entrySet()) {
                    EmbedBuilder embed = this.buildEmbed(entry.getValue());
                    if (embed == null) continue;
                    event.getHook().sendMessageEmbeds(embed.build()).queue();
                }

            } catch (Exception e) {
                e.printStackTrace();
                event.getHook().sendMessage("Une erreur est survenue").queue();
            }
        }
    }
}
