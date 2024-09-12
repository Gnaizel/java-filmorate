package gnaizel.inc.controller;

import gnaizel.inc.FilmoreateAplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    private static final String BASE_URL = "http://localhost:8080/users";

    @Test
    void getUsers() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Получен ответ от сервера: {}", response.body());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            log.error("Во время выполнения запроса произошло исключение", e);
            fail("Запрос не выполнен");
        }
    }

    @Test
    void postUser() {
        String jsonUser = "{\"login\":\"newLogin\", \"email\":\"newEmail@example.com\", \"name\":\"newName\", \"birthday\":\"2000-01-01\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonUser))
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Получен ответ от сервера: {}", response.body());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            log.error("Во время выполнения запроса произошло исключение", e);
            fail("Запрос не выполнен");
        }
    }

    @Test
    void updateUser() {
        String jsonUser = "{\"id\":1, \"login\":\"updatedLogin\", \"email\":\"updatedEmail@example.com\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(jsonUser))
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Получен ответ от сервера: {}", response.body());
            assertEquals(200, response.statusCode());
        } catch (IOException | InterruptedException e) {
            log.error("Во время выполнения запроса произошло исключение", e);
            fail("Запрос не выполнен");
        }
    }
}
