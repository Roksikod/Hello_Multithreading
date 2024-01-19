package deadLock;

public class Account {
    private int amount1; //счет 1
    private int amount2; //счет 2
    //работа с одним счетом не должна блокировать работу с другим
    //два метода: перевод с 1 на 2 и наоборот, поэтому два монитора

    private final Object monitor1 = new Object();
    private final Object monitor2 = new Object();

    public Account(int amount1, int amount2) {
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    //при переводе денег сначала нужно убедиться, что там достаточно денег и только потом осущесьвлять перевод

    //на строчках synchronized (monitor1) и synchronized (monitor2) оба потока заснут,
    // так как они взаимно заблокируют друг друга и мониторы для продолжения в принципе никогад не освободятся
    public void transferFrom1To2(int amount) {
        synchronized (monitor1) { //нужно синхронизировать
            try {
                Thread.sleep(2000); //немного задержим, чтобы операция происходила не мгновенно
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (amount <= amount1) {
                System.out.println("amount <= amount1");
                synchronized (monitor2) { //работаем со вторым, нужно синхронизировать по второму, чтобы в этот момент никто не работал с ним и у нас не было ошибок
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    amount1 -= amount; //меняем количесво денег на счету
                    amount2 += amount;
                }
            } else {
                System.out.println("Insufficient funds");
            }
        }
    }

    public void transferFrom2To1(int amount) {
        synchronized (monitor2) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (amount <= amount2) {
                System.out.println("amount <= amount2");
                synchronized (monitor1) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    amount2 -= amount;
                    amount1 += amount;
                }
            } else {
                System.out.println("Insufficient funds");
            }
        }
    }
}
