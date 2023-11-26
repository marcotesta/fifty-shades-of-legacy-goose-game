public class OrdinaryTentativePosition extends TentativePosition {
    protected OrdinaryTentativePosition(int tentativePosition) {
        super(tentativePosition);
    }

    public int getFinalPosition() {
        return tentativePosition;
    }

    public String getFinalPositionDescription(String player, int finalPosition) {
        return Integer.toString(finalPosition);
    }
}
