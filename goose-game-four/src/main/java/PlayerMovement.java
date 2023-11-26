public class PlayerMovement {
    private final String playerName;
    private final int dice1Value;
    private final int dice2Value;
    private final int startPosition;
    private final TentativePosition tentativePosition;
    private final int finalPosition;

    public PlayerMovement(String name, int value, int value1, int startPosition, TentativePosition tentativePosition, int finalPosition) {
        playerName = name;
        dice1Value = value;
        dice2Value = value1;
        this.startPosition = startPosition;
        this.tentativePosition = tentativePosition;
        this.finalPosition = finalPosition;
    }

    public String playerMovementDescription() {
        String startPositionDescription = getStartPositionDescription(startPosition);
        String finalPositionDescription = tentativePosition.getFinalPositionDescription(playerName, finalPosition);
        return playerName + " rolls " + dice1Value + ", " + dice2Value + ". " + playerName
                + " moves from " + startPositionDescription + " to " + finalPositionDescription;
    }

    private String getStartPositionDescription(int position) {
        if (position == 0) {
            return "Start";
        } else {
            return Integer.toString(position);
        }
    }

}
