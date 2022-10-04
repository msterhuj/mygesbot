package fr.myges.discord.bot.MyGesClient.Models;

import java.sql.Date;
import java.util.ArrayList;

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

    // Getter Setters Generated

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
