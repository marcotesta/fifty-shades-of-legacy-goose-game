import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class GooseGameTest {

    GooseGame gooseGame;
    MockDice dice1 = new MockDice(1);
    MockDice dice2 = new MockDice(2);


    @Before
    public void InstantiateGooseGame() {
        gooseGame  = new GooseGame(dice1, dice2);
    }

    @After
    public void after() {
    }
    @Test
    public void givenNoPlayersWhenAddPlayerThenANewPlayerIsAdded(){

        String player =  gooseGame.AddPlayer("Pippo");

        assertThat(player, Is.is("players: Pippo"));
    }

    @Test
    public void givenAPlayerWhenAddPlayerWithDifferentNameThenThereAreTwoPlayers(){
        gooseGame.AddPlayer("Pippo");

        String  players =  gooseGame.AddPlayer("Pluto");

        assertThat(players, Is.is("players: Pippo, Pluto"));
    }

    @Test
    public void givenAPlayerWhenAddPlayerWithSameNameThenNoPlayerIsAddedAndAWarningIsReturned(){
        gooseGame.AddPlayer("Pippo");

        String  players =  gooseGame.AddPlayer("Pippo");

        assertThat(players, Is.is("Pippo: already existing player"));
    }

    @Test
    public void givenPlayerAtStartWhenRoll4and2ThenPlayerMovesToPosition6AndJumpsToPosition12(){
        gooseGame.AddPlayer("Pippo");

        dice1.setValue(4);
        dice2.setValue(2);
        String position = gooseGame.MovePlayer("Pippo");

        assertThat(position, Is.is("Pippo rolls 4, 2. Pippo moves from Start to The Bridge. Pippo jumps to 12"));
    }

    @Test
    public void givenPlayerAtStartWhenRoll2and2ThenPlayerMovesToPosition4(){
        gooseGame.AddPlayer("Pluto");

        dice1.setValue(2);
        dice2.setValue(2);
        String position = gooseGame.MovePlayer("Pluto");

        assertThat(position, Is.is("Pluto rolls 2, 2. Pluto moves from Start to 4"));
    }

    @Test
    public void givenPlayerAtPosition5WhenRoll2and3ThenPlayerMovesToPosition10(){
        gooseGame.AddPlayer("Pippo");
        dice1.setValue(3);
        dice2.setValue(2);
        gooseGame.MovePlayer("Pippo");

        dice1.setValue(2);
        dice2.setValue(3);
        String position = gooseGame.MovePlayer("Pippo");

        assertThat(position, Is.is("Pippo rolls 2, 3. Pippo moves from 5 to 10"));
    }

    @Test
    public void givenPlayerAtPosition30WhenRoll1and2ThenPlayerMovesToPosition33AndWins(){
        gooseGame.AddPlayer("Pippo");
        dice1.setValue(30);
        dice2.setValue(30);
        gooseGame.MovePlayer("Pippo");

        dice1.setValue(1);
        dice2.setValue(2);
        String position = gooseGame.MovePlayer("Pippo");

        assertThat(position, Is.is("Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!"));
    }

    @Test
    public void givenPlayerAtPosition30WhenRoll3and2ThenPlayerMovesToPosition33AndBounceBackToPosition31(){
        gooseGame.AddPlayer("Pippo");
        dice1.setValue(30);
        dice2.setValue(30);
        gooseGame.MovePlayer("Pippo");

        dice1.setValue(3);
        dice2.setValue(2);
        String position = gooseGame.MovePlayer("Pippo");

        assertThat(position, Is.is("Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61"));
    }

    @Test
    public void givenPlayerAtPosition4WhenRoll1and2ThenPlayerMovesToPosition7(){
        gooseGame.AddPlayer("Pippo");
        dice1.setValue(2);
        dice2.setValue(2);
        gooseGame.MovePlayer("Pippo");

        dice1.setValue(1);
        dice2.setValue(2);
        String position = gooseGame.MovePlayer("Pippo");

        assertThat(position, Is.is("Pippo rolls 1, 2. Pippo moves from 4 to 7"));
    }

    @Test
    public void givenPlayerAtPosition4WhenRoll1and1ThenPlayerMovesToPosition6AndJumpsToPosition12() {
        gooseGame.AddPlayer("Pippo");
        dice1.setValue(2);
        dice2.setValue(2);
        gooseGame.MovePlayer("Pippo");

        dice1.setValue(1);;
        dice2.setValue(1);;
        String position = gooseGame.MovePlayer("Pippo");

        assertThat(position, Is.is("Pippo rolls 1, 1. Pippo moves from 4 to The Bridge. Pippo jumps to 12"));
    }

    @Test
    public void givenNoPlayerWhenRoll2and2ThenError(){

        dice1.setValue(2);
        dice2.setValue(2);
        String position = gooseGame.MovePlayer("Pluto");

        assertThat(position, Is.is("No player named Pluto"));
    }

}
