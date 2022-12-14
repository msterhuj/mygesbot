package fr.myges.discord.api.Models.Response;

import fr.myges.discord.api.DateUtils;
import fr.myges.discord.api.Models.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendaResponse {
    private int response_code;
    private String version;
    private ArrayList<Course> result;

    /**
     * Return cleaned data
     * @return HashMap<Integer, ArrayList<Course>>
     */
    public HashMap<Integer, ArrayList<Course>> getCoursesByDay() {

        HashMap<Integer, ArrayList<Course>> coursesByDay = new HashMap<>();

        ArrayList<Course> courses = this.result;
        courses.sort(Comparator.comparingLong(Course::getStart_date));

        for (Course course : courses) {
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
