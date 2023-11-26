import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return findPlayer(newPlayer).isPresent();
    }

    public String MovePlayer(String player) {
        return findPlayer(player)
                .map(this::movePlayer)
                .orElse("No player named " + player);
    }

    private Optional<Board> findPlayer(String player) {
        return boards.stream().filter(currentPlayer-> currentPlayer.player.equals(player)).findAny();
    }

    private String movePlayer(Board currentPlayer) {
        int startPosition = currentPlayer.position;
        dice1.roll();
        dice2.roll();
        TentativePosition tentativePosition = getTentativePosition(currentPlayer.position, dice1.getValue(), dice2.getValue());
        int finalPosition = tentativePosition.getFinalPosition();
        currentPlayer.position = finalPosition;
        String startPositionDescription = getStartPositionDescription(startPosition);
        String finalPositionDescription = tentativePosition.getFinalPositionDescription(currentPlayer.player, finalPosition);
        return currentPlayer.player + " rolls " + dice1.getValue() + ", " + dice2.getValue() + ". " + currentPlayer.player
                + " moves from " + startPositionDescription + " to " + finalPositionDescription;
    }
    private String getStartPositionDescription(int position) {
        if (position == 0) {
            return "Start";
        } else {
            return Integer.toString(position);
        }
    }

    private TentativePosition getTentativePosition(int position, int dice1Roll, int dice2Roll) {
        return TentativePosition.create(position + dice1Roll + dice2Roll);
    }
}
