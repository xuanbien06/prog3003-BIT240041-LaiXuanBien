
package exfour;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExFour {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
    } catch (Exception e) {
        e.printStackTrace();
    }
        System.out.println("--- Bắt đầu xử lý đơn hàng ---");

        // Tác vụ 1: Kiểm tra tính khả dụng
        CompletableFuture<Void> checkStock = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000); // Giả lập thời gian xử lý
                System.out.println("Xong: Kiểm tra tính khả dụng của sản phẩm.");
            } catch (InterruptedException e) { }
        });

        // Tác vụ 2: Thanh toán
        CompletableFuture<Void> processPayment = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1500);
                System.out.println("Xong: Thanh toán.");
            } catch (InterruptedException e) { }
        });

        // Tác vụ 3: Vận chuyển
        CompletableFuture<Void> shipOrder = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Xong: Đóng gói và vận chuyển đơn hàng.");
            } catch (InterruptedException e) { }
        });

        // Sử dụng allOf để chờ tất cả hoàn thành
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(checkStock, processPayment, shipOrder);

        // Chặn luồng chính cho đến khi tất cả xong để in thông báo cuối cùng
        allTasks.get(); 
        
        System.out.println("-> Tất cả tác vụ đã hoàn tất. Đơn hàng xử lý thành công!");
    }
}