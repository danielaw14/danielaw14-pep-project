import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.SocialMediaController;
import Model.Account;
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

         HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString("{" +
                        "\"username\": \"\", " +
                        "\"password\": \"password\" }"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Account expectedAccount = new Account(1, "testuser1", "password");
        Account actualAccount = objectMapper.readValue(response.body().toString(), Account.class);
        System.out.print(status + "\n" + expectedAccount + "\n" + actualAccount + "\n");
    }
}
