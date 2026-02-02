
package exthree;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ExThree {
    public static void main(String[] args) {
        try {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
    } catch (Exception e) {
        e.printStackTrace();
    }
        // B1: Cung cấp array bất đồng bộ
        CompletableFuture.supplyAsync(() -> {
            System.out.println("Đang tạo mảng...");
            return new Integer[]{1, 2, 5, 3, 100, 7, 4};
        })
        // B2: Lọc số lẻ và sắp xếp tăng dần
        .thenApply(arr -> {
            System.out.println("Đang xử lý và sắp xếp...");
            return Arrays.stream(arr)
                    .filter(n -> n % 2 != 0) // Lấy số lẻ
                    .sorted()                // Sắp xếp tăng dần
                    .collect(Collectors.toList());
        })
        // B3: Chuyển thành chuỗi
        .thenApply(list -> {
            System.out.println("Đang định dạng chuỗi...");
            return "Kết quả là: " + list.toString();
        })
        // B4: In ra màn hình
        .thenAccept(result -> {
            System.out.println(result);
        });

        // Giữ main thread sống để chờ kết quả (vì supplyAsync chạy luồng riêng)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
