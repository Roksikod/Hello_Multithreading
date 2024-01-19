package runningMultipleThreads;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");
// Runnable myRunnable = new MyRunnable();
// Thread thread = new Thread(myRunnable);
// Thread thread = new MyThread();
        //или, что то же самое по сути, но меньше кода - используется анонимный класс
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.print(i);
                }
            }
        });
        thread.start();
        for (int i = 0; i < 1000; i++) {
            System.out.print("M");
        }
        System.out.println("\nFinish");
    }
}
