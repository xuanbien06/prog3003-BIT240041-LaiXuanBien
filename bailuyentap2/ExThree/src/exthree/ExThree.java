package exthree;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExThree {
    public static void main(String[] args) {
        System.out.println("Bat dau xu ly he thong dat ve...\n");

        // 1. Tác vụ xác thực thông tin (mất 2 giây)
        CompletableFuture<String> authTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-> Tac vu: Xac thuc thong tin khach hang HOAN THANH.");
            return "Xac thuc thanh cong";
        });

        // 2. Tác vụ xuất vé (mất 3 giây)
        CompletableFuture<String> ticketTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-> Tac vu: Xuat ve xem phim HOAN THANH.");
            return "Da in ve so #999";
        });

        // 3. Kết hợp 2 tác vụ bằng thenCombine
        CompletableFuture<String> combinedTask = authTask.thenCombine(ticketTask, (authResult, ticketResult) -> {
            return "HE THONG HOAN TAT: " + authResult + " | " + ticketResult;
        });

        // Chờ và in kết quả cuối cùng
        try {
            System.out.println("\n" + combinedTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}