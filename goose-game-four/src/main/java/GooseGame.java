import java.util.ArrayList;
import java.util.List;

public class GooseGame {
    private final Dice dice1, dice2;

    private final List<Board> boards = new ArrayList<>();

    public GooseGame(Dice dice1, Dice dice2)
    {
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

    public String AddPlayer(String player) {

        if (IsPlayerAlreadyPresent(player))
            return player + ": already existing player";

        boards.add(new Board(player));

        return getPlayersName();
    }

    private String getPlayersName() {
        String output = "";
        for (Board currentPlayer : boards) {
            if (!output.isEmpty())
                output += ", ";
            output += currentPlayer.player;
        }

        return "players: " + output;
    }

    private boolean IsPlayerAlreadyPresent(String newPlayer) {
        for (Board currentPlayer : boards) {
            if (currentPlayer.player.equals(newPlayer))
                return true;
        }
        return false;
    }

    public String MovePlayer(String command) {
        String[] temp = command.split(" ");
        if (temp.length == 1) {
            return MovePlayer("move " + command );
        }
        else {
            int dice1Roll = dice1.roll();
            int dice2Roll = dice2.roll();
            String player = temp[1];
            int move = dice1Roll + dice2Roll;

            String startPosition = "", newPosition = "";

            for (Board currentPlayer : boards) {
                if (currentPlayer.player.equals(player)) {
                    startPosition = Integer.toString(currentPlayer.position);
                    newPosition = Integer.toString(currentPlayer.position += move);
                    if(currentPlayer.position > 63)
                        currentPlayer.position = 63 - (currentPlayer.position - 63);
                    else if (currentPlayer.position == 6)
                        currentPlayer.position += 6;
                }
            }

            startPosition = getParticularPosition(player, startPosition);
            newPosition = getParticularPosition(player, newPosition);

            return player + " rolls " + dice1Roll + ", " + dice2Roll + ". " + player + " moves from " + startPosition + " to " + newPosition;
        }
    }

    private String getParticularPosition(String player, String position) {

        if(Integer.parseInt(position) > 63) {
            position = Integer.toString(63 - (Integer.parseInt(position) - 63));
            position = "63. " + player + " bounces! " + player + " returns to " + position;
        }
        else {
            switch (position) {
                case "0":
                    position = "Start";
                    break;
                case "6":
                    position = "The Bridge. " + player + " jumps to 12";
                    break;
                case "63":
                    position += ". " + player + " Wins!!";
                    break;
            }
        }
        return position;
    }
}
