package extwo;

// Giao diện chung cho các phương thức thanh toán
interface Payment {
    void processPayment(double amount);
}

// Các lớp triển khai (Concrete classes)
class CreditCardPayment implements Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Credit Card payment of $" + amount);
    }
}

class PayPalPayment implements Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}

class CashPayment implements Payment {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Cash payment of $" + amount);
    }
}

// Lớp Factory để tạo đối tượng
class PaymentFactory {
    public static Payment getPaymentMethod(String type) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("CREDITCARD")) {
            return new CreditCardPayment();
        } else if (type.equalsIgnoreCase("PAYPAL")) {
            return new PayPalPayment();
        } else if (type.equalsIgnoreCase("CASH")) {
            return new CashPayment();
        }
        throw new IllegalArgumentException("Unknown payment method type.");
    }
}

// Lớp Main chạy ví dụ
public class ExTwo {
    public static void main(String[] args) {
        System.out.println("--- HE THONG THANH TOAN ---");
        
        // Tạo thanh toán qua Credit Card
        Payment payment1 = PaymentFactory.getPaymentMethod("CREDITCARD");
        payment1.processPayment(150.50);

        // Tạo thanh toán qua PayPal
        Payment payment2 = PaymentFactory.getPaymentMethod("PAYPAL");
        payment2.processPayment(89.99);

        // Tạo thanh toán bằng Tiền mặt
        Payment payment3 = PaymentFactory.getPaymentMethod("CASH");
        payment3.processPayment(20.00);
    }
}