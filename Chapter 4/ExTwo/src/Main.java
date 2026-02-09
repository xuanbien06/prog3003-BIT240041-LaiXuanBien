import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> cities = Arrays.asList("Hanoi", "Ho Chi Minh", "Da Nang", "Hue");

        // Sử dụng Lambda để sắp xếp theo độ dài chuỗi (ngắn đến dài)
        Collections.sort(cities, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

        System.out.println("Danh sach sau khi sap xep: " + cities);
    }
}