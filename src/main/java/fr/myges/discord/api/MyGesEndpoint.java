package fr.myges.discord.api;

import java.net.URI;

public enum MyGesEndpoint {

    AUTH("https://authentication.kordis.fr/oauth/authorize?response_type=token&client_id=skolae-app"),
    API("https://api.kordis.fr"),

    PROFILE("/me/profile"),
    AGENDA("/me/agenda"), // https://api.kordis.fr/me/agenda?start={start}&end={end}
    NEWS("/me/news"),
    YEARS("/me/years"),
    TEACHERS("/me/{year}/teachers"),
    COURSES("/me/{year}/courses"),
    NEWS_BANNERS("/me/news/banners"),
    GRADES("/me/{year}/grades"),
    ABSENCES("/me/{year}/absences"),
    CLASSES("/me/{year}/classes"),
    STUDENTS("/me/classes/{classeId}/students"),
    STUDENT("/me/students/{studentId}");

    private final String endpoint;

    MyGesEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public URI getURI() {
        if (MyGesEndpoint.API == this || MyGesEndpoint.AUTH == this) {
            return URI.create(this.endpoint);
        }
        throw new IllegalStateException("Endpoint " + this.name() + " is not a valid URI");
    }
}
