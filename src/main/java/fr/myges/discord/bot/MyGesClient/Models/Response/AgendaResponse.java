package fr.myges.discord.bot.MyGesClient.Models.Response;

import fr.myges.discord.bot.DateUtils;
import fr.myges.discord.bot.MyGesClient.Models.Course;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    // Getter & Setter Default

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<Course> getResult() {
        return result;
    }

    public void setResult(ArrayList<Course> result) {
        this.result = result;
    }
}
