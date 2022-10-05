package fr.myges.discord.bot.Commands;

import fr.myges.discord.bot.Config;
import fr.myges.discord.bot.DateUtils;
import fr.myges.discord.bot.EmbedColor;
import fr.myges.discord.bot.MyGesClient.Models.Course;
import fr.myges.discord.bot.MyGesClient.Models.Response.AgendaResponse;
import fr.myges.discord.bot.MyGesClient.Models.Room;
import fr.myges.discord.bot.MyGesClient.MyGesClient;
import fr.myges.discord.bot.MyGesClient.WeekBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class AgendaCmdListener extends ListenerAdapter {

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
                ArrayList<Long> weekRange = WeekBuilder.getWeekRange(offset);

                // get data
                MyGesClient client = new MyGesClient();
                client.login(Config.USER, Config.PASS);
                AgendaResponse agendaResponse = client.getAgenda(weekRange);

                if (agendaResponse.getResult() == null || agendaResponse.getResult().isEmpty()) {
                    event.getHook().sendMessage("Aucun cours trouve").queue();
                    return;
                }

                // sort data
                ArrayList<Course> courses = agendaResponse.getResult();
                courses.sort(Comparator.comparingLong(Course::getStart_date));

                HashMap<Integer, ArrayList<Course>> coursesByDay = agendaResponse.getCoursesByDay();

                // https://stackoverflow.com/questions/18623838/java-get-hours-from-a-timestamp
                SimpleDateFormat formatDateString = new SimpleDateFormat("EEEE, d MMM", Locale.FRANCE);
                SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm", Locale.FRANCE);
                hourFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

                int now_day_number = DateUtils.getDayNumberFromDate(new Date(System.currentTimeMillis()));

                for (Map.Entry<Integer, ArrayList<Course>> entry : coursesByDay.entrySet()) {

                    StringBuilder currentDayString = new StringBuilder();
                    EmbedBuilder embed = new EmbedBuilder();
                    ArrayList<Course> currentDayCourses = entry.getValue();

                    if (currentDayCourses.isEmpty()) {
                        continue;
                    }

                    Date currentLoopDate = entry.getValue().get(0).getStartDate();

                    embed.setTitle(formatDateString.format(currentLoopDate));

                    if (now_day_number == DateUtils.getDayNumberFromDate(currentLoopDate)) {
                        embed.setColor(EmbedColor.BLUE.getValue());
                    } else if (now_day_number > DateUtils.getDayNumberFromDate(currentLoopDate)) {
                        embed.setColor(EmbedColor.RED.getValue());
                    } else {
                        embed.setColor(EmbedColor.GREEN.getValue());
                    }

                    for (Course course : currentDayCourses) {
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
                    event.getHook().sendMessageEmbeds(embed.build()).queue();
                }

            } catch (Exception e) {
                e.printStackTrace();
                event.getHook().sendMessage("Une erreur est survenue").queue();
            }
        }
    }
}
