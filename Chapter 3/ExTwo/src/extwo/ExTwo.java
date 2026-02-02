/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package extwo;

/**
 *
 * @author Admin
 */
public class ExTwo {
    public static void main(String[] args) {
        try {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
    } catch (Exception e) {
        e.printStackTrace();
    }
        for (int i = 1; i <= 5; i++) {
            final int threadNum = i;
            Thread t = new Thread(() -> {
                for (int j = 1; j <= 10; j++) {
                    System.out.println("Thread " + threadNum + ": " + j);
                    try {
                        Thread.sleep(100); // Ngủ nhẹ để thấy các thread chạy xen kẽ (tùy chọn)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }
}