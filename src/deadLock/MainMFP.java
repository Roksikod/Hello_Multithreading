package deadLock;

public class MainMFP {
    public static void main(String[] args) {
        MFP mfp = new MFP();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mfp.scan(3);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mfp.print(5);
            }
        }).start();
    }
}
