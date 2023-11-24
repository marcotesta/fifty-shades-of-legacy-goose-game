public class TentativePosition {

    private final int position;

    public TentativePosition(int position) {
        this.position = position;
    }

    public int getFinalPosition() {
        if (position > 63)
            return 63 - (position - 63);
        else if (position == 6)
            return position + 6;
        else if (position == 63)
            return position;
        else
            return position;
    }

    public String getFinalPositionDescription(String player, int finalPosition) {
        String positionDescription = "";
        if(position > 63) {
            positionDescription = "63. " + player + " bounces! " + player + " returns to " + finalPosition;
        } else if (position == 6) {
            positionDescription = "The Bridge. " + player + " jumps to " + finalPosition;
        } else if (position == 63) {
            positionDescription = finalPosition + ". " + player + " Wins!!";
        } else {
            positionDescription = Integer.toString(finalPosition);
        }
        return positionDescription;
    }
}
