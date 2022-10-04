package fr.myges.discord.bot.MyGesClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.myges.discord.bot.MyGesClient.Models.Response.AgendaResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Base64;

public class MyGesClient {

    private String token;

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
            if (key_value[0].equals("access_token")) {
                this.token = key_value[1];
                System.out.println("Login success");
                break;
            }
        }
    }

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
}
