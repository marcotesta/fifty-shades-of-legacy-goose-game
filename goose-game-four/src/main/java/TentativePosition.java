public abstract class TentativePosition {

    protected final int tentativePosition;

    protected TentativePosition(int tentativePosition) {
        this.tentativePosition = tentativePosition;
    }

    static TentativePosition create(int tentativePosition) {
        if (tentativePosition > 63)
            return new BouncingTentativePosition(tentativePosition);
        else if (tentativePosition == 6)
            return new BridgeTentativePosition(tentativePosition);
        else if (tentativePosition == 63)
            return new EndTentativePosition(tentativePosition);
        else
            return new OrdinaryTentativePosition(tentativePosition);
    }

    abstract public int getFinalPosition();

    abstract public String getFinalPositionDescription(String player, int finalPosition);
}
