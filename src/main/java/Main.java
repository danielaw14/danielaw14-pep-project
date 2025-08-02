import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.SocialMediaController;
import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.*;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        ConnectionUtil.resetTestDatabase();
        HttpClient webClient = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        app.start(8080);
        Thread.sleep(1000);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/1/messages"))
                .build();
        HttpResponse response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        List<Message> expectedResult = new ArrayList<>();
        expectedResult.add(new Message(1, 1, "test message 1", 1669947792));
        List<Message> actualResult = objectMapper.readValue(response.body().toString(), new TypeReference<List<Message>>(){});
        System.out.print(status + "\n" + expectedResult + "\n" + actualResult + "\n");
    }
}
