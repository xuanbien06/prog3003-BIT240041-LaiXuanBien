import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // Stream API chain method
        int sum = numbers.stream()
                .filter(n -> n % 2 == 0)      // Lọc số chẵn (2, 4, 6)
                .mapToInt(n -> n * n)         // Bình phương (4, 16, 36)
                .sum();                       // Tính tổng (4+16+36 = 56)

        System.out.println("Tong cac so chan binh phuong: " + sum);
    }
}