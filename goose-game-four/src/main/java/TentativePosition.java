public class TentativePosition {

    public int getFinalPosition(int tentativePosition) {
        if (tentativePosition > 63)
            return 63 - (tentativePosition - 63);
        else if (tentativePosition == 6)
            return tentativePosition + 6;
        else if (tentativePosition == 63)
            return tentativePosition;
        else
            return tentativePosition;
    }

    public String getFinalPositionDescription(String player, int tentativePosition, int finalPosition) {
        String positionDescription = "";
        if(tentativePosition > 63) {
            positionDescription = "63. " + player + " bounces! " + player + " returns to " + finalPosition;
        } else if (tentativePosition == 6) {
            positionDescription = "The Bridge. " + player + " jumps to " + finalPosition;
        } else if (tentativePosition == 63) {
            positionDescription = finalPosition + ". " + player + " Wins!!";
        } else {
            positionDescription = Integer.toString(finalPosition);
        }
        return positionDescription;
    }
}
