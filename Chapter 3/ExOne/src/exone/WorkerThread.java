package exone;

public class WorkerThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread đang chạy...");
    }
}