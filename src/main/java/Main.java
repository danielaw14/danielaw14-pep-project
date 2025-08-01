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

        HttpRequest postMessageRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages"))
                .POST(HttpRequest.BodyPublishers.ofString("{"+
                        "\"posted_by\":1, " +
                        "\"message_text\": \"hello message\", " +
                        "\"time_posted_epoch\": 1669947792}"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = webClient.send(postMessageRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();

        ObjectMapper om = new ObjectMapper();
        Message expectedResult = new Message(2, 1, "hello message", 1669947792);
        System.out.println(response.body().toString());
        Message actualResult = om.readValue(response.body().toString(), Message.class);
        System.out.print(status + "\n" + expectedResult + "\n" + actualResult + "\n");
    }
}
