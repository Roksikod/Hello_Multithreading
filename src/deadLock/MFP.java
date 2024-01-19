package deadLock;

public class MFP {
//устройство одновременно печатает и сканирует документы

    private final Object printMonitor = new Object();
    private final Object scanMonitor = new Object();

    public void scan(int count) {
        synchronized (scanMonitor) {
            try {
                for (int i = 0; i < count; i++) {
                    Thread.sleep(100);
                    System.out.println("Scanned " + (i+1) + " pages.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void print(int count) {
        synchronized (printMonitor) {
            try {
                for (int i = 0; i < count; i++) {
                    Thread.sleep(100);
                    System.out.println("Printed " + (i+1) + " pages.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
