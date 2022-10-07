package fr.myges.discord.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.myges.discord.api.Models.Response.AgendaResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;

public class MyGesClient {

    private String token;
    private int expire;

    public MyGesClient() {
        this.token = "";
        this.expire = -1;
    }

    public String basicAuthEncodeToBase64(String username, String password) {
        String auth = username + ":" + password;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public void login(String username, String password) throws Exception {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(MyGesEndpoint.AUTH.getURI())
                .header("Authorization", "Basic " + this.basicAuthEncodeToBase64(username, password))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 302) throw new Exception("Login failed with status code " + response.statusCode());
        if (response.headers().firstValue("location").isEmpty()) throw new Exception("Login failed no redirect uri found for token");

        String location = response.headers().firstValue("location").get();
        location = location.split("#")[1];
        String[] params = location.split("&");
        for (String param : params) {
            String[] key_value = param.split("=");
            switch (key_value[0]) {
                case "access_token":
                    this.token = key_value[1];
                    break;
                case "expires_in":
                    this.expire = Integer.parseInt(key_value[1]);
                    break;
            }
        }
        if (!this.token.isEmpty() && this.expire != -1) System.out.println("Login success");
        else System.out.println("Error when perform login to api");
    }

    // todo do check token validity
    // todo add refresh function

    private HttpResponse<String> sendRequest(String url) throws Exception {
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + this.token)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) throw new Exception("Request failed with status code " + response.statusCode());
        return response;
    }

    public AgendaResponse getAgenda(ArrayList<Long> weekRange) throws Exception {
        if (this.token == null) throw new Exception("You must login before using this method");

        String url = MyGesEndpoint.API.getEndpoint() + MyGesEndpoint.AGENDA.getEndpoint();
        url += "?start=" + weekRange.get(0) + "&end=" + weekRange.get(1);

        HttpResponse<String> response = this.sendRequest(url);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        System.out.println("Parsed agenda ok");
        return objectMapper.readValue(response.body(), AgendaResponse.class);
    }

    /**
     * Find next week with course available and return response object and null if not
     * @return AgendaResponse|null
     * @throws Exception if login is not executed
     */
    public AgendaResponse getNextWeekCourse() throws Exception {
        if (this.token == null) throw new Exception("You must login before using this method");

        for (int i = 1; i <= 5; i++) {
            AgendaResponse agendaResponse = this.getAgenda(TimeBuilder.getWeekRangeTimestamp(i));
            if (agendaResponse.getResult().size() != 0) return agendaResponse;
        }
        return null;
    }

    // todo add getter with function getDateTimestampStartEnd
}
