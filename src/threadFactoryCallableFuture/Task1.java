package threadFactoryCallableFuture;

import java.util.concurrent.*;

/*
Поток1 - имитирует загрузку имени пользователя
Поток2 - имитирует загрузку возраста пользователя
пока идет загрузка, на экране точки
После окончания загрузки появляется строка "Имя , возраст "
Точки перестают появляться
 */
public class Task1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.print(".");
                        Thread.sleep(300);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Future<String> futureName = executorService.submit(new Callable<String>() { //класс возвращает объект в отличие от Runnable + метод submit
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "John";
            }
        });
        Future<Integer> futureAge = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(4000);
                return 25;
            }
        });
        try {
            String name = futureName.get(); //get==join заставляет поток main остановиться и ждать, пока выполнится метод public String call() - вернется имя
            int age = futureAge.get();
            System.out.println("\nName: " + name + " Age: " + age);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
