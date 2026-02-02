
package exone;

public class ExOne {
    public static void main(String[] args) {
        try {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
    } catch (Exception e) {
        e.printStackTrace();
    }
        // Khởi chạy WorkerThread
        WorkerThread t1 = new WorkerThread();
        t1.start();

        // Khởi chạy WorkerRunnable
        Thread t2 = new Thread(new WorkerRunnable());
        t2.start();
    }
}
