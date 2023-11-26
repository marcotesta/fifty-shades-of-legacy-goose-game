public class MockDice implements Dice {

    private int value;

    public MockDice(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int roll() {
        return this.value;
    }
}
