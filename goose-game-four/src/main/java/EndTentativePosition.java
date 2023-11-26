public class EndTentativePosition extends TentativePosition {
    protected EndTentativePosition(int tentativePosition) {
        super(tentativePosition);
    }
    public int getFinalPosition() {
        return tentativePosition;
    }

    public String getFinalPositionDescription(String player, int finalPosition) {
        return finalPosition + ". " + player + " Wins!!";
    }
}
