public class MockDice implements Dice {

    int die;

    public MockDice(int die) {
        this.die = die;
    }

    @Override
    public int roll() {
        return this.die;
    }
}
