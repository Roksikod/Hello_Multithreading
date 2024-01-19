package waitNotifyNotifyAll;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue {
    //все будет работать по принципам очереди, потому используем интерейес Queue типа Runnable
    private final Queue<Runnable> queue = new LinkedList<>();
    private final Object monitor = new Object();
    //чтобы из разных потоков методы могли быть синхронизированы, надо создать монитор
    //Нужно, чтобы при появлении задачи поток просыпался, а при конце списка задач (если очередь пуста) поток засыпал!

    public void add(Runnable task) {   //добавляет новую задачу в очередь
        synchronized (monitor) {
            queue.add(task); //добавляет элемент в очередь
            monitor.notifyAll(); //когда мы добавляем объект в очередь, мы говорим - разбуди всех, кто был в состоянии ожидания
        }
    }

    public Runnable take() {  //берет задачу из очереди
        synchronized (monitor) {
            try {
                while (queue.isEmpty()) {//если пусто, то
                    monitor.wait();//усыпить поток, в котором вызван метод take - перевести в ожидание, ОСВОБОДИТЬ монитор, а текущий поток - остановиться
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return queue.poll();  //забирает элемент из очереди
        }
    }
}
