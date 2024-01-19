package WaitNotifyNotifyAll;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
Создать три потока. Каждый выводит 5 раз букву А -1й, В-2й и С - 3й.
Нужно, чтобы в конце было АВСАВСАВСАВСАВС
Не использовать join(), sleep()
Вместо этого использовать wait(), notify()
 */
public class MainHW {
    private static final Object MONITOR = new Object();
    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    //создадим переменную, которая хранит значение, какая буква будет следующая
    private static String nextLetter = "A";

    public static void main(String[] args) {
        //логика такая - если в первом потоке не нужно печатать букву А, то этот поток засыпает.
        //А если нужно, то печатает А, говорит, что теперь нужно печатать букву В и будит остальные потоки
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (MONITOR) {
                    for (int i = 0; i < 5; i++) {
                        try {
                            while (!nextLetter.equals(A)) //пока не надо печатать А
                            {
                                MONITOR.wait(); //пусть поток спит
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.print(A); //если же надо печатать А, то блок с циклом и усыплением будет пропущен, мы печатаем здесь
                        nextLetter = B;  //присвоим новое значение для nextLetter
                        MONITOR.notifyAll(); //будим остальные потоки
                        //почему не notify(), если этот метод, то он разбудит первый попавшийся поток.
                        // И если после А вызовет С, то проверка nextLetter не пройдет, а программа зависнет - ее никто не вызывает
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (MONITOR) {
                    for (int i = 0; i < 5; i++) {
                        try {
                            while (!nextLetter.equals(B)){
                                MONITOR.wait();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.print(B);
                        nextLetter = C;
                        MONITOR.notifyAll();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (MONITOR) {
                    for (int i = 0; i < 5; i++) {
                        try {
                            while (!nextLetter.equals(C)){
                                MONITOR.wait();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.print(C);
                        nextLetter = A;
                        MONITOR.notifyAll();
                    }
                }
            }
        }).start();

    }
}

