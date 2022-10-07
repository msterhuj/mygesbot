package fr.myges.discord.api.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private int reservation_id;
    private ArrayList<Room> rooms;
    private long start_date;
    private long end_date;
    private String name;
    private Discipline discipline;
    private String teacher;

    public Date getStartDate() {
        return new Date(start_date);
    }

    public Date getEndDate() {
        return new Date(end_date);
    }
}
