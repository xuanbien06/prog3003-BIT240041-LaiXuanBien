import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        // Viết Lambda cho Predicate kiểm tra số chẵn
        Predicate<Integer> isEven = n -> n % 2 == 0;

        int number1 = 4;
        int number2 = 7;

        System.out.println(number1 + " la so chan? " + isEven.test(number1));
        System.out.println(number2 + " la so chan? " + isEven.test(number2));
    }
}