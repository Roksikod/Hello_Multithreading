package semaphoreCyclicBarrier;

import java.util.concurrent.CyclicBarrier;
/*
Класс Семафор работает с задачей: нам нужно, чтобы с файловой системой одновременно не работают больше трех потоков,
а остальные задачи при этом выполняются параллельно! Если с ExecutoreService, одновременно с файлами можно ограничить количество потоков,
но и остальные задачи не выполняются параллельно.
Семафор ограничивает доступ к какому-то ресурсу. В параметры передаем при создании объекта этого класса
количество потоков, которые хотим допустить к какой-то секции
В этом он похож наCountDownLatch/ aquire() - метод, который в Семафоре уменьшает счетчик
1) уменьшили количество потоков, которым есть доступ
2) работаем с файлами
3) вызываем релиз, чтобы опять увеличить и разрешить следующему потоку
release() - делается в блоке finally
 */
public class Cyclic {
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long millis = (long) (Math.random() * 5000 + 1000);
                    String name = Thread.currentThread().getName();
                    System.out.println(name + ": Data is being prepared");
                    try {
                        Thread.sleep(millis);
                        System.out.println(name + ": Data is ready");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        cyclicBarrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + ": Continue work");
                }
            }).start();
        }

    }
}
