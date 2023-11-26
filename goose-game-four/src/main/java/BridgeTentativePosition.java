public class BridgeTentativePosition extends TentativePosition {
    protected BridgeTentativePosition(int tentativePosition) {
        super(tentativePosition);
    }
    public int getFinalPosition() {
        return tentativePosition + 6;
    }

    public String getFinalPositionDescription(String player, int finalPosition) {
        return  "The Bridge. " + player + " jumps to " + finalPosition;
    }
}
