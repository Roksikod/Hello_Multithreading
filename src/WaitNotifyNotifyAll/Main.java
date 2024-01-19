package WaitNotifyNotifyAll;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();  //создаем объект этого класса
        new Thread(new Runnable() {
            //нам нужен бесконечный цикл задач, а если мы создадим его в главном потоке, то цикл никогда не будет выполнен. Поэтому мы переносим его в отдельный поток
            @Override
            public void run() {
                int i = 0;
                while (true) {   // в бесконечном цикле получаем задачи и если они там есть, начинаем их выполнение
                    System.out.println("Counter: " + i);
                    i++;
                    Runnable task = null;  //получаем задачу
                    try {
                        task = blockingQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new Thread(task).start();
                }
            }
        }).start();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            blockingQueue.add(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("---" + index);
                }
            });
        }
    }
}
