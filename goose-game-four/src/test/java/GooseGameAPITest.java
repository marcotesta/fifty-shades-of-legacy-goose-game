import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.eclipse.jetty.http.HttpHeader.CONTENT_TYPE;
import static org.junit.Assert.assertThat;

public class GooseGameAPITest {

    GooseGame gooseGame;
    private ApplicationServer server;

    @Before
    public void InstantiateGooseGame() throws Exception {
        gooseGame  = new GooseGame(new MockDice(1), new MockDice(2));
        server = new ApplicationServer(4567, new GooseGameApplication(gooseGame));
        server.start();
    }

    @After
    public void after() throws Exception {
        server.stop();
    }

    @Test
    public void givenNoPlayersWhenAddPlayerThenPlayerAdded() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = httpPost(client, "http://localhost:4567/players/add", "name=Pippo");

        assertThat(response.body(), Is.is("players: Pippo"));
    }

    @Test
    public void givenAPlayerWhenRollDicesThenPlayerMoves() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        httpPost(client, "http://localhost:4567/players/add", "name=Pippo");

        HttpResponse<String> rolResponse = httpGet(client, "http://localhost:4567/players/Pippo/rolls");

        assertThat(rolResponse.body(), Is.is("Pippo rolls 1, 2. Pippo moves from Start to 3"));
    }

    private HttpResponse<String> httpGet(HttpClient client, String url) throws IOException, InterruptedException {
        HttpRequest diceRollsRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(diceRollsRequest, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> httpPost(HttpClient client, String url, String body) throws IOException, InterruptedException {
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(bodyPublisher)
                .header(CONTENT_TYPE.asString(), "application/x-www-form-urlencoded")
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
