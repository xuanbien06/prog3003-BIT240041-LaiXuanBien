package extwo;

// Lớp Computer
class Computer {
    // Thuộc tính cơ bản
    private String HDD;
    private String RAM;
    // Thuộc tính tùy chọn
    private boolean isBluetoothEnabled;

    // Constructor private, chỉ cho phép tạo qua Builder
    private Computer(ComputerBuilder builder) {
        this.HDD = builder.HDD;
        this.RAM = builder.RAM;
        this.isBluetoothEnabled = builder.isBluetoothEnabled;
    }

    @Override
    public String toString() {
        return "Computer [HDD=" + HDD + ", RAM=" + RAM + ", Bluetooth=" + (isBluetoothEnabled ? "Co" : "Khong") + "]";
    }

    // Static inner class Builder
    public static class ComputerBuilder {
        private String HDD;
        private String RAM;
        private boolean isBluetoothEnabled;

        // Bắt buộc nhập các thuộc tính cơ bản
        public ComputerBuilder(String hdd, String ram) {
            this.HDD = hdd;
            this.RAM = ram;
        }

        // Setter cho thuộc tính tùy chọn, trả về chính đối tượng Builder
        public ComputerBuilder setBluetoothEnabled(boolean isBluetoothEnabled) {
            this.isBluetoothEnabled = isBluetoothEnabled;
            return this;
        }

        // Phương thức build trả về đối tượng Computer hoàn chỉnh
        public Computer build() {
            return new Computer(this);
        }
    }
}

public class ExTwo {
    public static void main(String[] args) {
        // Tạo máy tính có Bluetooth
        Computer comp1 = new Computer.ComputerBuilder("500 GB", "8 GB")
                .setBluetoothEnabled(true)
                .build();

        // Tạo máy tính không có Bluetooth (bỏ qua hàm set)
        Computer comp2 = new Computer.ComputerBuilder("1 TB", "16 GB")
                .setBluetoothEnabled(false) // hoặc không gọi hàm này cũng được
                .build();

        // In kết quả
        System.out.println("Cau hinh may 1: " + comp1);
        System.out.println("Cau hinh may 2: " + comp2);
    }
}