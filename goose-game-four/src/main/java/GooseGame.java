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

    public String MovePlayer(String player) {
        int dice1Roll = dice1.roll();
        int dice2Roll = dice2.roll();
        int move = dice1Roll + dice2Roll;

        for (Board currentPlayer : boards) {
            if (currentPlayer.player.equals(player)) {
                int startPosition = currentPlayer.position;
                String startPositionDescription = getStartPositionDescription(startPosition);
                int tentativePosition = currentPlayer.position += move;
                if(tentativePosition > 63)
                    currentPlayer.position = 63 - (tentativePosition - 63);
                else if (tentativePosition == 6)
                    currentPlayer.position += 6;
                String finalPositionDescription = getParticularPosition(player, tentativePosition);

                return player + " rolls " + dice1Roll + ", " + dice2Roll + ". " + player
                        + " moves from " + startPositionDescription + " to " + finalPositionDescription;
            }
        }
        return "No player named " + player;
    }

    private String getStartPositionDescription(int position) {

        if (position == 0) {
            return "Start";
        } else {
            return Integer.toString(position);
        }
    }

    private String getParticularPosition(String player, int tentativePosition) {

        String positionDescription = Integer.toString(tentativePosition);
        if(tentativePosition > 63) {
            int finalPosition = 63 - (tentativePosition - 63);
            positionDescription = "63. " + player + " bounces! " + player + " returns to " + finalPosition;
        }
        else {
            switch (tentativePosition) {
                case 6:
                    positionDescription = "The Bridge. " + player + " jumps to 12";
                    break;
                case 63:
                    positionDescription += ". " + player + " Wins!!";
                    break;
            }
        }
        return positionDescription;
    }
}
