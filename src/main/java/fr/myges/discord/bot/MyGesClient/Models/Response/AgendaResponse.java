package fr.myges.discord.bot.MyGesClient.Models.Response;

import fr.myges.discord.bot.MyGesClient.Models.Course;

import java.util.ArrayList;

public class AgendaResponse {
    private int response_code;
    private String version;
    private ArrayList<Course> result;

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
