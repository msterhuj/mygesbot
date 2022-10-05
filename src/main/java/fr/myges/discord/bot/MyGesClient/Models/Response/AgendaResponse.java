package fr.myges.discord.bot.MyGesClient.Models.Response;

import fr.myges.discord.bot.DateUtils;
import fr.myges.discord.bot.MyGesClient.Models.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendaResponse {
    private int response_code;
    private String version;
    private ArrayList<Course> result;

    public HashMap<Integer, ArrayList<Course>> getCoursesByDay() {

        HashMap<Integer, ArrayList<Course>> coursesByDay = new HashMap<>();

        for (Course course : this.result) {
            int dayNumber = DateUtils.getDayNumberFromDate(new Date(course.getStart_date()));
            if (coursesByDay.containsKey(dayNumber)) {
                coursesByDay.get(dayNumber).add(course);
            } else {
                ArrayList<Course> coursesByDayList = new ArrayList<>();
                coursesByDayList.add(course);
                coursesByDay.put(dayNumber, coursesByDayList);
            }
        }

        // order by interger key (day)
        LinkedHashMap<Integer, ArrayList<Course>> sortedCoursesByDay = new LinkedHashMap<>();
        coursesByDay.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedCoursesByDay.put(x.getKey(), x.getValue()));
        return sortedCoursesByDay;
    }
}
