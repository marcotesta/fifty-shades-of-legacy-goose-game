import java.util.Random;

public class RandomDice implements Dice {

    private Integer value = new Random().nextInt((6)) + 1;

    @Override
    public void roll() {
        value = new Random().nextInt((6)) + 1;
    }

    @Override
    public int getValue() {
        return value;
    }
}