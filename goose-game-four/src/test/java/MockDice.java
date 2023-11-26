public class MockDice implements Dice {

    private int value;

    private boolean alreadyRolled = false;

    public MockDice(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
        this.alreadyRolled = false;
    }

    @Override
    public int roll() {
        if (this.alreadyRolled) {
            throw new RuntimeException("You cannot call method roll twice");
        }
        this.alreadyRolled = true;
        return this.value;
    }
}
