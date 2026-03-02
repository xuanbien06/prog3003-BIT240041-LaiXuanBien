package exthree;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class ExThree {

    // Hàm tiện ích để giả lập thời gian xử lý (delay)
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        System.out.println("Bắt đầu giao dịch ngân hàng...\n");

        CompletableFuture<Void> bankTransaction = CompletableFuture.supplyAsync(() -> {
            // Tác vụ 1: Xác thực thông tin khách hàng
            System.out.println("[1] Đang xác thực thông tin khách hàng...");
            sleep(1500); // Giả lập mất 1.5s
            
            boolean isAuthSuccess = true; // Đổi thành false để test trường hợp huỷ giao dịch do xác thực
            if (!isAuthSuccess) {
                throw new RuntimeException("Xác thực thông tin thất bại!");
            }
            System.out.println("[1] Xác thực thành công.");
            return true;

        }).thenApplyAsync(isAuth -> {
            // Tác vụ 2: Kiểm tra số dư
            System.out.println("[2] Đang kiểm tra số dư trong tài khoản...");
            sleep(2000); // Giả lập mất 2s
            
            boolean hasEnoughBalance = true; // Đổi thành false để test trường hợp huỷ giao dịch do thiếu tiền
            if (!hasEnoughBalance) {
                throw new RuntimeException("Số dư không đủ để thực hiện giao dịch!");
            }
            System.out.println("[2] Số dư hợp lệ.");
            return true;

        }).thenAcceptAsync(isBalanceOk -> {
            // Tác vụ 3: Thực hiện chuyển tiền
            System.out.println("[3] Đang tiến hành chuyển tiền...");
            sleep(1000); // Giả lập mất 1s
            System.out.println("[3] Chuyển tiền thành công! Hoàn tất giao dịch.");

        }).exceptionally(ex -> {
            // Xử lý lỗi nếu tác vụ 1 hoặc 2 thất bại
            System.err.println("GIAO DỊCH BỊ HUỶ BỎ: " + ex.getMessage());
            return null;
        });

        // Giữ cho luồng main không bị tắt trước khi CompletableFuture chạy xong
        // (Vì CompletableFuture chạy ngầm trên ForkJoinPool daemon thread)
        bankTransaction.join(); 
        
        System.out.println("\nKết thúc chương trình.");
    }
}