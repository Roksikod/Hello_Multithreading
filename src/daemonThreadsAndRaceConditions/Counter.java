package daemonThreadsAndRaceConditions;
//в состоянии гонки потоки могут одновременно обращаться к переменной и пытаться ее изменить
public class Counter {
    private Object monitor = new Object();
    private int value;

    public void inc() {
        synchronized(monitor) {
            value++; //критическая секция
        }
    } //ключевое слово

    public synchronized void dec() {
        {
            synchronized (monitor){
                value--;
            }
        }
    }

    public int getValue() {
        return value;
    }
}
