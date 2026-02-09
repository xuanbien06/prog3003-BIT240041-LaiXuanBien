import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        List<String> moneyStrings = Arrays.asList("$10", "$20", "$50");
        List<Integer> moneyNumbers = new ArrayList<>();

        // Định nghĩa Function: cắt bỏ dấu $ và ép kiểu sang Integer
        Function<String, Integer> convert = str -> Integer.parseInt(str.substring(1));

        // Áp dụng cho từng phần tử
        for (String money : moneyStrings) {
            moneyNumbers.add(convert.apply(money));
        }

        System.out.println("Danh sach so nguyen: " + moneyNumbers);
    }
}