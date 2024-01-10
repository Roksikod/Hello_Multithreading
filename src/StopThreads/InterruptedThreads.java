package StopThreads;

public class InterruptedThreads {
    public static void main(String[] args) {


        System.out.println("Start");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 1000; i++) {
                        if (Thread.currentThread().isInterrupted()) {
                            break;
                        }
                        System.out.print(i);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            if (i == 100) {
                thread.interrupt();
            }
            System.out.print("M");
        }
        System.out.println("\nFinish");
    }
}
