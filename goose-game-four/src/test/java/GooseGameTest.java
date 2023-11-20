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

public class GooseGameTest {

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
    public void AddPlayerTest(){
        String player =  gooseGame.UserWrites("add player Pippo");
        assertThat(player, Is.is("players: Pippo"));
    }

    @Test
    public void AddPlayersTest(){
        gooseGame.UserWrites("add player Pippo");
        String  players =  gooseGame.UserWrites("add player Pluto");
        assertThat(players, Is.is("players: Pippo, Pluto"));
    }

    @Test
    public void AddDuplicatePlayerTest(){
        gooseGame.UserWrites("add player Pippo");
        String  players =  gooseGame.UserWrites("add player Pippo");
        assertThat(players, Is.is("Pippo: already existing player"));
    }

    @Test
    public void MovePlayerTest(){
        gooseGame.UserWrites("add player Pippo");
        gooseGame.UserWrites("add player Pluto");
        String position = gooseGame.UserWrites("move Pippo 4, 2");
        assertThat(position, Is.is("Pippo rolls 4, 2. Pippo moves from Start to The Bridge. Pippo jumps to 12"));
    }

    @Test
    public void Move2PlayerTest(){
        gooseGame.UserWrites("add player Pippo");
        gooseGame.UserWrites("add player Pluto");
        gooseGame.UserWrites("move Pippo 4, 2");
        String position = gooseGame.UserWrites("move Pluto 2, 2");
        assertThat(position, Is.is("Pluto rolls 2, 2. Pluto moves from Start to 4"));
    }

    @Test
    public void Move3PlayerTest(){
        gooseGame.UserWrites("add player Pippo");
        gooseGame.UserWrites("add player Pluto");
        gooseGame.UserWrites("move Pippo 3, 2");
        gooseGame.UserWrites("move Pluto 2, 2");
        String position = gooseGame.UserWrites("move Pippo 2, 3");
        assertThat(position, Is.is("Pippo rolls 2, 3. Pippo moves from 5 to 10"));
    }

    @Test
    public void WinPlayerTest(){
        gooseGame.UserWrites("add player Pippo");
        gooseGame.UserWrites("move Pippo 30, 30");
        String position = gooseGame.UserWrites("move Pippo 1, 2");
        assertThat(position, Is.is("Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!"));
    }

    @Test
    public void BouncePlayerTest(){
        gooseGame.UserWrites("add player Pippo");
        gooseGame.UserWrites("move Pippo 30, 30");
        String position = gooseGame.UserWrites("move Pippo 3, 2");
        assertThat(position, Is.is("Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61"));
    }

    @Test
    public void AutomaticDicePlayerTest(){
        GooseGame gooseGame = new GooseGame (new MockDice(1), new MockDice(2));
        gooseGame.UserWrites("add player Pippo");
        gooseGame.UserWrites("move Pippo 2, 2");
        String position = gooseGame.UserWrites("move Pippo");
        assertThat(position, Is.is("Pippo rolls 1, 2. Pippo moves from 4 to 7"));
    }

    @Test
    public void HttpPostTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = httpPost(client, "http://localhost:4567/players/add", "name=Pippo");

        assertThat(response.body(), Is.is("players: Pippo"));
    }

    @Test
    public void HttpGetTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        httpPost(client, "http://localhost:4567/players/add", "name=Pippo");

        HttpResponse<String> rolResponse = httpGet(client, "http://localhost:4567/players/Pippo/rolls");

        assertThat(rolResponse.body(), Is.is("Pippo rolls 1, 2. Pippo moves from Start to 3"));
    }

    @Test
    public void TheBridgeTest() {
        gooseGame.UserWrites("add player Pippo");
        gooseGame.UserWrites("move Pippo 2, 2");
        String position = gooseGame.UserWrites("move Pippo 1, 1");

        assertThat(position, Is.is("Pippo rolls 1, 1. Pippo moves from 4 to The Bridge. Pippo jumps to 12"));
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
