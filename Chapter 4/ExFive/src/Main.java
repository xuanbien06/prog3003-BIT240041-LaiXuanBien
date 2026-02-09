import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        // Supplier: Trả về số ngẫu nhiên từ 0-100
        Supplier<Double> randomSupplier = () -> Math.random() * 100;

        // Consumer: In số ra màn hình với định dạng
        Consumer<Double> printConsumer = num -> System.out.println("So may man: " + String.format("%.2f", num));

        // Gọi Supplier lấy số, sau đó đưa vào Consumer
        Double luckyNumber = randomSupplier.get();
        printConsumer.accept(luckyNumber);
    }
}