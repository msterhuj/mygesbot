package fr.myges.discord.bot.MyGesClient.Models;

public class Discipline {
    private String name;
    private String teacher;
    private String student_group_name;
    private int teacher_id;
    private int trimester_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStudent_group_name() {
        return student_group_name;
    }

    public void setStudent_group_name(String student_group_name) {
        this.student_group_name = student_group_name;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getTrimester_id() {
        return trimester_id;
    }

    public void setTrimester_id(int trimester_id) {
        this.trimester_id = trimester_id;
    }
}
