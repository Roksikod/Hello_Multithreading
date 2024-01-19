package threadFactoryCallableFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) { //здесь мы указываем, что все созданные потоки делаем демонами
                Thread thread = new Thread(r); //создаем поток и отдаем ему внутрь объект Runnable
                thread.setDaemon(true);
                return thread; //теперь любой код, который мы выполняем через execute, будет выполняться через поток-демон
            }
        }); //запуск таймера через один поток, здесь можно в параметры передать объект анонимного класса, чтобы управлять потоком
        executorService.execute(new Runnable() { //на объект этого класса мы повлиять не можем и объявить таймер демоном
            @Override
            public void run() {
                int seconds = 0;
                try {
                    while (true) {
                        System.out.println(seconds);
                        Thread.sleep(1000);
                        seconds++;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //в гнлавном потоке реадизуем какую-нибудь задачу
        for (int i = 0; i < 10; i++) {
            System.out.println("...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\nFinish");
    }
}
