package DaemonThreadsAndRaceConditions;

public class Counter {
    private int value;

    public void inc() {
        value++;
    }

    public void dec() {
        value--;
    }

    public int getValue() {
        return value;
    }
}
