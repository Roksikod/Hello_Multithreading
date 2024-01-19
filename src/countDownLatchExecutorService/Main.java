package countDownLatchExecutorService;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final  int index = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Start - " + index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Finish - " + index);
                }
                });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread:threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("All threads are terminated");
    }
}
