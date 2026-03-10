package exone;

import java.util.TreeSet;

public class ExOne {
    public static void main(String[] args) {
        // 1. Tạo TreeSet
        TreeSet<String> names = new TreeSet<>();
        names.add("John");
        names.add("Alice");
        names.add("Zack");
        names.add("Bob");

        // 2. In danh sách để quan sát thứ tự (TreeSet sẽ tự động sắp xếp theo Alphabet)
        System.out.println("Danh sach cac ten: " + names);

        // 3. Lấy phần tử đầu tiên và cuối cùng
        System.out.println("Phan tu dau tien (nho nhat): " + names.first());
        System.out.println("Phan tu cuoi cung (lon nhat): " + names.last());
    }
}