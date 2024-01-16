package CountDownLatchExecutorService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5); //фиксированный пул потоков
        //рационально использовать, если много разных задач в отдельных потоках
        CountDownLatch countDownLatch = new CountDownLatch(10); //счетчик для десяти потоков
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() { //передаем потоки на исполнение по мере освобождения потоков
                @Override
                public void run() {
                    System.out.println("Start - " + index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Finish - " + index);
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await(); //пока счетчик не станет ноль, ждем
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All threads are terminated");
    }
}
