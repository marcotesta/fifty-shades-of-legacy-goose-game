public class BouncingTentativePosition extends TentativePosition {
    protected BouncingTentativePosition(int tentativePosition) {
        super(tentativePosition);
    }
    public int getFinalPosition() {
        return 63 - (tentativePosition - 63);
    }

    public String getFinalPositionDescription(String player, int finalPosition) {
        return  "63. " + player + " bounces! " + player + " returns to " + finalPosition;
    }

}
