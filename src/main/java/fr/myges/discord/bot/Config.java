package fr.myges.discord.bot;

import java.util.ArrayList;

public class Config {
    public static String TOKEN;
    public static String USER;
    public static String PASS;

    public static void init() {
        ArrayList<String> require_env = new ArrayList<>();
        require_env.add("TOKEN");
        require_env.add("GES_USER");
        require_env.add("GES_PASS");

        boolean error = false;

        for (String env : require_env) {
            if (System.getenv(env) == null) {
                System.out.println("Missing env variable " + env);
                error = true;
            }
        }

        if (error) {
            System.exit(1);
        }

        TOKEN = System.getenv("TOKEN");
        USER = System.getenv("GES_USER");
        PASS = System.getenv("GES_PASS");
    }
}
